package pomeloman.springboot.configure;

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

import pomeloman.springboot.configure.authentication.AuthenticationProvider;
import pomeloman.springboot.configure.jwt.JwtAuthenticationFilter;
import pomeloman.springboot.configure.jwt.JwtTokenAuthenticationService;
import pomeloman.springboot.configure.jwt.JwtUsernamePasswordAuthenticationFilter;
import pomeloman.springboot.core.system.service.interfaces.IUserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

	/**
	 * {@link pomeloman.springboot.configure.MvcConfigure.addResourceHandlers(ResourceHandlerRegistry registry)}
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/resources/**");
		web.ignoring().antMatchers("/**/*.html", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.gif",
				"/**/*.svg", "/**/*.ico", "/**/*.ttf", "/**/*.woff", "/**/*.woff2");
	}

	/**
	 * http.cors() -> {@link pomeloman.springboot.configure。WebMvcConfigure。addCorsMappings(CorsRegistry registry)}
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.cors().and().authorizeRequests()
				.antMatchers("/", "/index.html").permitAll()
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				.anyRequest().authenticated();
		// .and().logout().logoutSuccessUrl("/").permitAll();
		// .and()http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		http.addFilterBefore(jwtUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
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
		jwtUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
		jwtUsernamePasswordAuthenticationFilter.setJwtTokenAuthenticationService(getApplicationContext().getBean(JwtTokenAuthenticationService.class));
		return jwtUsernamePasswordAuthenticationFilter;
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
		jwtAuthenticationFilter.setAuthenticationManager(authenticationManager());
		jwtAuthenticationFilter.setJwtTokenAuthenticationService(getApplicationContext().getBean(JwtTokenAuthenticationService.class));
		return jwtAuthenticationFilter;
	}
}
