package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pomeloman.core.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UtilTest {

	@Test
	public void TimezoneLocaleTest() {
		System.out.println("Hello");
	}
}
