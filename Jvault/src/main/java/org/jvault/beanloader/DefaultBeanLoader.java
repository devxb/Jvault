package org.jvault.beanloader;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.bean.Bean;
import org.jvault.bean.BeanBuilderFactory;
import org.jvault.bean.Type;
import org.jvault.exceptions.BeanCycledException;
import org.jvault.exceptions.DisallowedAccessException;
import org.jvault.exceptions.DuplicateBeanNameException;
import org.jvault.exceptions.NoDefinedInternalBeanException;
import org.jvault.factory.extensible.BeanLoader;
import org.jvault.metadata.InternalAPI;
import org.jvault.util.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

@InternalAPI
public final class DefaultBeanLoader implements BeanLoader {

    private final Reflection REFLECTION;
    private final BeanBuilderFactory BEAN_BUILDER_FACTORY;
    private final Map<String, Bean> BEANS;
    private final Map<String, Object> BEAN_INSTANCES;
    private final Map<String, Class<?>> SCANNED_BEANS;
    private final Map<String, Integer> SCC;
    private final Map<String, Integer> SCC_ENTER;
    private int order = 1;
    private int enter = 1;

    {
        REFLECTION = Accessors.UtilAccessor.getAccessor().getReflection();
        BEAN_BUILDER_FACTORY = Accessors.BeanAccessor.getAccessor().getBeanBuilderFactory();
        BEANS = new HashMap<>();
        BEAN_INSTANCES = new HashMap<>();
        SCANNED_BEANS = new HashMap<>();
        SCC = new HashMap<>();
        SCC_ENTER = new HashMap<>();
    }

    DefaultBeanLoader() {
    }

    @Override
    public Map<String, Bean> load(List<Class<?>> beanClasses) {
        throwIfListContainNotInternalBean(beanClasses);
        if (beanClasses.isEmpty()) return BEANS;
        scanBeans(beanClasses);
        for (Class<?> beanClass : beanClasses) {
            if (BEANS.containsKey(getBeanName(beanClass))) continue;
            loadBean(beanClass);
            enter++;
        }
        return BEANS;
    }

    private void throwIfListContainNotInternalBean(List<Class<?>> beanClasses) {
        for (Class<?> beanClass : beanClasses) throwIfIsNotInternalBean(beanClass);
    }

    private void throwIfIsNotInternalBean(Class<?> cls) {
        if (cls.getDeclaredAnnotation(InternalBean.class) == null)
            throw new NoDefinedInternalBeanException(cls.getSimpleName());
    }

    private void scanBeans(List<Class<?>> beanClasses) {
        for (Class<?> beanClass : beanClasses) {
            String beanName = getBeanName(beanClass);
            throwIfDuplicateBeanDetected(beanName);
            SCANNED_BEANS.put(beanName, beanClass);
        }
    }

    private void throwIfDuplicateBeanDetected(String beanName) {
        if (SCANNED_BEANS.containsKey(beanName))
            throw new DuplicateBeanNameException(beanName);
    }

    private void loadBean(Class<?> beanClass) {
        String beanName = getBeanName(beanClass);
        if (SCC.containsKey(beanName)) {
            throwIfBeanCycleOccurred(beanName);
            return;
        }
        SCC.put(beanName, ++order);
        SCC_ENTER.put(beanName, enter);
        registerBean(beanClass);
    }

    private void throwIfBeanCycleOccurred(String beanName) {
        if (SCC.get(beanName) <= order && Objects.equals(SCC_ENTER.get(beanName), enter))
            throw new BeanCycledException();
    }

    private void registerBean(Class<?> beanClass) {
        Constructor<?> constructor = REFLECTION.findConstructor(beanClass);
        if (constructor != null) {
            registerBeanFromConstructor(beanClass, constructor);
            return;
        }
        List<Field> fields = REFLECTION.findFields(beanClass);
        registerBeanFromField(beanClass, fields);
    }

    private void registerBeanFromConstructor(Class<?> beanClass, Constructor<?> constructor) {
        List<Parameter> parameters = REFLECTION.getAnnotatedConstructorParameters(constructor);
        List<Object> instancedParameters = new ArrayList<>();
        for (Parameter parameter : parameters) {
            Inject inject = parameter.getDeclaredAnnotation(Inject.class);
            String value = inject.value();
            loadBeanIfNotLoadedBean(value);
            throwIfNotInjectable(value, beanClass);
            instancedParameters.add(BEAN_INSTANCES.get(value));
        }
        try {
            constructor.setAccessible(true);
            createBean(beanClass, constructor.newInstance(instancedParameters.toArray()));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException IE) {
            throw new IllegalStateException("Can not call \"newInstance(params)\" of bean \"" + getBeanName(beanClass) + "\"");
        }
    }

    private void registerBeanFromField(Class<?> beanClass, List<Field> fields) {
        Object bean = getBeanInstance(beanClass);
        for (Field field : fields) {
            field.setAccessible(true);
            String value = getBeanNameFromField(field);
            loadBeanIfNotLoadedBean(value);
            throwIfNotInjectable(value, beanClass);
            injectBeanToField(bean, field, value);
        }
        createBean(beanClass, bean);
    }

    private Object getBeanInstance(Class<?> cls) {
        try {
            Constructor<?> constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new IllegalStateException("Can not invoke constructor of \"" + getBeanName(cls) + "\"");
        }
    }

    private String getBeanNameFromField(Field field) {
        String name = field.getName();
        if (!field.getAnnotation(Inject.class).value().equals(""))
            name = field.getAnnotation(Inject.class).value();
        return name;
    }

    private void loadBeanIfNotLoadedBean(String beanName) {
        if (!BEANS.containsKey(beanName)) {
            throwIfNotScannedBean(beanName);
            loadBean(SCANNED_BEANS.get(beanName));
        }
    }

    private void throwIfNotScannedBean(String beanName) {
        if (!SCANNED_BEANS.containsKey(beanName)) throw new NoDefinedInternalBeanException(beanName);
    }

    private void throwIfNotInjectable(String beanName, Class<?> injectClass) {
        Bean bean = BEANS.get(beanName);
        if (!bean.isInjectable(injectClass))
            throw new DisallowedAccessException(beanName, injectClass.getPackage().getName());
    }

    private void injectBeanToField(Object bean, Field field, String beanName) {
        try {
            field.set(bean, BEAN_INSTANCES.get(beanName));
        } catch (IllegalAccessException IAE) {
            throw new IllegalStateException("Can not access field value \"" + beanName + "\"");
        }
    }

    private void createBean(Class<?> beanClass, Object instance) {
        String beanName = getBeanName(beanClass);
        BEAN_INSTANCES.put(beanName, instance);
        BEANS.put(beanName, BEAN_BUILDER_FACTORY.getBeanBuilder(getBeanType(beanClass))
                .name(beanName)
                .accessPackages(getBeanInjectAccessPackages(beanClass))
                .accessClasses(getBeanInjectAccessClasses(beanClass))
                .beans(BEANS)
                .instance(BEAN_INSTANCES.get(beanName))
                .build());
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

    private Type getBeanType(Class<?> cls) {
        return cls.getDeclaredAnnotation(InternalBean.class).type();
    }

    private String[] getBeanInjectAccessPackages(Class<?> cls) {
        return cls.getDeclaredAnnotation(InternalBean.class).accessPackages();
    }

    private String[] getBeanInjectAccessClasses(Class<?> cls) {
        return cls.getDeclaredAnnotation(InternalBean.class).accessClasses();
    }

}
