package org.jvault.factory;

import org.jvault.factory.extensible.BeanLoader;
import org.jvault.factory.extensible.BuildStorage;
import org.jvault.factory.extensible.VaultFactoryBuildInfo;
import org.jvault.metadata.API;
import org.jvault.vault.Vault;
import org.jvault.vault.VaultType;

/**
 * receives VaultFactoryBuildInfo or String (the name of the Vault if the Vault already exists) value as an input,<br>
 * and returns {@link org.jvault.vault.ClassVault}.
 *
 * @author devxb
 * @see org.jvault.vault.ClassVault
 * @see org.jvault.factory.VaultFactory
 * @see VaultFactoryBuildInfo
 * @since 0.1
 */
@API
public final class TypeVaultFactory implements VaultFactory<VaultType> {

    private static final TypeVaultFactory INSTANCE = new TypeVaultFactory();
    private final BuildStorage BUILD_STORAGE;

    {
        BUILD_STORAGE = Accessors.StorageAccessor.getAccessor().getBuildStorage();
    }
    private TypeVaultFactory() {}

    /**
     * The ClassVault Factory is managed by Singleton, and all constructors are private, so execute this method to obtain the ClassVaultFactory.
     *
     * @return ClassVaultFactory
     */
    public static TypeVaultFactory getInstance() {
        return INSTANCE;
    }

    /**
     * @param vaultName The name of the vault to be returned.
     * @param vaultType Type of vault to be created.
     * @return ClassVault that implementation type of the Vault<P> interface.
     * @throws IllegalStateException Throw IllegalStateException if the corresponding vault does not exist for the vaultName.
     * @author devxb
     * @since 0.1
     */
    @Override
    public <R extends Vault<?>> R get(String vaultName, VaultType vaultType) throws IllegalStateException {
        BuildStorage.StorageInfo storageInfo = BUILD_STORAGE.get(vaultName);
        throwIfDoesNotExistVault(vaultName, storageInfo);

        return (R) buildVault(storageInfo, vaultType);
    }

    private void throwIfDoesNotExistVault(String vaultName, BuildStorage.StorageInfo storageInfo){
        if (storageInfo == null)
            throw new IllegalStateException("Can not find vaults named \"" + vaultName + "\"");
    }

    /**
     * @param buildInfo The collection of information that VaultFactory needs to create Vault.
     * @param vaultType Type of vault to be created.
     *
     * @return Vault that implementation type of the Vault<P> interface.
     *
     * @author devxb
     * @see VaultFactoryBuildInfo
     * @since 0.1
     */
    @Override
    public <R extends Vault<?>> R get(VaultFactoryBuildInfo buildInfo, VaultType vaultType) {
        BuildStorage.StorageInfo storageInfo = BUILD_STORAGE.get(buildInfo.getVaultName());
        if (storageInfo != null) return (R) buildVault(storageInfo, vaultType);
        return (R) registerVault(buildInfo, vaultType);
    }

    private synchronized Vault<?> registerVault(VaultFactoryBuildInfo buildInfo, VaultType vaultType) {
        BuildStorage.StorageInfo storageInfo = BUILD_STORAGE.get(buildInfo.getVaultName());
        if (storageInfo != null) return buildVault(storageInfo, vaultType);

        BeanLoader beanLoader = Accessors.BeanLoaderAccessor.getAccessor().getBeanLoader();

        BUILD_STORAGE.cache(BuildStorage.StorageInfo.getBuilder()
                        .name(buildInfo.getVaultName())
                        .accessClasses(buildInfo.getVaultAccessClasses())
                        .accessPackages(buildInfo.getVaultAccessPackages())
                        .beans(beanLoader.load(buildInfo.getBeanClasses()))
                        .build());

        return buildVault(BUILD_STORAGE.get(buildInfo.getVaultName()), vaultType);
    }

    private Vault<?> buildVault(BuildStorage.StorageInfo storageInfo, VaultType vaultType){
        return Accessors.VaultAccessor.getAccessor().getBuilder(vaultType)
                .name(storageInfo.getName())
                .accessClasses(storageInfo.getAccessClasses())
                .accessPackages(storageInfo.getAccessPackages())
                .beans(storageInfo.getBeans())
                .build();
    }

}
