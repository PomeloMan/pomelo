package pomelo.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pomelo.core.Application;
import pomelo.core.module.system.persistence.entity.User;
import pomelo.core.module.system.service.interfaces.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TUserService {

	@Autowired
	private IUserService userService;

	@Test
	public void save() {
		User user = new User();
		user.setUsername("pomeloman");
		userService.saveOne(user);
	}

	private static void saveOne(User user) {
		synchronized (user.getUsername()) {
			System.out.println(user.toString());
			System.out.println(user.getUsername());
		}
	}

	@Test
	public void save(User user) {
		for (int i = 0; i < 4; i++) {
			final int j = i;
			new Thread(() -> {
				User _user = userService.findOne(user.getUsername());
				if (_user == null) {
					_user = new User();
					_user.setUsername(user.getUsername());
				}
				_user.setDisplayName(user.getDisplayName() + "-Thread[" + j + "]");
				userService.saveOne(_user);
			}).start();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			if (i == 3) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						User user = new User();
						user.setUsername("pomeloman");
						saveOne(user);
					}
				}).start();
			} else {
				new Thread(new Runnable() {
					@Override
					public void run() {
						User user = new User();
						user.setUsername("pomelo21431t13gwreghrqgrqegrqgqregqeggr");
						saveOne(user);
					}
				}).start();
			}
		}
	}
}
