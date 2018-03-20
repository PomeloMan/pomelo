package pomeloman.springboot.configure.initialization;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import pomeloman.springboot.common.exception.BusinessException;
import pomeloman.springboot.common.util.IDateUtil;
import pomeloman.springboot.core.system.persistence.model.Authority;
import pomeloman.springboot.core.system.persistence.model.Role;
import pomeloman.springboot.core.system.persistence.model.User;
import pomeloman.springboot.core.system.service.interfaces.IAuthorityService;
import pomeloman.springboot.core.system.service.interfaces.IRoleService;
import pomeloman.springboot.core.system.service.interfaces.IUserService;

@Configuration
public class InitializationConfigure {

	private final Log logger = LogFactory.getLog(InitializationConfigure.class);

	Gson gson = new GsonBuilder().create();

	@Autowired
	IUserService userService;
	@Autowired
	IAuthorityService authorityService;
	@Autowired
	IRoleService roleService;

	@PostConstruct
	public void init() throws JsonSyntaxException, IOException, BusinessException {
		boolean debug = logger.isDebugEnabled();

		// Init Timezone/Locale
		IDateUtil.setDefault();

		// Init data
		try {
			authorityService.save(gson.fromJson(
					IOUtils.toString(this.getClass().getResourceAsStream("/initialization/authority.json"), "UTF-8"),
					new TypeToken<List<Authority>>() {
					}.getType()));
//			authorityService.save(gson.fromJson(FileUtils.readFileToString(
//					FileUtils.getFile(this.getClass().getResource("/initialization/authority.json").getPath()),
//					"UTF-8"), new TypeToken<List<Authority>>() {
//					}.getType()));
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
//			roleService.save(gson.fromJson(FileUtils.readFileToString(
//					FileUtils.getFile(this.getClass().getResource("/initialization/role.json").getPath()), "UTF-8"),
//					new TypeToken<List<Role>>() {
//					}.getType()));
		} catch (Exception e) {
			if (debug) {
				logger.debug("Initialize the original role data failed", e);
			}
			throw new BusinessException(e);
		}
		try {
			userService.save(gson.fromJson(
					IOUtils.toString(this.getClass().getResourceAsStream("/initialization/user.json"), "UTF-8"),
					new TypeToken<List<User>>() {
					}.getType()));
//			userService.save(gson.fromJson(FileUtils.readFileToString(
//					FileUtils.getFile(this.getClass().getResource("/initialization/user.json").getPath()), "UTF-8"),
//					new TypeToken<List<User>>() {
//					}.getType()));
		} catch (Exception e) {
			if (debug) {
				logger.debug("Initialize the original user data failed", e);
			}
			throw new BusinessException(e);
		}
	}
}