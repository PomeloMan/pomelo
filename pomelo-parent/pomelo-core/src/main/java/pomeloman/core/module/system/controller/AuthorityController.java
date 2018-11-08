package pomeloman.core.module.system.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import pomeloman.core.common.interceptor.PreCheck;
import pomeloman.core.module.system.persistence.entity.Authority;
import pomeloman.core.module.system.service.interfaces.IAuthorityService;
import pomeloman.core.module.system.view.IAuthority;

@RestController
@RequestMapping("/auth")
@Api(value = "/auth", tags = "AuthorityController")
public class AuthorityController {

	@Autowired
	IAuthorityService authService;

	@GetMapping("query")
	@ApiOperation(value = "获取权限列表", notes = "头部需要带token信息")
	public ResponseEntity<Collection<Authority>> query(
			@RequestBody @ApiParam(name = "object", value = "传入json格式", required = true) IAuthority view) {
		return new ResponseEntity<Collection<Authority>>(authService.query(view), HttpStatus.OK);
	}

	@PreCheck
	@PostMapping("save")
	public ResponseEntity<Authority> save(IAuthority view) {
		return new ResponseEntity<Authority>(authService.saveOne(view), HttpStatus.OK);
	}
}
