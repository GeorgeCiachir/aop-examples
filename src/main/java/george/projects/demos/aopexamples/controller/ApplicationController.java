package george.projects.demos.aopexamples.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import george.projects.demos.aopexamples.service.SimpleService;

@RequestMapping(value = "/")
@Controller
public class ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

	@Resource
	private SimpleService simpleService;

	/**
	 * Special case that demonstrates proxy objects with AOP
	 */
	@RequestMapping(value = "/indirectlyCallMethodThatIsNotAdvised")
	@ResponseBody
	public String controllerIndirectlyCallsServiceMethodThatIsNotAdvised () {
		return simpleService.callMethodThatIsNotAdvised();
	}

	/**
	 * Special case that demonstrates proxy objects with AOP
	 */
	@RequestMapping(value = "/directlyCallMethodThatIsNotAdvised")
	@ResponseBody
	public String controllerDirectlyCallsServiceMethodThatIsNotAdvised () {
		return simpleService.methodThatIsNotAdvisedIfCalledFromWithinTheService();
	}

	@RequestMapping(value = "/beforeAndAfter")
	@ResponseBody
	public String controllerDoSomething(@RequestParam(value = "name", required = false, defaultValue = "User") String name) {
		LOG.info("Inside the ApplicationController");
		return simpleService.doSomething(name);
	}

	@RequestMapping(value = "/around")
	@ResponseBody
	public String controllerDoSomethingElse() {
		LOG.info("Inside the ApplicationController");
		return simpleService.doSomethingElse();
	}

	@RequestMapping(value = "/afterReturning")
	@ResponseBody
	public String afterReturning() {
		LOG.info("Inside the ApplicationController");
		return simpleService.returnSuccess();
	}

	@RequestMapping(value = "/afterThrowing")
	@ResponseBody
	public String controllerThrowException() {
		LOG.info("Inside the ApplicationController");
		return simpleService.throwException();
	}

	@RequestMapping(value = "/annotatedMethod")
	@ResponseBody
	public String controllerAnnotatedMethod() {
		return simpleService.annotatedMethod();
	}
}
