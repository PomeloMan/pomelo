package pomeloman.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import pomeloman.core.Application;
import pomeloman.core.module.system.controller.UserController;
import pomeloman.core.module.system.persistence.entity.User;
import pomeloman.core.module.system.view.IUser;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DefaultControllerTests {

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

	// 使用 TestRestTemplate 测试接口
	@Autowired
	private TestRestTemplate template;

	@Test
	public void testLoginByTemplate() {
		Map<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("username", "administrator");
		urlVariables.put("password", "administrator");
		ResponseEntity<String> body = this.template.postForEntity("/login", urlVariables, String.class);
		assertThat(body.getHeaders().get("Authorization")).isNotNull();
	}

	@TestConfiguration
	static class Config {
		@Bean
		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder().setConnectTimeout(1000).setReadTimeout(1000);
		}
	}

	// 使用模拟(MockMvc)环境测试
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testLoginByMock() throws Exception {
		this.mockMvc.perform(post("/login").param("username", "administrator").param("password", "administrator"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(header().string("Authorization", Matchers.notNullValue()));
	}
}
