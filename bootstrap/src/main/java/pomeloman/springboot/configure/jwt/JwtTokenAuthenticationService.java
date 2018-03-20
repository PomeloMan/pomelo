package pomeloman.springboot.configure.jwt;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import pomeloman.util.jwt.JwtUtil;

@Service
public class JwtTokenAuthenticationService {

	public final static String TOKEN_PREFIX = "Bearer ";
	public final static String TOKEN_HEADER = "Authorization";
	public final static String PRINCIPAL = "principal";

	@Value("${jwt.algorithm}")
	private String algorithm;
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private long expiration;

	public String generateToken(String subject, Map<String, Object> claims) {
		return JwtUtil.generateToken(SignatureAlgorithm.valueOf(algorithm), subject, claims, expiration, secret);
	}

	public Claims validateToken(String token) {
		return JwtUtil.validateToken(token, secret);
	}
}
