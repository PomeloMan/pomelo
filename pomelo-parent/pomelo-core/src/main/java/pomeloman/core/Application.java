package pomeloman.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author PomeloMan
 */
@EnableScheduling
@EnableTransactionManagement
@EnableJpaRepositories("pomeloman.core.module.*.persistence.repo")
@EntityScan("pomeloman.core.module.*.persistence.model")
@SpringBootApplication(scanBasePackages = { "pomeloman.core" })
public class Application {

	@Bean
	public Gson gson() {
		return new GsonBuilder().create();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
