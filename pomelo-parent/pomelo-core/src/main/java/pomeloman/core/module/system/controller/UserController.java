package pomeloman.core.module.system.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pomeloman.core.common.interceptor.PreCheck;
import pomeloman.core.module.system.persistence.model.User;
import pomeloman.core.module.system.service.interfaces.IUserService;
import pomeloman.core.module.system.view.IUser;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "http://127.0.0.1", maxAge = 3600)
public class UserController {

	@Autowired
	IUserService userService;

	@GetMapping("/me")
	public Principal user(Principal user) {
		return user;
	}

	/**
	 * @param iuser
	 * @return
	 */
	@GetMapping("query")
//	@PreAuthorize("hasAnyAuthority('SYSTEM','GUEST')")
	public ResponseEntity<Collection<User>> query(IUser view) {
		return new ResponseEntity<Collection<User>>(userService.query(view), HttpStatus.OK);
	}

	/**
	 * @param view
	 * @return
	 * @PreAuthorize.hasPermission {@link pomeloman.core.configure.authentication.DefaultPermissionEvaluator.java}
	 */
	@PreCheck
	@GetMapping("queryByPage")
	@PreAuthorize("authenticated and hasPermission(#view, 'GUEST')")// DefaultPermissionEvaluator
	public ResponseEntity<Page<User>> queryByPage(@P("view") IUser view) {
		return new ResponseEntity<Page<User>>(userService.query(view, null), HttpStatus.OK);
	}

	@PreCheck
	@PostMapping("/save")
	public ResponseEntity<User> save(IUser view) {
		return new ResponseEntity<User>(userService.saveOne(view), HttpStatus.OK);
	}

}
