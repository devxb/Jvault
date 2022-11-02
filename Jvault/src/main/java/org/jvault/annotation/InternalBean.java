package org.jvault.annotation;

import org.jvault.beans.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that registers the object as Bean in {@link org.jvault.vault.Vault}.<br><br>
 *
 * Below are the default settings of InternalBean. <br> <br>
 *
 * Bean name : Same as changing the first alphabet of the class name to lowercase <br>
 * Bean {@link org.jvault.beans.Type} : Bean is created as Singleton by default <br>
 * Bean accesses : Set to Empty String[] by default. In this case, this Bean can be injected from all packages
 * <br><br>
 *
 * Example code
 * <pre>
 * {@code @InternalBean(name = "example",
 *               type = Type.NEW,
 *               accesses = {"org.jvault.beans", "org.jvault.factory.*"})
 * public Class HelloWorld{}}
 * </pre>
 * then this Class will register bean as <br>
 * name : "example", <br>
 * type = Type.NEW, <br>
 * accesses = {"org.jvault.beans", "org.jvault.factory.*"}
 *
 * @see Inject
 *
 * @author devxb
 * @since 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface InternalBean {

    /**
     * Specifies the name of the bean to be created.<br>
     * When this bean is injected, you need to write the name you specified here. <br><br>
     *
     * Default Bean name : Same as changing the first alphabet of the class name to lowercase
     *
     * @return String - bean name
     *
     * @author devxb
     * @since 0.1
     */
    String name() default "";

    /**
     * Specifies the type of bean to be created.
     *
     * Default Bean {@link org.jvault.beans.Type} : Bean is created as Singleton by default <br>
     *
     * @return {@link org.jvault.beans.Type}
     *
     * @author devxb
     * @since 0.1
     */
    Type type() default Type.SINGLETON;

    /**
     * Specifies the package path into which this bean can be injected.<br><br>
     *
     * If .* expression is used,<br>
     * this bean can be injected up to the leaf directory of all children including the preceding path. <br><br>
     * <hr>
     * example1 : org.jvault.* <br>
     * All packages under org.jvault including the org.jvault package can receive this bean injection. <br><br>
     *
     * example2 : org.jvault.bean <br>
     * Only objects included in the org.jvault.bean package can receive this bean injection.
     * <hr>
     * Default accesses : Set to Empty String[] by default. In this case, this Bean can be injected from all packages
     *
     * @return String[]
     *
     * @author devxb
     * @since 0.1
     */
    String[] accesses() default {};
}
