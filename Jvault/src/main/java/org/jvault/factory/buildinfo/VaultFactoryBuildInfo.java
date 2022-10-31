package org.jvault.factory.buildinfo;

import org.jvault.beanreader.BeanReader;

public interface VaultFactoryBuildInfo {

    String getVaultName();
    BeanReader.BeanLocation getBeanLocation();
    String[] getInjectAccesses();

}
