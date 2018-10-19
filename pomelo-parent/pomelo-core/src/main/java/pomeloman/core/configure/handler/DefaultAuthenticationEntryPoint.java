package pomeloman.core.configure.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.google.gson.Gson;

public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private Gson gson;

	public DefaultAuthenticationEntryPoint(Gson gson) {
		this.gson = gson;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setHeader("Content-Type", "application/json;charset=utf-8");
		String responseJson = gson.toJson(new ResponseEntity<>(authException.getMessage(), HttpStatus.FORBIDDEN));
		response.getWriter().print(responseJson);
		response.getWriter().flush();
	}

}
