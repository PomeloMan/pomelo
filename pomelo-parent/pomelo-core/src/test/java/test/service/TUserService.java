package test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pomeloman.core.Application;
import pomeloman.core.module.system.persistence.model.User;
import pomeloman.core.module.system.service.interfaces.IUserService;

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