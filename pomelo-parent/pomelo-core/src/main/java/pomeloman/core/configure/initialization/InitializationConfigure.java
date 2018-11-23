package pomeloman.core.configure.initialization;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import pomeloman.core.common.exception.BusinessException;
import pomeloman.core.common.util.DateUtil;
import pomeloman.core.configure.ApplicationConstants;
import pomeloman.core.module.project.persistence.entity.ProjectWorkItemProcess;
import pomeloman.core.module.project.persistence.entity.ProjectWorkItemType;
import pomeloman.core.module.project.service.interfaces.IProjectWorkItemProcessService;
import pomeloman.core.module.project.service.interfaces.IProjectWorkItemTypeService;
import pomeloman.core.module.system.persistence.entity.Authority;
import pomeloman.core.module.system.persistence.entity.Role;
import pomeloman.core.module.system.persistence.entity.User;
import pomeloman.core.module.system.service.interfaces.IAuthorityService;
import pomeloman.core.module.system.service.interfaces.IRoleService;
import pomeloman.core.module.system.service.interfaces.IUserService;

@Configuration
public class InitializationConfigure {

	private final Log logger = LogFactory.getLog(InitializationConfigure.class);
	private boolean debug = logger.isDebugEnabled();

	@Value("${spring.application.initial}")
	boolean initial;

	@Autowired
	IUserService userService;
	@Autowired
	IAuthorityService authorityService;
	@Autowired
	IRoleService roleService;
	@Autowired
	IProjectWorkItemProcessService projProcessService;
	@Autowired
	IProjectWorkItemTypeService projTypeService;

	/** 手动注入 Bean **/
	ConfigurableApplicationContext context;
	BeanDefinitionRegistry beanDefinitionRegistry;

	@Autowired
	public InitializationConfigure(ConfigurableApplicationContext context) {
		this.context = context;
		this.beanDefinitionRegistry = (DefaultListableBeanFactory) context.getBeanFactory();
	}

	/**
	 * 注册 Bean 到 ApplicationContext
	 * @param beanId
	 * @param beanClass
	 */
	public void registerBean(String beanId, Class<?> beanClass) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
		BeanDefinition beanDefinition = builder.getBeanDefinition();
		beanDefinitionRegistry.registerBeanDefinition(beanId, beanDefinition);
	}
	/** 手动注入 Bean **/

	@Bean
	public Gson gson() {
		return new GsonBuilder().create();
	}

	@PostConstruct
	public void init() throws JsonSyntaxException, IOException, BusinessException {
		// Init Timezone/Locale
		DateUtil.setDefault();

		if (initial) {
			// Init data@src/main/resources/initialization/*.json
			try {
				Collection<Authority> auths = gson().fromJson(
						IOUtils.toString(this.getClass().getResourceAsStream("/initialization/authority.json"),
								ApplicationConstants.ENCODING),
						new TypeToken<List<Authority>>() {
						}.getType());
				Collection<Authority> _auths = authorityService.query(null);
				auths.removeAll(_auths);
				authorityService.save(auths);
			} catch (Exception e) {
				if (debug) {
					logger.debug("Initialize the original authority data failed", e);
				}
				throw new BusinessException(e);
			}
			try {
				Collection<Role> roles = gson()
						.fromJson(IOUtils.toString(this.getClass().getResourceAsStream("/initialization/role.json"),
								ApplicationConstants.ENCODING), new TypeToken<List<Role>>() {
								}.getType());
				Collection<Role> _roles = roleService.query(null);
				roles.removeAll(_roles);
				roleService.save(roles);
			} catch (Exception e) {
				if (debug) {
					logger.debug("Initialize the original role data failed", e);
				}
				throw new BusinessException(e);
			}
			try {
				Collection<User> users = gson()
						.fromJson(IOUtils.toString(this.getClass().getResourceAsStream("/initialization/user.json"),
								ApplicationConstants.ENCODING), new TypeToken<List<User>>() {
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
			try {
				Collection<ProjectWorkItemProcess> processes = gson().fromJson(IOUtils.toString(
						this.getClass().getResourceAsStream("/initialization/project/workItem_process.json"),
						ApplicationConstants.ENCODING), new TypeToken<List<ProjectWorkItemProcess>>() {
						}.getType());
				Collection<ProjectWorkItemProcess> _processes = projProcessService.query(null);
				processes.removeAll(_processes);
				projProcessService.save(processes);
			} catch (Exception e) {
				if (debug) {
					logger.debug("Initialize the original user data failed", e);
				}
				throw new BusinessException(e);
			}
			try {
				Collection<ProjectWorkItemType> types = gson().fromJson(IOUtils.toString(
						this.getClass().getResourceAsStream("/initialization/project/workItem_type.json"),
						ApplicationConstants.ENCODING), new TypeToken<List<ProjectWorkItemType>>() {
						}.getType());
				Collection<ProjectWorkItemType> _processes = projTypeService.query(null);
				Collection<String> names = _processes.stream().map(process -> {
					return process.getName();
				}).collect(Collectors.toSet());
				// exclude exist items
				types = types.stream().filter(type -> !names.contains(type.getName())).collect(Collectors.toSet());
				projTypeService.save(types);
			} catch (Exception e) {
				if (debug) {
					logger.debug("Initialize the original user data failed", e);
				}
				throw new BusinessException(e);
			}
		}
	}
}