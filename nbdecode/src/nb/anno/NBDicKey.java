package nb.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NBDicKey {
    String[] key() default {};
    int index() default 0;
    String dict() default "";
    String prop() default "";
}
