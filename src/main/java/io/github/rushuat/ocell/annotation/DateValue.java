package io.github.rushuat.ocell.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateValue {

  String value();

  String format() default "yyyy-MM-dd'T'HH:mm:ss'Z'";
}
