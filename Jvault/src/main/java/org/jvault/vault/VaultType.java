package org.jvault.vault;

import org.jvault.factory.extensible.Vault;
import org.jvault.metadata.API;

/**
 * Types of Vault interface implementations that can be created.<br>
 * To create a Vault implementation using the VaultType enum, see {@link org.jvault.factory.TypeVaultFactory}.
 *
 * @author devxb
 * @since 0.1
 */
@API
public enum VaultType {

    /**
     * The enum type used to get the ClassVault that make up the ClassVault.Builder that creates the ClassVault.
     *
     * @since 0.1
     */
    CLASS(new Vault.Builder<ClassVault>() {
        @Override
        public ClassVault build() {
            return new ClassVault(this);
        }
    }),
    /**
     * The enum type used to get the InstanceVault that make up the InstanceVault.Builder that creates the InstanceVault.
     *
     * @since 0.1
     */
    INSTANCE(new Vault.Builder<InstanceVault>() {
        @Override
        public InstanceVault build() {
            return new InstanceVault(this);
        }
    });

    private final Vault.Builder<? extends Vault<?>> BUILDER;

    <S extends Vault<?>> VaultType(Vault.Builder<S> builder) {
        BUILDER = builder;
    }

    @SuppressWarnings("unchecked")
    <S extends Vault<?>> Vault.Builder<S> getBuilder() {
        return (Vault.Builder<S>) BUILDER;
    }

}
