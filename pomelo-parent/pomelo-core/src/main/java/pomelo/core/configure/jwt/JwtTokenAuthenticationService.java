package pomelo.core.configure.jwt;

import java.util.Date;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import pomelo.util.jwt.JwtUtil;

@Service
@ConfigurationProperties("jwt")
public class JwtTokenAuthenticationService {

//	public final static String TOKEN_PREFIX = "Bearer ";
//	public final static String TOKEN_HEADER = "Authorization";
//	public final static String PRINCIPAL = "principal";
	private String prefix;
	private String header;
	private String principal;
	private String algorithm;
	private String secret;
	private long expiration;

	public String generateToken(String subject, Map<String, Object> claims) {
		return JwtUtil.generateToken(SignatureAlgorithm.valueOf(algorithm), subject, claims, expiration, secret);
	}

	public Claims validateToken(String token) {
		return JwtUtil.validateToken(token, secret);
	}

	public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public long getExpiration() {
		return expiration;
	}

	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}

}
