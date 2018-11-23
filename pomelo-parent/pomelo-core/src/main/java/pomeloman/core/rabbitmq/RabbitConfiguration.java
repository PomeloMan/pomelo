package pomeloman.core.rabbitmq;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * https://docs.spring.io/spring-boot/docs/1.5.17.RELEASE/reference/htmlsingle/#boot-features-rabbitmq
 * 
 * @author Administrator
 */
@Configuration
public class RabbitConfiguration {

	@Autowired
	ApplicationContext context;

	@Value("${spring.rabbitmq.listener.simple.concurrency}")
	int concurrency;

	@PostConstruct
	public void init() {
		for (int i = 0; i < concurrency; i++) {
			context.getBean(Consumer.class);
		}
	}

	@Bean
	public Queue queue() {
		return new Queue("hello");
	}

}
