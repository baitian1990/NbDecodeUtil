package nb.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NbSub {

    int length() default 0;

    String lenthProperty() default "";

    String preProperty() default "";

    String encodeType() default "";

    String dict() default "";

    String[] propKey() default {};

    String listCount() default "";

    Class listClass() default Object.class;

    String sortType() default "";

    int scale() default 0;

    boolean csVer() default false;

    String csStart() default "";

    String csEnd() default "";
}
