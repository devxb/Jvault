package org.jvault.metadata;

import java.lang.annotation.*;

/**
 * This annotation means that the client is free to extend. <br>
 * Extended information can be used to change the action of the Jvault library.
 *
 * @author devxb
 * @since 0.1
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExtensiblePoint {}
