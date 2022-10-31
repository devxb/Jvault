package org.jvault.factory.buildinfo;

import org.jvault.beanreader.BeanReader;

public abstract class AbstractVaultFactoryBuildInfo implements VaultFactoryBuildInfo {

    public abstract String getVaultName();

    public abstract BeanReader.BeanLocation getBeanLocation();

    public String[] getInjectAccesses(){
        return new String[0];
    }

}
