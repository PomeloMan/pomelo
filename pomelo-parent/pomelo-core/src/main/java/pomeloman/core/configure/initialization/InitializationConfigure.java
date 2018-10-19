package pomeloman.core.configure.initialization;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import pomeloman.core.common.exception.BusinessException;
import pomeloman.core.common.util.DateUtil;
import pomeloman.core.module.system.persistence.model.Authority;
import pomeloman.core.module.system.persistence.model.Role;
import pomeloman.core.module.system.persistence.model.User;
import pomeloman.core.module.system.service.interfaces.IAuthorityService;
import pomeloman.core.module.system.service.interfaces.IRoleService;
import pomeloman.core.module.system.service.interfaces.IUserService;

@Configuration
public class InitializationConfigure {

	private final Log logger = LogFactory.getLog(InitializationConfigure.class);

	@Value("${spring.application.init}")
	boolean init;

	@Autowired
	IUserService userService;
	@Autowired
	IAuthorityService authorityService;
	@Autowired
	IRoleService roleService;
	@Autowired
	Gson gson;

	@PostConstruct
	public void init() throws JsonSyntaxException, IOException, BusinessException {
		if (!init) {
			return;
		}
		boolean debug = logger.isDebugEnabled();

		// Init Timezone/Locale
		DateUtil.setDefault();

		// Init data
		try {
			authorityService.save(gson.fromJson(
					IOUtils.toString(this.getClass().getResourceAsStream("/initialization/authority.json"), "UTF-8"),
					new TypeToken<List<Authority>>() {
					}.getType()));
		} catch (Exception e) {
			if (debug) {
				logger.debug("Initialize the original authority data failed", e);
			}
			throw new BusinessException(e);
		}
		try {
			roleService.save(gson.fromJson(
					IOUtils.toString(this.getClass().getResourceAsStream("/initialization/role.json"), "UTF-8"),
					new TypeToken<List<Role>>() {
					}.getType()));
		} catch (Exception e) {
			if (debug) {
				logger.debug("Initialize the original role data failed", e);
			}
			throw new BusinessException(e);
		}
		try {
			Collection<User> users = gson.fromJson(
					IOUtils.toString(this.getClass().getResourceAsStream("/initialization/user.json"), "UTF-8"),
					new TypeToken<List<User>>() {
					}.getType());
			users.stream().forEach(user -> {
				try {
					user.setPassword(user.getPassword());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			});
			userService.save(users);
		} catch (Exception e) {
			if (debug) {
				logger.debug("Initialize the original user data failed", e);
			}
			throw new BusinessException(e);
		}
	}
}