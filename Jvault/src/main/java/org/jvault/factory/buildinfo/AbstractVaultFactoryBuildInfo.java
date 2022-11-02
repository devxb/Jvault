package org.jvault.factory.buildinfo;

import org.jvault.beanreader.BeanLocation;

/**
 * Abstract class to help implement {@link VaultFactoryBuildInfo} interface.
 * The getInjectAccesses() method is implemented.
 *
 * @see org.jvault.factory.VaultFactory
 * @see org.jvault.beanreader.BeanLocation
 *
 * @author devxb
 * @since 0.1
 */
public abstract class AbstractVaultFactoryBuildInfo implements VaultFactoryBuildInfo {

    public abstract String getVaultName();

    public abstract BeanLocation getBeanLocation();

    public String[] getInjectAccesses(){
        return new String[0];
    }

}
