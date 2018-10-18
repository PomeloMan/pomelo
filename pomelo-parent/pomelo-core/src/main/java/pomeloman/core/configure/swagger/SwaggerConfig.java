package pomeloman.core.configure.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket docket() {

		ParameterBuilder builder = new ParameterBuilder();

		List<Parameter> pars = new ArrayList<>();
		builder.name("Authorization").description("token").modelRef(new ModelRef("string")).parameterType("header")
				.required(false).build();
		pars.add(builder.build());

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any())
				.build().globalOperationParameters(pars);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Pomelo API").description("Restful API specification")
				.termsOfServiceUrl("https://github.com/PomeloMan/pomelo").version("1.0").build();
	}

}
