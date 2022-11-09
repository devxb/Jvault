package org.jvault.factory.extensible;

import org.jvault.factory.buildinfo.AbstractVaultFactoryBuildInfo;
import org.jvault.factory.buildinfo.PropertiesVaultFactoryBuildInfo;
import org.jvault.factory.buildinfo.extensible.BeanLocationExtensiblePoint;
import org.jvault.metadata.ExtensiblePoint;

import java.util.List;

/**
 * One of the parameters that {@link org.jvault.factory.VaultFactory} receives. <br>
 * This interface returns the name of the vault to be created,<br>
 * location information of beans,<br>
 * and package information that can use the vault.
 *
 * @see PropertiesVaultFactoryBuildInfo
 * @see AbstractVaultFactoryBuildInfo
 * @see BeanLocationExtensiblePoint
 *
 * @author devxb
 * @since 0.1
 */
@ExtensiblePoint
public interface VaultFactoryBuildInfoExtensiblePoint {

    /**
     * Method that returns the name of the {@link org.jvault.vault.Vault} to be created by {@link org.jvault.factory.VaultFactory}. <br>
     * You can find Vault from VaultFactory using this name.
     *
     * @return String - Name of the vault to be created
     *
     * @author devxb
     * @since 0.1
     */
    String getVaultName();

    /**
     * Method that returns class that convert and register to Bean <br>
     *
     * @return {@link List}
     *
     * @author devxb
     * @since 0.1
     */
    List<Class<?>> getBeanClasses();

    /**
     * Method that returns the accessible package paths to which {@link org.jvault.vault.Vault} created by {@link org.jvault.factory.VaultFactory} <br>
     * Only the path of the class specified here can be passed as a parameter of the vault. <br>
     * If empty, any class can be passed as a parameter.<br>
     *
     * @return String[] Packages of classes that can be passed as parameters to the {@link org.jvault.vault.Vault}
     *
     * @author devxb
     * @since 0.1
     */
    String[] getVaultAccessPackages();

    /**
     * Method that returns the accessible class name with path to which {@link org.jvault.vault.Vault} created by {@link org.jvault.factory.VaultFactory} <br>
     * Only the path of the class specified here can be passed as a parameter of the vault.<br>
     * If empty, any class can be passed as a parameter.<br>
     *
     * @return String[] The name with path of the class that can be passed as parameters to the {@link org.jvault.vault.Vault}
     *
     * @author devxb
     * @since 0.1
     */
    String[] getVaultAccessClasses();

}
