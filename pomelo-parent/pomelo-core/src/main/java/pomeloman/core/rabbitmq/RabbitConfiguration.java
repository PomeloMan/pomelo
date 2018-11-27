package pomeloman.core.rabbitmq;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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

	private final static String DEFAULT_FANOUT_EXCHANGE = "pomelor.fanout.default";
	private final static String DEFAULT_DIRECT_EXCHANGE = "pomelor.direct.default";
	private final static String DEFAULT_TOPIC_EXCHANGE = "pomelor.topic.default";

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

	/**
	 * <p>
	 * 声明交互器
	 * </p>
	 * <p>
	 * FanoutExchange：将消息分发到所有的绑定队列，无 routingkey 的概念<br/>
	 * DirectExchange：按照 routingkey 分发到指定队列<br/>
	 * TopicExchange：多关键字匹配<br/>
	 * HeadersExchange：通过添加属性 key-value 匹配
	 * </p>
	 */
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(DEFAULT_FANOUT_EXCHANGE);
	}

	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(DEFAULT_DIRECT_EXCHANGE);
	}

	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(DEFAULT_TOPIC_EXCHANGE);
	}

	/**
	 * 日志队列
	 * 
	 * @return
	 */
	@Bean
	public Queue logAllQueue() {
		return new Queue("log.all");
	}

	@Bean
	public Queue logInfoQueue() {
		return new Queue("log.info");
	}

	/**
	 * 邮件队列
	 * 
	 * @return
	 */
	@Bean
	public Queue emailQueue() {
		return new Queue("email");
	}

	/**
	 * Topic exchange<br/>
	 * '#' 表示0个或若干个关键字<br/>
	 * '*' 表示一个关键字<br>
	 * 如'log.*' 与 'log.warn' 匹配，无法与 'log.warn.timeout' 匹配；但是 'log.#' 能与上述两者匹配。
	 */
	private final static String ROUTING_KEY_LOG_ALL = "log.#";
	private final static String ROUTING_KEY_LOG_INFO = "log.info";

	/**
	 * 绑定 Exchange 与 Queue
	 * 
	 * @return
	 */
	@Bean
	public Binding bindingTopicLogAll() {
		return bindingExchange(logAllQueue(), topicExchange(), ROUTING_KEY_LOG_ALL);
	}

	@Bean
	public Binding bindingTopicLogInfo() {
		return bindingExchange(logInfoQueue(), topicExchange(), ROUTING_KEY_LOG_INFO);
	}

	@Bean
	public Binding bindingFanout() {
		return bindingExchange(emailQueue(), fanoutExchange());
	}

	/** Private Method **/
	private Binding bindingExchange(Queue queue, FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}

	private Binding bindingExchange(Queue queue, TopicExchange exchange, String routingKey) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}
}
