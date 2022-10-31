package org.jvault.factory;

import org.jvault.factory.buildinfo.VaultFactoryBuildInfo;
import org.jvault.vault.Vault;

public interface VaultFactory <T extends Vault<?>>{
    T get(String vaultName) throws IllegalStateException;
    T get(VaultFactoryBuildInfo buildInfo);

}
