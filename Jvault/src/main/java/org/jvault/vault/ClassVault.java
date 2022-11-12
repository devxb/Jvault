package org.jvault.vault;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.beans.Bean;
import org.jvault.beans.Type;
import org.jvault.exceptions.DisallowedAccessException;
import org.jvault.exceptions.NoDefinedInternalBeanException;
import org.jvault.metadata.API;
import org.jvault.util.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Receive the Class type as a parameter and returns the corresponding instance.
 * <br>
 * <br>
 * ClassVault can only be instantiated in the org.jvault.* package,<br>
 * and actually you can't force instantiation of Vault without using Reflection.<br>
 * This means that you should not instantiate ClassVault using Reflection.<br>
 * To obtain ClassVault, see the {@link org.jvault.factory.TypeVaultFactory} class.
 *
 * @author devxb
 * @see org.jvault.factory.TypeVaultFactory
 * @see org.jvault.vault.Vault
 * @see org.jvault.annotation.Inject
 * @see org.jvault.annotation.InternalBean
 * @since 0.1
 */
@API
public final class ClassVault implements Vault<Class<?>> {

    private final String NAME;
    private final String[] ACCESS_PACKAGES;
    private final String[] ACCESS_CLASSES;
    private final Map<String, Bean> BEANS;
    private final ConcurrentMap<Class<?>, Bean> CACHED_BEANS;
    private final Reflection REFLECTION;

    ClassVault(Vault.Builder<ClassVault> builder) {
        NAME = builder.name;
        ACCESS_PACKAGES = builder.accessPackages;
        ACCESS_CLASSES = builder.accessClasses;
        BEANS = builder.BEANS;
        CACHED_BEANS = new ConcurrentHashMap<>();
        REFLECTION = Accessors.UtilAccessor.getAccessor().getReflection();
    }

    /**
     * @param <R>   the type of return instance
     * @param param The type of target to be injected beans, Vault will inject beans into the param.
     * @return Returns an instance of the type received param.
     * @throws DisallowedAccessException Occurs when the package in param is a package that does not have access to Vault,
     *                                   * or the Beans to be injected into param cannot be injected into the package in Param.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <R> R inject(Class<?> param) {
        throwIfParamDoesNotAccessibleVault(param);

        if(CACHED_BEANS.containsKey(param)) return CACHED_BEANS.get(param).load();
        cacheBean(param);
        if(CACHED_BEANS.containsKey(param)) return CACHED_BEANS.get(param).load();

        Constructor<?> constructor = REFLECTION.findConstructor(param);
        if (constructor != null) return (R) loadBeanFromConstructor(param, constructor);
        List<Field> fields = REFLECTION.findFields(param);
        return (R) loadBeanFromField(param, fields);
    }

    private void throwIfParamDoesNotAccessibleVault(Class<?> param){
        if (!isVaultAccessible(param)) throw new DisallowedAccessException(NAME, param.getPackage().getName());
    }
    private boolean isVaultAccessible(Class<?> cls) {
        if (ACCESS_CLASSES.length == 0 && ACCESS_PACKAGES.length == 0) return true;
        if (isVaultAccessibleClass(cls)) return true;
        return isVaultAccessiblePackage(cls);
    }
    private boolean isVaultAccessibleClass(Class<?> cls) {
        String name = cls.getName().replace("$", ".");
        for (String access : ACCESS_CLASSES)
            if (access.equals(name)) return true;
        return false;
    }

    private boolean isVaultAccessiblePackage(Class<?> cls) {
        String src = cls.getPackage().getName();
        for (String vaultAccess : ACCESS_PACKAGES) {
            if (isContainSelectAllRegex(vaultAccess)) {
                String substring = vaultAccess.substring(0, vaultAccess.length() - 2);
                if (substring.length() > src.length()) continue;
                if (src.contains(substring)) return true;
            }
            if (vaultAccess.equals(src)) return true;
        }
        return false;
    }

    private boolean isContainSelectAllRegex(String pkg) {
        return pkg.startsWith(".*", pkg.length() - 2);
    }

    private void cacheBean(Class<?> cls){
        if(isDoseNotNeedCache(cls)) return;
        String cachedBeanName = getBeanName(cls);
        if(BEANS.containsKey(cachedBeanName)) CACHED_BEANS.put(cls, BEANS.get(cachedBeanName));
    }

    private boolean isDoseNotNeedCache(Class<?> cls){
        InternalBean internalBean = cls.getDeclaredAnnotation(InternalBean.class);
        return internalBean == null || internalBean.type() != Type.SINGLETON;
    }

    private String getBeanName(Class<?> cls) {
        String name = convertToBeanName(cls.getSimpleName());
        InternalBean internalBean = cls.getDeclaredAnnotation(InternalBean.class);
        if (internalBean.name().equals("")) return name;
        return internalBean.name();
    }

    private String convertToBeanName(String name) {
        return name.substring(0, 1).toLowerCase() + name.subSequence(1, name.length());
    }

    private Object loadBeanFromConstructor(Class<?> cls, Constructor<?> constructor) {
        Parameter[] parameters = constructor.getParameters();
        List<Object> instancedParameters = new ArrayList<>();
        for (Parameter parameter : parameters) {
            Inject inject = parameter.getDeclaredAnnotation(Inject.class);

            throwIfParameterDoesNotAnnotatedInject(inject);

            String value = inject.value();

            throwIfCanNotFindDefinedBean(value);
            throwIfBeanDoesNotAccessInject(value, cls);

            instancedParameters.add(BEANS.get(value).load());
        }
        try {
            constructor.setAccessible(true);
            return constructor.newInstance(instancedParameters.toArray());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void throwIfParameterDoesNotAnnotatedInject(Inject inject) {
        if (inject == null || inject.value().equals(""))
            throw new IllegalStateException("Constructor injection must specify \"@Inject(value = \"?\")\"");
    }

    private Object loadBeanFromField(Class<?> cls, List<Field> fields) {
        Object bean = loadBeanFromDefaultConstructor(cls);
        for (Field field : fields) {
            field.setAccessible(true);
            String value = getBeanNameByField(field);

            throwIfCanNotFindDefinedBean(value);
            throwIfBeanDoesNotAccessInject(value, cls);

            Object instance = BEANS.get(value).load();
            injectBeanToField(field, bean, instance);
        }
        return bean;
    }

    private Object loadBeanFromDefaultConstructor(Class<?> cls) {
        try {
            Constructor<?> constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new IllegalStateException("Can not find default constructor of \"" + cls.getSimpleName() + "\"");
        }
    }

    private void throwIfCanNotFindDefinedBean(String beanName) {
        if (!BEANS.containsKey(beanName)) throw new NoDefinedInternalBeanException(beanName);
    }

    private void throwIfBeanDoesNotAccessInject(String beanName, Class<?> injectReceiver){
        if (!BEANS.get(beanName).isInjectable(injectReceiver))
            throw new DisallowedAccessException(beanName, injectReceiver.getPackage().getName());
    }

    private void injectBeanToField(Field field, Object bean, Object instance) {
        try {
            field.set(bean, instance);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Can not access field value \"" + getBeanNameByField(field) + "\"");
        }
    }

    private String getBeanNameByField(Field field) {
        String beanName = field.getName();
        if (!field.getAnnotation(Inject.class).value().equals(""))
            beanName = field.getAnnotation(Inject.class).value();
        return beanName;
    }

}
