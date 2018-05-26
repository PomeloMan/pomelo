package test.ctrl;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import pomeloman.core.Application;
import pomeloman.core.module.system.controller.UserController;
import pomeloman.core.module.system.persistence.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TUserController {

	@Autowired
	private UserController userCtrl;

	@Test
	public void query() {
		ResponseEntity<Collection<User>> res = userCtrl.query(null);
		res.getBody().stream().forEach((user) -> {
			System.out.println(user.getUsername());
		});
	}

	@Test
	public void queryByPage() {
		ResponseEntity<Page<User>> res = userCtrl.queryByPage(null);
		res.getBody().getContent().stream().forEach((user) -> {
			System.out.println(user.getUsername());
		});
	}
}
