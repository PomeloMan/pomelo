/*package pomeloman.springboot.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class DefaultWebController {

	@RequestMapping(value = "/{page}", method = RequestMethod.GET)
	public String template(@PathVariable("page") String page, HttpServletRequest request) {
		return "/" + page;
	}

}
*/