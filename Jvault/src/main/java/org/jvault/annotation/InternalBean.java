package org.jvault.annotation;

import org.jvault.beans.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface InternalBean {
    String name() default "";
    Type type() default Type.SINGLETON;
    String[] accessVaults() default {""};
}
