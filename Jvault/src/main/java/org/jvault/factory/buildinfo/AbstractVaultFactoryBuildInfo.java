package org.jvault.factory.buildinfo;

import org.jvault.beanreader.BeanReader;

/**
 * Abstract class to help implement {@link VaultFactoryBuildInfo} interface.
 * The getInjectAccesses() method is implemented.
 *
 * @see org.jvault.factory.VaultFactory
 * @see org.jvault.beanreader.BeanReader.BeanLocation
 *
 * @author devxb
 * @since 0.1
 */
public abstract class AbstractVaultFactoryBuildInfo implements VaultFactoryBuildInfo {

    public abstract String getVaultName();

    public abstract BeanReader.BeanLocation getBeanLocation();

    public String[] getInjectAccesses(){
        return new String[0];
    }

}
