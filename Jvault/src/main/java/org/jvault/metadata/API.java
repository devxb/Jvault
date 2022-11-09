package org.jvault.metadata;

import java.lang.annotation.*;

/**
 * A class marked with this annotation means that it is designed as an API.<br>
 * In other words, that class is intended to be used by clients, and in fact, clients can use it.
 *
 * @author devxb
 * @since 0.1
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface API {}
