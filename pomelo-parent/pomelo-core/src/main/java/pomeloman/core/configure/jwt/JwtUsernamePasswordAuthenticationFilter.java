package pomeloman.core.configure.jwt;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import pomeloman.core.module.system.enums.Status;
import pomeloman.core.module.system.persistence.model.User;

/**
 * JwtUsernamePasswordAuthenticationFilter
 * @author PomeloMan
 */
public class JwtUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private Gson gson;
	private JwtTokenAuthenticationService jwtTokenAuthenticationService;

	public JwtUsernamePasswordAuthenticationFilter() {
		super(new AntPathRequestMatcher("/login", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse res)
			throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		try {
			User user = gson.fromJson(new InputStreamReader(request.getInputStream()), User.class);
			// User user = new ObjectMapper().readValue(req.getInputStream(), User.class); //JWT

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					user.getUsername(), user.getPassword());

			return getAuthenticationManager().authenticate(authRequest);
		} catch (IOException e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(auth);

		User principal = new User(auth.getName(), Status.Valid, auth.getAuthorities().stream()
				.map(a -> new SimpleGrantedAuthority(a.getAuthority())).collect(Collectors.toSet()));
		String token = jwtTokenAuthenticationService.generateToken(auth.getName(),
				gson.fromJson("{" + JwtTokenAuthenticationService.PRINCIPAL + ":" + gson.toJson(principal) + "}",
						new TypeToken<HashMap<String, Object>>() {
						}.getType()));
		res.addHeader(JwtTokenAuthenticationService.TOKEN_HEADER, JwtTokenAuthenticationService.TOKEN_PREFIX + token);
	}

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	public JwtTokenAuthenticationService getJwtTokenAuthenticationService() {
		return jwtTokenAuthenticationService;
	}

	public void setJwtTokenAuthenticationService(JwtTokenAuthenticationService jwtTokenAuthenticationService) {
		this.jwtTokenAuthenticationService = jwtTokenAuthenticationService;
	}

}