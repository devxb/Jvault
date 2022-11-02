package org.jvault.factory;

import org.jvault.beanloader.BeanLoader;
import org.jvault.beanreader.BeanReader;
import org.jvault.beans.Bean;
import org.jvault.factory.buildinfo.VaultFactoryBuildInfo;
import org.jvault.vault.ClassVault;
import org.jvault.vault.VaultType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * receives VaultFactoryBuildInfo or String (the name of the Vault if the Vault already exists) value as an input,<br/>
 * and returns {@link org.jvault.vault.ClassVault}.
 *
 * @see org.jvault.vault.ClassVault
 * @see org.jvault.factory.VaultFactory
 * @see org.jvault.factory.buildinfo.VaultFactoryBuildInfo
 *
 * @author devxb
 * @since 0.1
 */
public final class ClassVaultFactory implements VaultFactory <ClassVault>{

    private final Map<String, ClassVault> VAULTS;
    private final BeanReader BEAN_READER;
    private final BeanLoader BEAN_LOADER;
    private static final ClassVaultFactory INSTANCE = new ClassVaultFactory();
    {
        VAULTS = new HashMap<>();
        BEAN_READER = Accessors.BeanReaderAccessor.getAccessor().getBeanReader();
        BEAN_LOADER = Accessors.BeanLoaderAccessor.getAccessor().getBeanLoader();
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
     * @see org.jvault.factory.buildinfo.VaultFactoryBuildInfo
     *
     * @author devxb
     * @since 0.1
     */
    @Override
    public ClassVault get(VaultFactoryBuildInfo buildInfo) {
        if(VAULTS.containsKey(buildInfo.getVaultName())) return VAULTS.get(buildInfo.getVaultName());

        List<Class<?>> classes = BEAN_READER.read(buildInfo.getBeanLocation());
        Map<String, Bean> beans = BEAN_LOADER.load(classes);

        ClassVault classVault = Accessors.VaultAccessor.getAccessor().getBuilder(VaultType.CLASS, ClassVault.class)
                .name(buildInfo.getVaultName())
                .injectAccesses(buildInfo.getInjectAccesses())
                .beans(beans).build();

        VAULTS.put(buildInfo.getVaultName(), classVault);

        return classVault;
    }

}
