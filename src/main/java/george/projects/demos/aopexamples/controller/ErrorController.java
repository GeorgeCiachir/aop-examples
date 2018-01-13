package george.projects.demos.aopexamples.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Used just for experimenting various scenarios
 */
@ControllerAdvice(annotations = {Service.class})
public class ErrorController {

	private static final Logger LOG = LoggerFactory.getLogger(ErrorController.class);

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public String handle(Exception e) {
		LOG.info("Caught the exception in the ErrorController");
		LOG.info("Message: {}", e.getMessage());
		return e.getMessage();
	}
}
