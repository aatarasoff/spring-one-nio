package info.developerblog.spring.oneserver;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author alexander.tarasov
 */
@Target({java.lang.annotation.ElementType.TYPE})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface HttpController {
    String[] value() default {};
}
