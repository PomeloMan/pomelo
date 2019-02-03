package pomelo.core.module.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pomelo.core.common.annotation.LogOperation;
import pomelo.core.module.app.annotation.Guest;

@RestController("appUserController")
@RequestMapping("/app/user")
public class UserController {

	@Guest
	@GetMapping("/test")
	@LogOperation("test")
	public ResponseEntity<String> test() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/auth")
	public ResponseEntity<String> auth() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
