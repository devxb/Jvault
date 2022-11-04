package org.jvault.factory.buildinfo;

import org.jvault.annotation.InternalBean;
import org.jvault.beanloader.BeanLoadable;
import org.jvault.beanreader.BeanLocation;
import org.jvault.beanreader.BeanReader;
import org.jvault.beans.Type;
import org.jvault.exceptions.NoDefinedInternalBeanException;

import java.util.ArrayList;
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

    @Override
    public abstract String getVaultName();

    @Override
    public List<BeanLoadable> getBeanLoadables(){
        BeanReader beanReader = Accessors.BeanReaderAccessor.getAccessor().getBeanReader();
        List<Class<?>> classes = beanReader.read(new BeanLocation(){
            @Override
            public String[] getPackages() {
                return getPackagesImpl();
            }

            @Override
            public String[] getExcludePackages() {
                return getExcludePackagesImpl();
            }
        });

        return convertToBeanLoadables(classes);
    }

    private List<BeanLoadable> convertToBeanLoadables(List<Class<?>> classes){
        List<BeanLoadable> beanLoadables = new ArrayList<>();
        for(Class<?> cls : classes){
            InternalBean internalBean = cls.getDeclaredAnnotation(InternalBean.class);
            if(internalBean == null) throw new NoDefinedInternalBeanException(cls.getSimpleName());
            String name = convertToBeanName(cls.getSimpleName());
            if(!internalBean.name().equals("")) name = internalBean.name();
            Type type = internalBean.type();
            String[] accesses = internalBean.accesses();
            beanLoadables.add(
                    Accessors.BeanLoaderAccessor.getAccessor().getBeanLoadable(name, type, accesses, cls)
            );
        }
        return beanLoadables;
    }

    private String convertToBeanName(String name){
        return name.substring(0, 1).toLowerCase() + name.subSequence(1, name.length());
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
