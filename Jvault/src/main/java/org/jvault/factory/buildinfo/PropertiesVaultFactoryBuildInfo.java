package org.jvault.factory.buildinfo;

import org.jvault.beanreader.BeanReader;

import java.io.*;
import java.util.Properties;

public final class PropertiesVaultFactoryBuildInfo implements VaultFactoryBuildInfo{

    private final String VAULT_NAME;
    private final BeanReader.BeanLocation BEAN_LOCATION;
    private final String[] INJECT_ACCESSES;

    @Override
    public String getVaultName() {
        return VAULT_NAME;
    }

    @Override
    public BeanReader.BeanLocation getBeanLocation() {
        return BEAN_LOCATION;
    }

    @Override
    public String[] getInjectAccesses() {
        return INJECT_ACCESSES;
    }

    public PropertiesVaultFactoryBuildInfo(String propertiesSrc){
        try(InputStream input = new FileInputStream(propertiesSrc)){
            Properties properties = new Properties();
            properties.load(input);
            VAULT_NAME = getVaultName(propertiesSrc, properties);
            BEAN_LOCATION = getBeanLocation(properties);
            INJECT_ACCESSES = getInjectAccesses(properties);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cant not find properties located at \"" + propertiesSrc + "\"");
        }
    }

    private String getVaultName(String propertiesSrc, Properties properties){
        String vaultName = properties.getProperty("org.jvault.vault.name");
        if(vaultName == null) throw new IllegalStateException("Can not find \"org.jvault.name\" at \"" + propertiesSrc + "\"");
        return vaultName;
    }

    private BeanReader.BeanLocation getBeanLocation(Properties properties){
        return new BeanReader.BeanLocation(){

            @Override
            public String[] getRootPackage() {
                String[] packages = properties.getProperty("org.jvault.reader.scan.packages", "").split(",");
                for(int i = 0; i < packages.length; i++) packages[i] = packages[i].strip();
                for(int i = 0; i < packages.length; i++) System.out.println(packages[i]);
                return packages;
            }

            @Override
            public String[] getExcludePackages() {
                String[] packages = properties.getProperty("org.jvault.reader.exclude.packages", "").split(",");
                for(int i = 0; i < packages.length; i++) packages[i] = packages[i].strip();
                return packages;
            }
        };
    }

    private String[] getInjectAccesses(Properties properties){
        String[] injectAccesses = properties.getProperty("org.jvault.vault.inject.accesses", "").split(",");
        for(int i = 0; i < injectAccesses.length; i++)
            injectAccesses[i] = injectAccesses[i].strip();
        return injectAccesses;
    }

}
