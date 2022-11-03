package org.jvault.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to inject the corresponding Bean.<br>
 *
 * Here is two ways to inject Bean.<br><br>
 *
 * FIRST. FIELD INJECTION
 * <pre>
 * {@code @Inject
 * private HelloToo helloToo;}
 * </pre>
 * In this case, inject bean based on the name of the field.<br>
 * Alternatively, you can search for bean by entering the bean name as follows.
 *
 * <pre>
 * {@code @Inject("customNamedHelloToo")
 * private HelloToo helloToo;}
 * </pre>
 * <br>
 *
 * SECOND. CONSTRUCTOR INJECTION
 * <pre>
 * {@code @Inject
 * private HelloWorld(@Inject("helloToo") HelloToo helloToo){}}
 * </pre>
 *
 * In this case, @Inject annotation should be marked on the constructor to be used for injection,<br>
 * and @Inject annotation should be marked on the constructor parameter to be injected.<br>
 * Also, always input the value of @Inject of the marked parameter. <br> <br>
 *
 * If the @Inject-marked parameter of the constructor <br>
 * marked with @Inject does not have a value, {@link org.jvault.exceptions.NoDefinedInternalBeanException} is thrown.<br>
 *
 * A class must have at most one constructor marked with @Inject annotation,<br>
 * and if two or more constructors marked with @Inject annotation are found, {@link org.jvault.exceptions.DuplicateInjectConstructorException} is thrown.<br>
 *
 * If CONSTRUCTOR INJECTION and FIELD INJECTION coexist, <br>
 * CONSTRUCTOR INJECTION is selected, and Bean injection proceeds in constructor injection method.
 *
 * @see InternalBean
 *
 * @author devxb
 * @since 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
public @interface Inject {

    /**
     * Specifies the name of the bean to be injected.
     *
     * @return String - The name of the bean to be injected.
     *
     * @author devxb
     * @since 0.1
     */
    String value() default "";
}
