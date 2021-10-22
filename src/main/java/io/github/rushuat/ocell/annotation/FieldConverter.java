package io.github.rushuat.ocell.annotation;

import io.github.rushuat.ocell.field.ValueConverter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldConverter {

  Class<? extends ValueConverter> value();
}
