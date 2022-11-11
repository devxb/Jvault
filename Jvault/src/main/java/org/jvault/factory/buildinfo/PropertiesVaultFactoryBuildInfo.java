package org.jvault.factory.buildinfo;

import org.jvault.factory.extensible.VaultFactoryBuildInfo;
import org.jvault.metadata.API;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Receive the path of *.properties file and specify the return value of the {@link VaultFactoryBuildInfo} methods.
 *
 * <hr>
 * Below is examples of .properties phrases
 * <br><br>
 * <p>
 * org.jvault.vault.name = SOME_VAULT_NAME <br>
 * - "getVaultName()" method returns a value of org.jvault.vault.name,
 * which will be the name of the vault that was created.
 * <br><br>
 * <p>
 * org.jvault.vault.access.packages = org.jvault.factory, org.jvault.util.* <br>
 * - "getVaultAccessPackages()" method returns a vaule of org.jvault.vault.access.packages,
 * which will be specified range of packages that Vault can inject.
 * <br><br>
 * <p>
 * org.jvault.vault.access.classes = org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo <br>
 * - "getVaultAccessClasses()" method returns a vaule of org.jvault.vault.access.classes,
 * which will be specified class name with path that Vault can inject.
 * <br><br>
 * <p>
 * org.jvault.reader.packages = org.jvault, org.jvault.struct.scanwithproperties, org.jvault.* <br>
 * - The package path where the beans located. <br>
 * When you use an ".*" expression, it find beans in all child directories and descendant directories to leaf directories, including the path before the expression.
 * <br>
 * org.jvault.reader.exclude.packages = org.jvault.factory, org.jvault.beans.* <br>
 * - The path of the package to exclude from the scan. <br>
 * When you use an ".*" expression, it find beans in all child directories and descendant directories to leaf directories, including the path before the expression.
 * <br><br>
 * <p>
 * org.jvault.reader.classes = org.jvault.factory.ClassVaultFactory, org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo <br>
 * - The class path with class name where the bean located. <br>
 *
 * <br>
 * <hr>
 *
 * @author devxb
 * @see org.jvault.factory.VaultFactory
 * @since 0.1
 */
@API
public final class PropertiesVaultFactoryBuildInfo extends AbstractVaultFactoryBuildInfo {

    private final String VAULT_NAME;
    private final String[] SCANNING_PACKAGES;
    private final String[] SCANNING_CLASSES;
    private final String[] EXCLUDE_SCANNING_PACKAGES;
    private final String[] VAULT_ACCESS_PACKAGES;
    private final String[] VAULT_ACCESS_CLASSES;

    /**
     * Receive the path of *.properties file and specify the return value of the {@link VaultFactoryBuildInfo} methods.
     *
     * @param propertiesSrc the path of *.properties file
     * @throws IllegalArgumentException thrown when, can not find properties file.
     * @author devxb
     * @since 0.1
     */
    public PropertiesVaultFactoryBuildInfo(String propertiesSrc) {
        try (InputStream input = Files.newInputStream(Paths.get(propertiesSrc))) {
            Properties properties = new Properties();
            properties.load(input);
            VAULT_NAME = getVaultName(propertiesSrc, properties);
            SCANNING_PACKAGES = getScanningPackages(properties);
            SCANNING_CLASSES = getScanningClasses(properties);
            EXCLUDE_SCANNING_PACKAGES = getExcludeScanningPackages(properties);
            VAULT_ACCESS_PACKAGES = getVaultAccessPackages(properties);
            VAULT_ACCESS_CLASSES = getVaultAccessClasses(properties);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cant not find properties located at \"" + propertiesSrc + "\"");
        }
    }

    private String getVaultName(String propertiesSrc, Properties properties) {
        String vaultName = properties.getProperty("org.jvault.vault.name");
        if (vaultName == null)
            throw new IllegalStateException("Can not find \"org.jvault.name\" at \"" + propertiesSrc + "\"");
        return vaultName;
    }

    private String[] getScanningPackages(Properties properties) {
        String[] packages = properties.getProperty("org.jvault.reader.packages", "").split(",");
        for (int i = 0; i < packages.length; i++) packages[i] = packages[i].trim();
        return packages;
    }

    private String[] getScanningClasses(Properties properties) {
        String[] classes = properties.getProperty("org.jvault.reader.classes", "").split(",");
        for (int i = 0; i < classes.length; i++) classes[i] = classes[i].trim();
        return classes;
    }

    private String[] getExcludeScanningPackages(Properties properties) {
        String[] packages = properties.getProperty("org.jvault.reader.exclude.packages", "").split(",");
        for (int i = 0; i < packages.length; i++) packages[i] = packages[i].trim();
        return packages;
    }

    private String[] getVaultAccessPackages(Properties properties) {
        String[] injectAccesses = properties.getProperty("org.jvault.vault.access.packages", "").split(",");
        for (int i = 0; i < injectAccesses.length; i++)
            injectAccesses[i] = injectAccesses[i].trim();
        return injectAccesses;
    }

    private String[] getVaultAccessClasses(Properties properties) {
        String[] injectAccesses = properties.getProperty("org.jvault.vault.access.classes", "").split(",");
        for (int i = 0; i < injectAccesses.length; i++)
            injectAccesses[i] = injectAccesses[i].trim();
        return injectAccesses;
    }

    @Override
    public String getVaultName() {
        return VAULT_NAME;
    }

    @Override
    public String[] getVaultAccessPackages() {
        return VAULT_ACCESS_PACKAGES;
    }

    @Override
    public String[] getVaultAccessClasses() {
        return VAULT_ACCESS_CLASSES;
    }

    @Override
    protected String[] getPackagesImpl() {
        return SCANNING_PACKAGES;
    }

    @Override
    protected String[] getExcludePackagesImpl() {
        return EXCLUDE_SCANNING_PACKAGES;
    }

    @Override
    protected String[] getClassesImpl() {
        return SCANNING_CLASSES;
    }

}
