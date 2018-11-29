package pomelo.core.common.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName GlobalExceptionHandler.java
 * @Description {@Link BasicErrorController}
 * @author PomeloMan
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	public static final String DEFAULT_ERROR_VIEW = "/error";

	@ExceptionHandler(value = Exception.class)
	public Object defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		Object item = null;
		if (isJson(req)) {
			item = new ResponseEntity<Exception>(e, HttpStatus.EXPECTATION_FAILED);
		} else {
			ModelAndView mav = new ModelAndView();
			mav.addObject("exception", e);
			mav.addObject("url", req.getRequestURI());
			mav.setViewName(DEFAULT_ERROR_VIEW);
			item = mav;
		}
		return item;
	}

	private Boolean isJson(HttpServletRequest req) {
		String acceptHeader = req.getHeader("Accept");
		String contentTypeHeader = req.getHeader("Content-Type");
		return (acceptHeader != null && acceptHeader.contains("json")) || (contentTypeHeader != null && contentTypeHeader.contains("json"));
	}

	@SuppressWarnings("unused")
	private boolean isAjax(HttpServletRequest req) {
		return "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
	}

}
