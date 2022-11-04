package org.jvault.factory.buildinfo;

import org.jvault.beanloader.BeanLoadable;
import org.jvault.beanreader.BeanLocation;

import java.util.List;

/**
 * One of the parameters that {@link org.jvault.factory.VaultFactory} receives. <br>
 * This interface returns the name of the vault to be created,<br>
 * location information of beans,<br>
 * and package information that can use the vault.
 *
 * @see org.jvault.factory.buildinfo.PropertiesVaultFactoryBuildInfo
 * @see org.jvault.factory.buildinfo.AbstractVaultFactoryBuildInfo
 * @see org.jvault.beanreader.BeanLocation
 *
 * @author devxb
 * @since 0.1
 */
public interface VaultFactoryBuildInfo {

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
     * Method that returns bean-info that convert and register to Beans <br>
     * The {@link org.jvault.factory.VaultFactory} generates beans based on the return value of this method.
     *
     * @return {@link List}
     *
     * @author devxb
     * @since 0.1
     */
    List<BeanLoadable> getBeanLoadables();

    /**
     * Method that returns the accessible package paths to which {@link org.jvault.vault.Vault} created by {@link org.jvault.factory.VaultFactory} <br>
     * If it's empty, it can be accessed from any package.
     *
     * @return String[]
     *
     * @author devxb
     * @since 0.1
     */
    String[] getInjectAccesses();

}
