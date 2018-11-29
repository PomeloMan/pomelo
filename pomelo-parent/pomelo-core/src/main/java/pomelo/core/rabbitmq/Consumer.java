package pomelo.core.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 多例模式（可以生成多个不同的消费者对象）
 * 
 * @author Administrator
 */
@Component
@Scope("prototype")
public class Consumer {

	@RabbitListener(queues = "log.all")
	@RabbitHandler
	public void logWarnMessage(String content) {
		System.out.println("Receiver:all " + this.toString() + ":" + content);
	}

	@RabbitListener(queues = "log.info")
	@RabbitHandler
	public void logInfoMessage(String content) {
		System.out.println("Receiver:info " + this.toString() + ":" + content);
	}
}
