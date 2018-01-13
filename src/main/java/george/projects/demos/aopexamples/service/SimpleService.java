package george.projects.demos.aopexamples.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import george.projects.demos.aopexamples.annotations.CustomAnnotation;
import george.projects.demos.aopexamples.annotations.SecondCustomAnnotation;
import george.projects.demos.aopexamples.simpleaspect.LoggingAspect;

/**
 * A service that is called by the ApplicationController
 */
@Service
public class SimpleService {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleService.class);

	/**
	 * Special case that demonstrates proxy objects with AOP
	 */
	public String callMethodThatIsNotAdvised() {
		return methodThatIsNotAdvisedIfCalledFromWithinTheService();
	}

	/**
	 * Special case that demonstrates proxy objects with AOP
	 *
	 * All of the methods of this class are advised by the {@link george.projects.demos.aopexamples.simpleaspect.LoggingAspect}. However,
	 * if this one will be called only from within the class, it will not be discovered and advised.
	 *
	 * The idea is that a proxy object is created, is wrapped around this class, and then injected wherever this class is required. When a method call
	 * is made for this class, it actually goes through the proxy and the proxy calls both the required method and the corresponding method on the
	 * {@link LoggingAspect} annotated class
	 * If the this method is called from within this class, the call does not go through the proxy, therefore the corresponding
	 * method on the {@link LoggingAspect} annotated class will not be called
	 */
	public String methodThatIsNotAdvisedIfCalledFromWithinTheService() {
		return "This method is not discovered by the LoggingAspect";
	}

	public String doSomething(String name) {
		LOG.info("Service doing something with the received name: {}", name);
		return "doSomething from the service";
	}

	public String doSomethingElse() {
		LOG.info("Service doing something else");
		return "doSomething from the service";
	}

	public String throwException() {
		LOG.info("Service throwing an exception");
		throw new RuntimeException("This is the exception thrown from the service");
	}

	public String returnSuccess() {
		LOG.info("Service returning success");
		return "Success from the service";
	}

	@CustomAnnotation
	public String annotatedMethod() {
		return "Method is annotated";
	}

	@SecondCustomAnnotation
	public String secondAnnotatedMethod() {
		return "Method is annotated";
	}

	@CustomAnnotation
	@SecondCustomAnnotation
	public String thirdAnnotatedMethod() {
		return "Method is annotated";
	}
}
