package pomeloman.core.configure.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * AuthenticationProvider
 * @author PomeloMan
 */
public abstract class AbstractAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {

	protected final Log logger = LogFactory.getLog(getClass());

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	protected abstract UserDetails loadUserByUsername(String username) throws AuthenticationException;

	/**
	 * AuthenticationProvider
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		String username = token.getName();

		UserDetails loadedUser = null;
		try {
			loadedUser = loadUserByUsername(username);
		} catch (UsernameNotFoundException notFound) {
			throw notFound;
		} catch (Exception repositoryProblem) {
			throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}

		Assert.notNull(loadedUser, "retrieveUser returned null - a violation of the interface contract");
		preCheck(loadedUser);
		credentialsCheck(loadedUser, token);
		postCheck(loadedUser);
		return createSuccessAuthentication(loadedUser, authentication, loadedUser);
	}

	/**
	 * @param principal
	 * @param authentication
	 * @param user
	 * @return
	 */
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal, authentication.getCredentials(), user.getAuthorities());
		result.setDetails(authentication.getDetails());
		return result;
	}

	/**
	 * 认证状态
	 * @param user
	 */
	protected void preCheck(UserDetails user) {
		if (!user.isAccountNonLocked()) {
			logger.debug("User account is locked");
			throw new LockedException(messages.getMessage("AuthenticationProvider.locked", "User account is locked"));
		}
		if (!user.isEnabled()) {
			logger.debug("User account is disabled");
			throw new DisabledException(messages.getMessage("AuthenticationProvider.disabled", "User is disabled"));
		}
		if (!user.isAccountNonExpired()) {
			logger.debug("User account is expired");
			throw new AccountExpiredException(messages.getMessage("AuthenticationProvider.expired", "User account has expired"));
		}
	}

	/**
	 * 认证密码
	 * @param user
	 * @param authentication
	 */
	protected void credentialsCheck(UserDetails user, UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

	protected void postCheck(UserDetails user) {
		if (!user.isCredentialsNonExpired()) {
			logger.debug("User account credentials have expired");
			throw new CredentialsExpiredException(messages.getMessage("AuthenticationProvider.credentialsExpired", "User credentials have expired"));
		}
	}

	/**
	 * AuthenticationProvider
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);// return true
	}

	/**
	 * MessageSourceAware
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}
}
