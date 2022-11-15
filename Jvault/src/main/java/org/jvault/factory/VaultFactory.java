package org.jvault.factory;

import org.jvault.factory.extensible.VaultFactoryBuildInfo;
import org.jvault.metadata.API;
import org.jvault.factory.extensible.Vault;

/**
 * Receives the VaultFactoryBuildInfo or String (the name of the Vault if the Vault already exists) value as an input,
 * and returns the {@link Vault}.
 *
 * @param <T> Type of vault to be created.
 *
 * @see Vault
 * @see VaultFactoryBuildInfo
 *
 * @author devxb
 * @since 0.1
 */
@API
public interface VaultFactory <T>{
    /**
     * Returns the Vault interface implementation by receiving the vaultName and param(type of vault) <br>
     * This method is not to create a new vault, but to return the existing vault corresponding to the vaultName. <br>
     * If none of the vault matched with vaultName, throw IllegalStateException.
     *
     * @param <R> The implementation of Vault.
     * @param vaultName The name of the vault to be returned.
     * @param param Type of vault to be created.
     *
     * @return R The implementation type of the Vault<P> interface.
     *
     * @throws IllegalStateException Throw IllegalStateException if the corresponding vault does not exist for the vaultName.
     *
     * @author devxb
     * @since 0.1
     */
    <R extends Vault<?>> R get(String vaultName, T param) throws IllegalStateException;

    /**
     * Returns the Vault interface implementation class by receiving the VaultFactoryBuildInfo and param(type of vault) <br>
     * Vault Factory creates a new Vault based on VaultFactoryInfo. <br>
     * If a vault with the name corresponding to "VaultFactoryBuildInfo.getVaultName()" already exists, the vault is returned.
     *
     * @param <R> The implementation of Vault.
     * @param buildInfo The collection of information that VaultFactory needs to create Vault.
     * @param param Type of vault to be created.
     *
     * @return R The implementation type of the Vault<P> interface.
     *
     * @see VaultFactoryBuildInfo
     *
     * @author devxb
     * @since 0.1
     */
    <R extends Vault<?>> R get(VaultFactoryBuildInfo buildInfo, T param);

}
