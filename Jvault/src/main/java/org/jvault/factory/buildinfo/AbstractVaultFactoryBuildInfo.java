package org.jvault.factory.buildinfo;

import org.jvault.factory.buildinfo.extensible.BeanLocationExtensiblePoint;
import org.jvault.factory.buildinfo.extensible.BeanReaderExtensiblePoint;
import org.jvault.factory.extensible.VaultFactoryBuildInfoExtensiblePoint;
import org.jvault.metadata.API;

import java.util.List;

/**
 * Abstract class to help implement {@link VaultFactoryBuildInfoExtensiblePoint} interface.
 * The getInjectAccesses() method and getClasses() method is implemented.
 *
 * @see org.jvault.factory.VaultFactory
 * @see BeanLocationExtensiblePoint
 *
 * @author devxb
 * @since 0.1
 */
@API
public abstract class AbstractVaultFactoryBuildInfo implements VaultFactoryBuildInfoExtensiblePoint {

    @Override
    public abstract String getVaultName();

    @Override
    public List<Class<?>> getBeanClasses(){
        BeanReaderExtensiblePoint beanReader = Accessors.RuntimeExtensionAccessor.getAccessor().getRuntimeExtension().getExtension(BeanReaderExtensiblePoint.class);
        if(beanReader == null) beanReader = Accessors.BeanReaderAccessor.getAccessor().getBeanReader();
        return beanReader.read(new BeanLocationExtensiblePoint(){
            @Override
            public String[] getPackages() {
                return getPackagesImpl();
            }

            @Override
            public String[] getExcludePackages() {
                return getExcludePackagesImpl();
            }

            @Override
            public String[] getClasses(){return getClassesImpl();}
        });
    }

    /**
     * This method implementation should return package paths where Beans exist.<br>
     * .* expression can be used at the end of the path,<br>
     * and if there is a .* expression at the end of the path,<br>
     * all classes in the last leaf directory including the path are scanned.<br><br>
     *
     * BuildInfo will create a class that will be Beans based on this information.<br>
     *
     * @return String[] Package paths where the scan target beans exists
     */
    protected abstract String[] getPackagesImpl();

    /**
     * The implementation of this method should return the package path of the beans to exclude from the scan.<br>
     * * expression can be used at the end of the package path,<br>
     * and if a .* expression is used at the end of the package path,<br>
     * the class in the last leaf directory including the path is excluded from empty scanning.
     *
     * @return String[] Package paths with beans to exclude from scanning
     */
    protected abstract String[] getExcludePackagesImpl();

    /**
     * The implementation of this method should return the path and package of the class.<br>
     * The bean of the returned information will be registered in the Vault.
     *
     * @return String[] Class name with package
     */
    protected abstract String[] getClassesImpl();

    @Override
    public String[] getVaultAccessPackages(){
        return new String[0];
    }

    @Override
    public String[] getVaultAccessClasses(){
        return new String[0];
    }

}
