package org.jvault.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta-information used when creating a {@link org.jvault.factory.buildinfo.VaultFactoryBuildInfo} using a class. <br>
 * It can be created by passing the class marked as BeanArea as a parameter of the constructor of {@link org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo}.<br>
 * <br>
 * Example.
 * <pre>
 * {@code new AnnotationVaultFactoryBuildInfo(SomeBeanAreaMarked.class)}
 * </pre>
 * <br>
 *
 * A field marked as {@link BeanWire} in a class marked as BeanArea is created as a {@link InternalBean}. <br>
 * <br>
 * Example.
 * <pre>
 * {@code @BeanArea
 * public final class BeanAreaConfig{ @BeanWire private final Foo foo;}}
 * </pre>
 *
 * @see BeanWire
 * @see InternalBean
 * @see org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo
 *
 * @author devxb
 * @since 0.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanArea {

    /**
     * Returns the name of the {@link org.jvault.vault.Vault} to be created by {@link org.jvault.factory.VaultFactory}.
     *
     * @return String - vault name
     *
     * @author devxb
     * @since 0.1
     */
    String name() default "";

    /**
     * Returns the paths of packages that can be passed as parameters of the {@link org.jvault.vault.Vault}
     *
     * @return - String[] Packages injectable from vault
     *
     * @author devxb
     * @since 0.1
     */
    String[] accesses() default {};

}
