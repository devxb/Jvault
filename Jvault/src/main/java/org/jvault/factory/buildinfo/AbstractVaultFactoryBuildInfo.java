package org.jvault.factory.buildinfo;

import org.jvault.beanreader.BeanLocation;
import org.jvault.beanreader.BeanReader;

import java.util.List;

/**
 * Abstract class to help implement {@link VaultFactoryBuildInfo} interface.
 * The getInjectAccesses() method and getClasses() method is implemented.
 *
 * @see org.jvault.factory.VaultFactory
 * @see org.jvault.beanreader.BeanLocation
 *
 * @author devxb
 * @since 0.1
 */
public abstract class AbstractVaultFactoryBuildInfo implements VaultFactoryBuildInfo {

    public abstract String getVaultName();

    public List<Class<?>> getClasses(){
        BeanReader beanReader = Accessors.BeanReaderAccessor.getAccessor().getBeanReader();
        return beanReader.read(new BeanLocation(){

            @Override
            public String[] getPackages() {
                return getPackagesImpl();
            }

            @Override
            public String[] getExcludePackages() {
                return getExcludePackagesImpl();
            }
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


    public String[] getInjectAccesses(){
        return new String[0];
    }

}
