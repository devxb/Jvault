package org.jvault.factory.buildinfo;

import org.jvault.annotation.BeanArea;
import org.jvault.annotation.BeanWire;
import org.jvault.annotation.InternalBean;
import org.jvault.beanloader.BeanLoadable;
import org.jvault.beans.Type;
import org.jvault.exceptions.InvalidAnnotationConfigClassException;
import org.jvault.exceptions.NoDefinedInternalBeanException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * A class that reads the {@link BeanWire} marking information of the class marked with {@link BeanArea} and returns the BuildInfo method.<br><br>
 *
 * Thrown {@link InvalidAnnotationConfigClassException} if an unmarked {@link BeanArea} parameter is passed to the constructor.
 *
 * @see BeanArea
 * @see BeanWire
 * @see InvalidAnnotationConfigClassException
 *
 * @author devxb
 * @since 0.1
 */
public final class AnnotationVaultFactoryBuildInfo implements VaultFactoryBuildInfo {

    private final String VAULT_NAME;
    private final List<BeanLoadable> BEAN_LOAD_ABLES;
    private final String[] VAULT_ACCESS_PACKAGES;
    private final String[] VAULT_ACCESS_CLASSES;

    /**
     * A class marked with {@link BeanArea} must be passed as a parameter. <br>
     *
     * It reads the field information marked with {@link BeanWire} and generates the return information of the method.
     *
     * @param cls The class marked BeanArea
     *
     * @author devxb
     * @since 0.1
     */
    public AnnotationVaultFactoryBuildInfo(Class<?> cls){
        throwIfClsNotBeansArea(cls);
        VAULT_NAME = getVaultName(cls);
        BEAN_LOAD_ABLES = getBeanLoadables(cls);
        VAULT_ACCESS_PACKAGES = getVaultAccessPackages(cls);
        VAULT_ACCESS_CLASSES = getVaultAccessClasses(cls);
    }

    private void throwIfClsNotBeansArea(Class<?> cls){
        if(cls.getDeclaredAnnotation(BeanArea.class) == null) throw new InvalidAnnotationConfigClassException(cls.getSimpleName());
    }

    private String getVaultName(Class<?> cls){
        String vaultName = convertToBeanName(cls.getSimpleName());
        BeanArea beanArea = cls.getDeclaredAnnotation(BeanArea.class);
        if(beanArea != null && !beanArea.name().equals("")) vaultName = beanArea.name();
        return vaultName;
    }

    private List<BeanLoadable> getBeanLoadables(Class<?> cls){
        List<BeanLoadable> loadables = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields){
            if(field.getDeclaredAnnotation(BeanWire.class) == null) continue;
            Class<?> beanClass = field.getType();
            String beanName = convertToBeanName(beanClass.getSimpleName());
            InternalBean internalBean = beanClass.getDeclaredAnnotation(InternalBean.class);
            throwIfIsNotInternalBean(beanClass, internalBean);
            if(!internalBean.name().equals("")) beanName = internalBean.name();
            String[] packageAccesses = internalBean.accessPackages();
            String[] classAccesses = internalBean.accessClasses();
            Type beanType = internalBean.type();
            loadables.add(
                    Accessors.BeanLoaderAccessor.getAccessor().getBeanLoadable(beanName, beanType, packageAccesses, classAccesses, beanClass)
            );
        }
        return loadables;
    }

    private String convertToBeanName(String name){
        return name.substring(0, 1).toLowerCase() + name.subSequence(1, name.length());
    }

    private void throwIfIsNotInternalBean(Class<?> cls, InternalBean internalBean){
        if(internalBean == null) throw new NoDefinedInternalBeanException(cls.getSimpleName());
    }

    private String[] getVaultAccessPackages(Class<?> cls){
        BeanArea beansArea = cls.getDeclaredAnnotation(BeanArea.class);
        return beansArea.vaultAccessPackages();
    }

    private String[] getVaultAccessClasses(Class<?> cls){
        BeanArea beanArea = cls.getDeclaredAnnotation(BeanArea.class);
        return beanArea.vaultAccessClasses();
    }

    @Override
    public String getVaultName() {
        return VAULT_NAME;
    }

    @Override
    public List<BeanLoadable> getBeanLoadables() {
        return BEAN_LOAD_ABLES;
    }

    @Override
    public String[] getVaultAccessPackages() {
        return VAULT_ACCESS_PACKAGES;
    }

    @Override
    public String[] getVaultAccessClasses(){
        return VAULT_ACCESS_CLASSES;
    }

}