package pomelo.core.module.app.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.jsonwebtoken.Claims;
import pomelo.core.configure.jwt.JwtTokenAuthenticationService;
import pomelo.core.module.app.annotation.Guest;

/**
 * {@link WebMvcConfigure.class 'registry.addInterceptor(new
 * AppAuthorizationInterceptor())'} {@link WebSecurityConfigure.class
 * 'web.ignoring().antMatchers("/app/**")'}
 * 
 * @author Administrator
 */
@Component
public class AppAuthorizationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private JwtTokenAuthenticationService jwtTokenAuthenticationService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// only handler url path '/app/**'
		String uri = request.getRequestURI();
		if (!uri.startsWith("/app/")) {
			return true;
		}

		Guest annotation;
		if (handler instanceof HandlerMethod) {
			annotation = ((HandlerMethod) handler).getMethodAnnotation(Guest.class);
		} else {
			return true;
		}

		if (annotation != null) {
			return true;
		}

		String header = request.getHeader(jwtTokenAuthenticationService.getHeader());

		if (header == null) {
			throw new AuthenticationServiceException(HttpStatus.UNAUTHORIZED.getReasonPhrase());
		}

		String token = StringUtils.substringAfter(header, jwtTokenAuthenticationService.getPrefix());
		Claims claims = null;

		try {
			claims = jwtTokenAuthenticationService.getClaimByToken(token);
		} catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}

		// expired?
		if (jwtTokenAuthenticationService.isTokenExpired(claims.getExpiration())) {
			throw new AuthenticationServiceException("Token expired");
		}

		return true;
	}
}
