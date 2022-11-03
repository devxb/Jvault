package org.jvault.factory.buildinfo;

import org.jvault.annotation.BeanArea;
import org.jvault.annotation.BeanWire;
import org.jvault.exceptions.InvalidAnnotationConfigClassException;

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
    private final List<Class<?>> CLASSES;
    private final String[] INJECT_ACCESSES;

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
        CLASSES = getClasses(cls);
        INJECT_ACCESSES = getInjectAccesses(cls);
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

    private List<Class<?>> getClasses(Class<?> cls){
        List<Class<?>> classes = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields){
            if(field.getDeclaredAnnotation(BeanWire.class) == null) continue;
            Class<?> beanType = field.getType();
            classes.add(beanType);
        }
        return classes;
    }

    private String[] getInjectAccesses(Class<?> cls){
        BeanArea beansArea = cls.getDeclaredAnnotation(BeanArea.class);
        return beansArea.accesses();
    }

    private String convertToBeanName(String name){
        return name.substring(0, 1).toLowerCase() + name.subSequence(1, name.length());
    }

    @Override
    public String getVaultName() {
        return VAULT_NAME;
    }

    @Override
    public List<Class<?>> getClasses() {
        return CLASSES;
    }

    @Override
    public String[] getInjectAccesses() {
        return INJECT_ACCESSES;
    }

}
