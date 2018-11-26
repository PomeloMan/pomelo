package pomeloman.test.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pomeloman.core.Application;
import pomeloman.core.rabbitmq.Producer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RabbitMQControllerTests {

	@Autowired
	private Producer sender;

	@Test
	public void hello() {
		for (int i = 0; i < 50; i++) {
			if (i > 25) {
				sender.send(1);
			} else {
				sender.send(0);
			}
		}
	}

}
