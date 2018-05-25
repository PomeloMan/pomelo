package pomeloman.springboot.configure.authentication;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * {@link https://docs.spring.io/spring-security/site/docs/5.0.0.RELEASE/reference/htmlsingle/#el-permission-evaluator}
 * @author PomeloMan
 */
@Component
public class DefaultPermissionEvaluator implements PermissionEvaluator {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		return authentication.getAuthorities().stream()
				.filter(a -> a.getAuthority().equalsIgnoreCase(permission.toString())).findAny().isPresent();
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		// ...
		return false;
	}

}
