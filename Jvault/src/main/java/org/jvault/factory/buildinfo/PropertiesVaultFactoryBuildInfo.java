package org.jvault.factory.buildinfo;

import org.jvault.beanreader.BeanLocation;

import java.io.*;
import java.util.Properties;

/**
 * Receive the path of *.properties file and specify the return value of the {@link VaultFactoryBuildInfo} methods.
 *
 * <hr/>
 * Below is examples of .properties phrases
 * <br/><br/>
 *
 * org.jvault.vault.name = SOME_VAULT_NAME <br/>
 * "getVaultName()" method returns a value of org.jvault.vault.name,
 * which will be the name of the vault that was created.
 * <br/><br/>
 *
 * org.jvault.vault.inject.accesses = org.jvault.factory, org.jvault.util.* <br/>
 * "getInjectAccesses()" method returns a vaule of org.jvault.vault.inject.accesses,
 * which will be specified range of packages that Vault Can inject.
 * <br/><br/>
 *
 * org.jvault.reader.packages = org.jvault, org.jvault.struct.scanwithproperties, org.jvault.* <br/>
 * The package path where the beans located. <br/>
 * When you use an ".*" expression, it find beans in all child directories and descendant directories to leaf directories, including the path before the expression.
 * <br/>
 * org.jvault.reader.exclude.packages = org.jvault.factory, org.jvault.beans.* <br/>
 * The path of the package to exclude from the scan. <br/>
 * When you use an ".*" expression, it find beans in all child directories and descendant directories to leaf directories, including the path before the expression.
 * <br/>
 * "getBeanLocation()" method returns a vaule of BeanLocation object created based on the above two information.
 * <br/>
 * <hr/>
 * @see org.jvault.factory.VaultFactory
 * @see org.jvault.beanreader.BeanLocation
 *
 * @author devxb
 * @since 0.1
 */
public final class PropertiesVaultFactoryBuildInfo implements VaultFactoryBuildInfo{

    private final String VAULT_NAME;
    private final BeanLocation BEAN_LOCATION;
    private final String[] INJECT_ACCESSES;

    @Override
    public String getVaultName() {
        return VAULT_NAME;
    }

    @Override
    public BeanLocation getBeanLocation() {
        return BEAN_LOCATION;
    }

    @Override
    public String[] getInjectAccesses() {
        return INJECT_ACCESSES;
    }

    /**
     * Receive the path of *.properties file and specify the return value of the {@link VaultFactoryBuildInfo} methods.
     *
     * @param propertiesSrc the path of *.properties file
     */
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

    private BeanLocation getBeanLocation(Properties properties){
        return new BeanLocation(){

            @Override
            public String[] getPackages() {
                String[] packages = properties.getProperty("org.jvault.reader.packages", "").split(",");
                for(int i = 0; i < packages.length; i++) packages[i] = packages[i].strip();
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
