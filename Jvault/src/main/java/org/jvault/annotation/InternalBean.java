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
 *               accessPackages = {"org.jvault.beans", "org.jvault.factory.*"},
 *               accessClasses = {"org.beanreader.AnnotatedBeanReader"})
 * public Class HelloWorld{}}
 * </pre>
 * then this Class will be registered bean as below info <br>
 * name : "example" <br>
 * type = Type.NEW <br>
 * accessPackages = {"org.jvault.beans", "org.jvault.factory.*"} <br>
 * accessClasses = {"org.beanreader.AnnotatedBeanReader"} <br><br>
 *
 * @see Inject
 *
 * @author devxb
 * @since 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
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
     * Specifies the package path into which this bean can be injected.<br>
     * This bean can be injected into all classes in the package.<br><br>
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
     * Set to Empty String[] by default.<br>
     * If both accessPackages() and accessClasses() are empty, this bean is accessible by all classes and packages.
     *
     * @return String[] Packages that can inject this bean
     *
     * @author devxb
     * @since 0.1
     */
    String[] accessPackages() default {};

    /**
     * Specifies the Class name with path into which this bean can be injected.<br>
     * This bean can be injected into only class. <br><br>
     *
     * .* expression is not allowed. <br><br>
     *
     * Set to Empty String[] by default. <br>
     * If both accessPackages() and accessClasses() are empty, this bean is accessible by all classes and packages.
     *
     * @return String[] Class name with path that can inject this bean
     */
    String[] accessClasses() default {};

}
