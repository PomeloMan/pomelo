package pomelo.core.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import pomelo.core.common.interceptor.PreCheckInterceptor;
import pomelo.core.configure.jwt.JwtTokenAuthenticationService;

@Configuration
public class WebMvcConfigure extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("/index");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new PreCheckInterceptor());
	}

	/**
	 * <p>跨域设置<br/>Cross-domain setting</p>
	 * {@link https://spring.io/blog/2015/06/08/cors-support-in-spring-framework}
	 * {@link https://spring.io/guides/gs/rest-service-cors/}
	 * {@link application.properties/endpoints.cors.allowed-xxx}
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.exposedHeaders(JwtTokenAuthenticationService.TOKEN_HEADER);// Set exposed header for front page to get the header.
		// .allowedOrigins("http://domainfrom.com", "*")
		// .allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE", "*");
		// .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin",
		// "Access-Control-Request-Method", "Access-Control-Request-Headers", "*")
		// .allowCredentials(true).maxAge(3600);
	}

	/**
	 * {@link pomelo.springboot.configure.WebSecurityConfigure.configure(WebSecurity web)}
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
//		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
//		registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/");
	}

}
