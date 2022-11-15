package org.jvault.annotation;

import org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo;
import org.jvault.factory.extensible.Vault;
import org.jvault.factory.extensible.VaultFactoryBuildInfo;
import org.jvault.metadata.API;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that used creating a {@link VaultFactoryBuildInfo} with class-configuration. <br>
 * It can be created by passing the class marked as VaultConfiguration as a parameter of the constructor of {@link AnnotationVaultFactoryBuildInfo}.<br>
 * <br>
 * Example.
 * <pre>
 * {@code new AnnotationVaultFactoryBuildInfo(SomeVaultConfigurationMarked.class)}
 * </pre>
 * <br>
 *
 * A field marked as {@link BeanWire} in a class marked as VaultConfiguration is created as a {@link InternalBean}. <br>
 * <br>
 * Example.
 * <pre>
 * {@code @VaultConfiguration
 * public final class BeanAreaConfig{ @BeanWire private Foo foo; }}
 * </pre>
 *
 * @see BeanWire
 * @see InternalBean
 * @see AnnotationVaultFactoryBuildInfo
 *
 * @author devxb
 * @since 0.1
 */
@API
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VaultConfiguration {

    /**
     * Returns the name of the {@link Vault} to be created by {@link org.jvault.factory.VaultFactory}.
     *
     * @return String - vault name
     *
     * @author devxb
     * @since 0.1
     */
    String name() default "";

    /**
     * Returns the paths of packages that can be passed as parameters of the {@link Vault}
     *
     * @return - String[] Packages injectable from vault
     *
     * @author devxb
     * @since 0.1
     */
    String[] vaultAccessPackages() default {};

    /**
     * Returns the class name with path that can be passed as parameters of the {@link Vault}
     *
     * @return - String[] Class name with path injectable from vault
     *
     * @author devxb
     * @since 0.1
     */
    String[] vaultAccessClasses() default {};

}