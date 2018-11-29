package pomelo.core.module.system.controller;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import pomelo.core.common.interceptor.PreCheck;
import pomelo.core.module.system.persistence.entity.User;
import pomelo.core.module.system.service.interfaces.IUserService;
import pomelo.core.module.system.view.IUser;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "http://127.0.0.1", maxAge = 3600)
@Api(value = "/user", tags = "UserController")
public class UserController {

	@Autowired
	IUserService userService;

	@GetMapping("/me")
	@ApiOperation(value = "获取当前登录用户信息", notes = "头部需要带token信息")
	public Principal user(Principal user) {
		return user;
	}

	/**
	 * @param iuser
	 * @return
	 */
	@GetMapping("query")
//	@PreAuthorize("hasAnyAuthority('SYSTEM','GUEST')")
	@ApiOperation(value = "获取当前登录用户信息", notes = "头部需要带token信息")
	public ResponseEntity<Collection<User>> query(
			@RequestBody @ApiParam(name = "用户对象", value = "传入json格式", required = true) IUser view) {
		return new ResponseEntity<Collection<User>>(userService.query(view), HttpStatus.OK);
	}

	/**
	 * @param view
	 * @return
	 * @PreAuthorize.hasPermission {@link pomelo.core.configure.authentication.DefaultPermissionEvaluator.java}
	 */
	@PreCheck
	@GetMapping("queryByPage")
	@PreAuthorize("authenticated and hasPermission(#view, 'GUEST')") // DefaultPermissionEvaluator
	public ResponseEntity<Page<User>> queryByPage(@P("view") IUser view) {
		return new ResponseEntity<Page<User>>(userService.query(view, null), HttpStatus.OK);
	}

	@PreCheck
	@PostMapping("/save")
	public ResponseEntity<User> save(IUser view) {
		return new ResponseEntity<User>(userService.saveOne(view), HttpStatus.OK);
	}

}
