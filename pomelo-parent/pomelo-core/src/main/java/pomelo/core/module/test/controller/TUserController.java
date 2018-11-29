package pomelo.core.module.test.controller;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pomelo.core.module.system.persistence.entity.User;
import pomelo.core.module.system.service.interfaces.IUserService;
import pomelo.core.module.system.view.IUser;

@RestController
@RequestMapping("/test")
public class TUserController {

	private final static Log logger = LogFactory.getLog(TUserController.class);

	@Autowired
	IUserService userService;

	@GetMapping("query")
	public ResponseEntity<Collection<User>> query(IUser view) {
		return new ResponseEntity<Collection<User>>(userService.query(view), HttpStatus.OK);
	}

	@GetMapping("queryByPage")
	public ResponseEntity<Page<User>> queryByPage(IUser view) {
		return new ResponseEntity<Page<User>>(userService.query(view, null), HttpStatus.OK);
	}

	@GetMapping("/save")
	public ResponseEntity<User> saveOne(User user) {
		return new ResponseEntity<User>(userService.saveOne(user), HttpStatus.OK);
	}

	/**
	 * 并发测试 悲观锁
	 * 
	 * @param user
	 * @return
	 */
	@GetMapping("/findforupdate")
	public ResponseEntity<User> findOne(User user) {
		return new ResponseEntity<User>(userService.findOneForUpdate(user.getUsername()), HttpStatus.OK);
	}

	/**
	 * 并发测试 乐观锁
	 * 
	 * @param user
	 * @return
	 */
	@GetMapping("/mutisave")
	public ResponseEntity<User> save(User user) {
		for (int i = 0; i < 4; i++) {
			final int j = i;
			new Thread(() -> {
				User _user = userService.findOne(user.getUsername());
				logger.info("Thread-" + j);
				if (_user == null) {
					_user = new User();
					_user.setUsername(user.getUsername());
				}
				_user.setDisplayName(user.getDisplayName() + "-Thread[" + j + "]");
				userService.saveOne(_user);
			}).start();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
