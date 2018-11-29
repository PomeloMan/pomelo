package pomelo.core.rabbitmq;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	private final Log logger = LogFactory.getLog(Producer.class);

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

	public void send(int type) {
		String sendMsg = "hello " + new Date();
		if (type == 1) {
			this.rabbitTemplate.convertAndSend("pomelor.topic.default", "log.all", "log.all" + sendMsg);
		} else {
			this.rabbitTemplate.convertAndSend("pomelor.topic.default", "log.info", "log.info" + sendMsg);
		}
	}

	/**
	 * 消息是否到 exchange
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (ack) {
			logger.info(correlationData + "(message => exchange) successful.");
		} else {
			logger.warn(correlationData + "(message => exchange) failed: " + cause);
		}
	}

	/**
	 * exchange 到 queue 失败回调函数
	 */
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		logger.warn(message.getMessageProperties().getCorrelationIdString() + "(exchange => queue) failed.");
	}
}
