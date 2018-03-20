package pomeloman.springboot.core.system.controller;

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

import pomeloman.springboot.common.interceptor.PreCheck;
import pomeloman.springboot.core.system.persistence.model.User;
import pomeloman.springboot.core.system.service.interfaces.IUserService;
import pomeloman.springboot.core.system.view.IUser;

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
	public ResponseEntity<Collection<User>> query(IUser iuser) {
		return new ResponseEntity<Collection<User>>(userService.query(iuser), HttpStatus.OK);
	}

	/**
	 * @param iuser
	 * @return
	 * @PreAuthorize.hasPermission {@link pomeloman.springboot.configure.authentication.DefaultPermissionEvaluator.java}
	 */
	@PreCheck
	@GetMapping("queryByPage")
	@PreAuthorize("authenticated and hasPermission(#iuser, 'GUEST')")
	public ResponseEntity<Page<User>> queryByPage(@P("iuser") IUser iuser) {
		return new ResponseEntity<Page<User>>(userService.query(iuser, null), HttpStatus.OK);
	}

	@PreCheck
	@PostMapping("/save")
	public ResponseEntity<User> save(IUser iuser) {
		return new ResponseEntity<User>(userService.saveOne(iuser), HttpStatus.OK);
	}

}
