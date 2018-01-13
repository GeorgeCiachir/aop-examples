package george.projects.demos.aopexamples.pointcuts;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Demonstrates that the pointcuts can be declared separately
 */
public class ApplicationPointcuts {

	/**
	 * The method is used in the {@link george.projects.demos.aopexamples.simpleaspect.LoggingAspect#logInfoFromAnnotatedMethod2}
	 */
	@Pointcut("execution(@george.projects.demos.aopexamples.annotations.CustomAnnotation * george..*.*(..))")
	public void logAnnotatedMethods () {
	}
}
