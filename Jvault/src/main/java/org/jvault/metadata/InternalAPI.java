package org.jvault.metadata;

import java.lang.annotation.*;

/**
 * A class marked with this annotation means that it is designed as an API for internal use.<br>
 * Therefore, clients should not use this API. <br>
 * <br>
 * In fact, the internal API is designed to be inaccessible from outside of project without using Reflection.<br>
 * If the client ignores this warning and uses Reflection to access and use the Internal API,<br>
 * the client will not be guaranteed backwards compatibility by upgrading the API version.<br>
 *
 * @author devxb
 * @since 0.1
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InternalAPI {
}
