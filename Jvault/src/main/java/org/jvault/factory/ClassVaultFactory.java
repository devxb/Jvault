package org.jvault.factory;

import org.jvault.factory.extensible.VaultFactoryBuildInfoExtensiblePoint;
import org.jvault.factory.extensible.BeanLoaderExtensiblePoint;
import org.jvault.metadata.API;
import org.jvault.vault.ClassVault;
import org.jvault.vault.VaultType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * receives VaultFactoryBuildInfo or String (the name of the Vault if the Vault already exists) value as an input,<br>
 * and returns {@link org.jvault.vault.ClassVault}.
 *
 * @see org.jvault.vault.ClassVault
 * @see org.jvault.factory.VaultFactory
 * @see VaultFactoryBuildInfoExtensiblePoint
 *
 * @author devxb
 * @since 0.1
 */
@API
public final class ClassVaultFactory implements VaultFactory <ClassVault>{

    private final ConcurrentMap<String, ClassVault> VAULTS;
    private static final ClassVaultFactory INSTANCE = new ClassVaultFactory();
    {
        VAULTS = new ConcurrentHashMap<>();
    }

    private ClassVaultFactory(){}

    /**
     * The ClassVault Factory is managed by Singleton, and all constructors are private, so execute this method to obtain the ClassVaultFactory.
     *
     * @return ClassVaultFactory
     */
    public static ClassVaultFactory getInstance(){
        return INSTANCE;
    }

    /**
     * @param vaultName The name of the vault to be returned.
     * @return ClassVault that implementation type of the Vault<P> interface.
     * @throws IllegalStateException Throw IllegalStateException if the corresponding vault does not exist for the vaultName.
     *
     * @author devxb
     * @since 0.1
     */
    @Override
    public ClassVault get(String vaultName) throws IllegalStateException {
        if(!VAULTS.containsKey(vaultName)) throw new IllegalStateException("Can not find vaults named \"" + vaultName +"\"");
        return VAULTS.get(vaultName);
    }

    /**
     * @param buildInfo The collection of information that VaultFactory needs to create Vault.
     * @return ClassVault that implementation type of the Vault<P> interface.
     *
     * @see VaultFactoryBuildInfoExtensiblePoint
     *
     * @author devxb
     * @since 0.1
     */
    @Override
    public ClassVault get(VaultFactoryBuildInfoExtensiblePoint buildInfo) {
        if(VAULTS.containsKey(buildInfo.getVaultName())) return VAULTS.get(buildInfo.getVaultName());
        return registerClassVault(buildInfo);
    }

    private synchronized ClassVault registerClassVault(VaultFactoryBuildInfoExtensiblePoint buildInfo){
        if(VAULTS.containsKey(buildInfo.getVaultName())) return VAULTS.get(buildInfo.getVaultName());
        BeanLoaderExtensiblePoint beanLoader = Accessors.BeanLoaderAccessor.getAccessor().getBeanLoader();

        ClassVault classVault = Accessors.VaultAccessor.getAccessor().getBuilder(VaultType.CLASS, ClassVault.class)
                .name(buildInfo.getVaultName())
                .accessPackages(buildInfo.getVaultAccessPackages())
                .accessClasses(buildInfo.getVaultAccessClasses())
                .beans(beanLoader.load(buildInfo.getBeanClasses())).build();

        VAULTS.put(buildInfo.getVaultName(), classVault);

        return classVault;
    }

}
