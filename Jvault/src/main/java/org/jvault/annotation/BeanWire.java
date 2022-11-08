package org.jvault.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta-information used in the class marked with {@link BeanArea} <br>
 * This information is read from the {@link org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo} class,<br>
 * and classes that will become {@link InternalBean} are found. <br> <br>
 *
 * This annotation can be declared on a field, <br>
 * and a bean is created based on the @InternalBean information declared in the class corresponding to the marked field.<br><br>
 *
 * Example. <br>
 * <pre>
 * {@code @BeanWire private Foo foo;}
 * </pre>
 * As above, if @BeanWire is marked in a class marked with @BeanArea, and the @InternalBean information of class Foo is as follows,
 * <pre>
 * {@code @InternalBean(name = "fOO",
 *                      type = Type.SINGLETON,
 *                      accesses = {"org.jvault.*"})
 * public class Foo{}}
 * </pre>
 * Bean is created with the values name = "fOO", type = singleton, accesses = {"org.jvault.*"} <br> <br>
 *
 * This means that the class of fields marked with @BeanWire must be marked with @InternalBean. <br>
 * If not, a {@link org.jvault.exceptions.NoDefinedInternalBeanException} is thrown.
 *
 * @see BeanArea
 * @see InternalBean
 *
 * @author devxb
 * @since 0.1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanWire {}