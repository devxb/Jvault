package org.jvault.metadata;

import java.lang.annotation.*;

/**
 * It means an extension point used internally by Jvault.<br>
 * <br>
 * Extending this class does not change the action of Jvault library.
 *
 * @author devxb
 * @since 0.1
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InternalExtensiblePoint {}
