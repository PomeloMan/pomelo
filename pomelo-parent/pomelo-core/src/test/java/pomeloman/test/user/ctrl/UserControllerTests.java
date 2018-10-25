package pomeloman.test.user.ctrl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;

import pomeloman.core.Application;
import pomeloman.core.module.system.controller.UserController;
import pomeloman.core.module.system.persistence.model.User;
import pomeloman.core.module.system.view.IUser;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTests {

	@Autowired
	Gson gson;
	@Autowired
	UserController userCtrl;

	@Test
	public void query() {
		ResponseEntity<Collection<User>> res = userCtrl.query(null);
		System.out.println(gson.toJson(res));
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

	@Transactional
	@Rollback(true)
	@Test
	public void save() {
		ResponseEntity<User> response = userCtrl.save(new IUser(new User("hello wordl")));
		System.out.println(response);
	}

}
