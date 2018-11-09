package pomeloman.test.project.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pomeloman.core.Application;
import pomeloman.core.module.project.persistence.entity.ProjectTeam;
import pomeloman.core.module.project.service.interfaces.IProjectTeamService;
import pomeloman.core.module.system.persistence.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectTeamServiceTest {

	@Autowired
	IProjectTeamService service;

	@Test
	public void test() {
		ProjectTeam team = new ProjectTeam();

		List<User> users = new ArrayList<>();
		users.add(new User("administrator"));
		users.add(new User("user"));
		team.setUsers(users);

		service.saveOne(team);
	}
}
