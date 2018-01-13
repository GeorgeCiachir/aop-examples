package george.projects.demos.aopexamples.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Config class not actually required, as the aspects can also be components and can be discovered by the framework
 * Just wanted to point they can also instantiated as beans
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages="george.projects.demos.aopexamples.simpleaspect")
public class SimpleAspectConfiguration {

}
