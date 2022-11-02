package org.jvault.factory;

import org.jvault.beanloader.BeanLoader;
import org.jvault.beanreader.BeanReader;
import org.jvault.beans.Bean;
import org.jvault.factory.buildinfo.VaultFactoryBuildInfo;
import org.jvault.vault.ClassVault;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static ClassVaultFactory getInstance(){
        return INSTANCE;
    }

    @Override
    public ClassVault get(String vaultName) throws IllegalStateException {
        if(!VAULTS.containsKey(vaultName)) throw new IllegalStateException("Can not find vaults named \"" + vaultName +"\"");
        return VAULTS.get(vaultName);
    }

    @Override
    public ClassVault get(VaultFactoryBuildInfo buildInfo) {
        if(VAULTS.containsKey(buildInfo.getVaultName())) return VAULTS.get(buildInfo.getVaultName());

        List<Class<?>> classes = BEAN_READER.read(buildInfo.getBeanLocation());
        Map<String, Bean> beans = BEAN_LOADER.load(classes);

        ClassVault classVault = ClassVault.getBuilder()
                .name(buildInfo.getVaultName())
                .injectAccesses(buildInfo.getInjectAccesses())
                .beans(beans).build();

        VAULTS.put(buildInfo.getVaultName(), classVault);

        return classVault;
    }

}
