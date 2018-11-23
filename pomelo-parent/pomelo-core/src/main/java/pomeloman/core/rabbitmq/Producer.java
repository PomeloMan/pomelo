package pomeloman.core.rabbitmq;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 如果消息没有到 exchange, 则confirm回调, ack=false<br/>
 * 如果消息到达 exchange, 则confirm回调, ack=true
 * </p>
 * <p>
 * exchange 到 queue 成功, 则不回调 return<br/>
 * exchange 到 queue 失败, 则回调 return (需设置 mandatory=true, 否则不回回调, 消息就丢了)
 * </p>
 * <p>
 * 备注:需要说明, spring-rabbit 和原生的 rabbit-client, 表现是不一样的. 原生的 client, exchange
 * 错误的话, 直接就报错了, 是不会到 confirmListener 和 returnListener
 * </p>
 * 
 * @author Administrator
 *
 */
@Component
public class Producer implements ConfirmCallback, ReturnCallback {

//	private final AmqpAdmin amqpAdmin;
//	private final AmqpTemplate amqpTemplate;

//	@Autowired
//	public Producer(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
//		this.amqpAdmin = amqpAdmin;
//		this.amqpTemplate = amqpTemplate;
//	}

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostConstruct
	public void init() {
		rabbitTemplate.setConfirmCallback(this);
		rabbitTemplate.setReturnCallback(this);
	}

	public void send() {
		String sendMsg = "hello " + new Date();
		System.out.println("Sender: " + sendMsg);
		this.rabbitTemplate.convertAndSend("hello", sendMsg);
	}

	/**
	 * 消息是否到 exchange
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (ack) {
			System.out.println("消息发送成功:" + correlationData);
		} else {
			System.out.println("消息发送失败:" + cause);
		}
	}

	/**
	 * exchange 到 queue 失败回调函数
	 */
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		System.out.println(message.getMessageProperties().getCorrelationIdString() + " 发送失败");
	}
}
