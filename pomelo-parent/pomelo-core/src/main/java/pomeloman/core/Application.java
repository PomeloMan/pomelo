package pomeloman.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author PomeloMan
 */
@EnableScheduling
@EnableTransactionManagement
@EnableJpaRepositories("pomeloman.core.module.*.persistence.repo")
@EntityScan("pomeloman.core.module.*.persistence.entity")
@SpringBootApplication(scanBasePackages = { "pomeloman.core" })
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class }) // @link DynamicDataSourceConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
