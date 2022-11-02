package org.jvault.factory;

import org.jvault.factory.buildinfo.VaultFactoryBuildInfo;
import org.jvault.vault.Vault;

/**
 * Receives the VaultFactoryBuildInfo or String (the name of the Vault if the Vault already exists) value as an input,
 * and returns the {@link org.jvault.vault.Vault}.
 *
 * @param <T> The implementation type of the Vault<P> interface.
 *
 * @implNote {@link org.jvault.factory.ClassVaultFactory}
 *
 * @see org.jvault.vault.Vault
 * @see org.jvault.factory.buildinfo.VaultFactoryBuildInfo
 *
 * @author devxb
 * @since 0.1
 */
public interface VaultFactory <T extends Vault<?>>{
    /**
     * Returns the Vault interface implementation class by receiving the VaultName. <br/>
     * This method is not to create a new vault, but to return the existing vault corresponding to the vaultName. <br/>
     * If the vaultName does not have a corresponding vault, throw IllegalStateException.
     *
     * @param vaultName The name of the vault to be returned.
     * @return <T> The implementation type of the Vault<P> interface.
     * @throws IllegalStateException Throw IllegalStateException if the corresponding vault does not exist for the vaultName.
     *
     * @author devxb
     * @since 0.1
     */
    T get(String vaultName) throws IllegalStateException;

    /**
     * Returns the Vault interface implementation class by receiving the VaultFactoryBuildInfo. <br/>
     * Vault Factory creates a new Vault based on VaultFactoryInfo. <br/>
     * If a vault with the name corresponding to "VaultFactoryBuildInfo.getVaultName()" already exists, the vault is returned.
     *
     * @param buildInfo The collection of information that VaultFactory needs to create Vault.
     * @return <T> The implementation type of the Vault<P> interface.
     *
     * @see org.jvault.factory.buildinfo.VaultFactoryBuildInfo
     *
     * @author devxb
     * @since 0.1
     */
    T get(VaultFactoryBuildInfo buildInfo);

}
