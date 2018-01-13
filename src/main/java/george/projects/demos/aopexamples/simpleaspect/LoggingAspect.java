package george.projects.demos.aopexamples.simpleaspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import george.projects.demos.aopexamples.annotations.CustomAnnotation;
import george.projects.demos.aopexamples.annotations.SecondCustomAnnotation;
import george.projects.demos.aopexamples.pointcuts.ApplicationPointcuts;
import george.projects.demos.aopexamples.service.SimpleService;

@Aspect
@Component
public class LoggingAspect {

	private Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

	/***
	 * Special case that demonstrates proxy objects with AOP
	 *
	 * Even though this advice points to the valid method {@link SimpleService#methodThatIsNotAdvisedIfCalledFromWithinTheService}, it will not be called if that
	 * method is called from within SimpleService and not from outside the class
	 *
	 * See doc for {@link SimpleService#methodThatIsNotAdvisedIfCalledFromWithinTheService} for a more detailed explanation
	 */
	@Before("execution(* methodThatIsNotAdvisedIfCalledFromWithinTheService())")
	public void thisMethodWillNotBeCalled() {
		LOG.info("This line is be logged only when the method is called from outside the class");
	}

	/**
	 * Executes before {@link SimpleService#doSomething}
	 */
	@Before("execution(* doSomething(String))")
	public void before(JoinPoint joinPoint) {
		LOG.info("Aspect before " + joinPoint.getStaticPart().getSignature().toString());
	}

	/**
	 * Executes after {@link SimpleService#doSomething}
	 */
	@After("execution(* doSomething(String))")
	public void after(JoinPoint joinPoint) {
		LOG.info("Aspect after " + joinPoint.getStaticPart().getSignature().toString());
	}

	/**
	 * Executes before and after {@link SimpleService#doSomethingElse}
	 * This is the most powerful advice an can actually prevent methods from executing
	 */
	@Around("execution(* doSomethingElse(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		LOG.info("Aspect entering around 1 " + joinPoint.getStaticPart().getSignature().toString());
		Object methodResult = joinPoint.proceed();
		LOG.info("Aspect exiting around 1 " + joinPoint.getStaticPart().getSignature().toString());
		return methodResult;
	}

	/**
	 * Executes {@link SimpleService#returnSuccess}, if it returns with success
	 */
	@AfterReturning(pointcut = "execution(* returnSuccess())", returning = "aStringValue")
	public void afterReturning(JoinPoint joinPoint, String aStringValue) {
		LOG.info("Aspect afterReturning caught String value from advised method: " + aStringValue);
		LOG.info("Aspect afterReturning " + joinPoint.getStaticPart().getSignature().toString());
	}

	/**
	 * Executes {@link SimpleService#throwException}, if it throws an the exception
	 */
	@AfterThrowing(pointcut = "execution(* throwException(..))", throwing = "thrownException")
	public void afterThrowing(RuntimeException thrownException) {
		LOG.info("Aspect afterThrowing. Exception message is: {}", thrownException.getMessage());
	}

	/**
	 * Executes for every method in the class {@link SimpleService}
	 */
	@Before("execution(* george.projects.demos.aopexamples.service.SimpleService.*(..))")
	public void logTheInfo1() {
		LOG.info("Logging the info 1");
	}

	/**
	 * Executes for every method in the class {@link SimpleService}, using wildcards for the package
	 */
	@Before("execution(* george..*SimpleService.*(..))")
	public void logTheInfo2() {
		LOG.info("Logging the info 2");
	}

	/**
	 * Executes for every method in the package {@linkplain george} or another subpackage
	 */
	@Before("execution(* george..*.*(..))")
	public void logTheInfo3(JoinPoint joinPoint) {
		LOG.info("Logging the info 3 - " + joinPoint.getStaticPart().getSignature().toString());
	}

	/**
	 * Executes if a method is annotated with {@link CustomAnnotation}
	 */
	@Before("execution(@george.projects.demos.aopexamples.annotations.CustomAnnotation * george..*.*(..))")
	public void logInfoFromAnnotatedMethod1(JoinPoint joinPoint) {
		LOG.info("Aspect for annotated method - " + joinPoint.getStaticPart().getSignature().toString());
	}

	/**
	 * The pointcut is defines as method in another class: {@link ApplicationPointcuts}
	 * The pointcut expression is defined on another method and is referenced here
	 */
	@Before(value = "george.projects.demos.aopexamples.pointcuts.ApplicationPointcuts.logAnnotatedMethods()")
	public void logInfoFromAnnotatedMethod2(JoinPoint joinPoint) {
		LOG.info("Logging for annotated method using ApplicationPointcuts class - " + joinPoint.getStaticPart().getSignature().toString());
	}

	/**
	 * Executes if a method is annotated with either {@link CustomAnnotation} OR {@link SecondCustomAnnotation}
	 */
	@Before("execution(@george.projects.demos.aopexamples.annotations.CustomAnnotation * george..*.*(..)) || "
			+ "execution(@george.projects.demos.aopexamples.annotations.SecondCustomAnnotation * george..*.*(..))")
	public void logInfoFromAnnotatedMethod3(JoinPoint joinPoint) {
		LOG.info("Aspect for annotated method - " + joinPoint.getStaticPart().getSignature().toString());
	}

	/**
	 * Executes if a method is annotated with BOTH {@link CustomAnnotation} AND {@link SecondCustomAnnotation}
	 */
	@Before("execution(@george.projects.demos.aopexamples.annotations.CustomAnnotation * george..*.*(..)) && "
			+ "execution(@george.projects.demos.aopexamples.annotations.SecondCustomAnnotation * george..*.*(..))")
	public void logInfoFromAnnotatedMethod4(JoinPoint joinPoint) {
		LOG.info("Aspect for annotated method - " + joinPoint.getStaticPart().getSignature().toString());
	}

}
