package pomeloman.core.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.gson.Gson;

import pomeloman.core.configure.authentication.AuthenticationProvider;
import pomeloman.core.configure.handler.DefaultAccessDeniedHandler;
import pomeloman.core.configure.handler.DefaultAuthenticationEntryPoint;
import pomeloman.core.configure.jwt.JwtAuthenticationFilter;
import pomeloman.core.configure.jwt.JwtTokenAuthenticationService;
import pomeloman.core.configure.jwt.JwtUsernamePasswordAuthenticationFilter;
import pomeloman.core.module.system.service.interfaces.IUserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

	/**
	 * {@link pomeloman.springboot.configure.MvcConfigure.addResourceHandlers(ResourceHandlerRegistry
	 * registry)}
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/resources/**");
		web.ignoring().antMatchers("/**/*.html", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.gif",
				"/**/*.svg", "/**/*.ico", "/**/*.ttf", "/**/*.woff", "/**/*.woff2");
		web.ignoring().antMatchers("/test/**");
		// swagger-ui
		web.ignoring().antMatchers("/swagger-resources/**", "/v2/api-docs", "/webjars/**", "/swagger-ui.html");
	}

	/**
	 * http.cors() ->
	 * {@link pomeloman.springboot.configure。WebMvcConfigure。addCorsMappings(CorsRegistry
	 * registry)}
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// security 默认会开启 CSRF 处理，判断请求是否携带了token，如果没有就拒绝访问。并且，在请求为(GET|HEAD|TRACE|OPTIONS)时，则不会开启。
		http.csrf().disable();
		// http.headers().xssProtection();// security.headers.xss=true
		http.cors().and().authorizeRequests().antMatchers("/", "/index.html").permitAll()
				.antMatchers(HttpMethod.POST, "/login").permitAll().anyRequest().authenticated();
		// .and().logout().logoutSuccessUrl("/").permitAll();
		// .and()http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		http.addFilterBefore(jwtUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().accessDeniedHandler(new DefaultAccessDeniedHandler(getApplicationContext().getBean(Gson.class)))
				.authenticationEntryPoint(new DefaultAuthenticationEntryPoint(getApplicationContext().getBean(Gson.class)));
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new AuthenticationProvider(getApplicationContext().getBean(IUserService.class)));
	}

	/**
	 * JWT
	 */
	@Bean
	public JwtUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter() throws Exception {
		JwtUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter = new JwtUsernamePasswordAuthenticationFilter();
		jwtUsernamePasswordAuthenticationFilter.setGson(getApplicationContext().getBean(Gson.class));
		jwtUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
		jwtUsernamePasswordAuthenticationFilter
				.setJwtTokenAuthenticationService(getApplicationContext().getBean(JwtTokenAuthenticationService.class));
		return jwtUsernamePasswordAuthenticationFilter;
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
		jwtAuthenticationFilter.setGson(getApplicationContext().getBean(Gson.class));
		jwtAuthenticationFilter.setAuthenticationManager(authenticationManager());
		jwtAuthenticationFilter
				.setJwtTokenAuthenticationService(getApplicationContext().getBean(JwtTokenAuthenticationService.class));
		return jwtAuthenticationFilter;
	}
}
