package pomelo.test.user.ctrl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;

import pomelo.core.Application;
import pomelo.core.module.system.controller.UserController;
import pomelo.core.module.system.persistence.entity.User;
import pomelo.core.module.system.view.IUser;

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
//	@WithMockUser(username="administrator", roles={"USER", "ADMIN"})
	@WithUserDetails(value = "administrator", userDetailsServiceBeanName = "userService")
	public void queryByPage() {
		ResponseEntity<Page<User>> res = userCtrl.queryByPage(new IUser());
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
