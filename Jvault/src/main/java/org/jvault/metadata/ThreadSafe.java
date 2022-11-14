package org.jvault.metadata;

import java.lang.annotation.*;

/**
 * A class marked with this annotation means that it is designed to be ThreadSafe.
 *
 * @since 0.1
 * @author devxb
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ThreadSafe {
}
