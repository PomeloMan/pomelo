package pomeloman.core.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Consumer {

	@RabbitListener(queues = "hello")
	public void processMessage(String content) {
		System.out.println("Receiver " + this.toString() + ":" + content);
	}
}
