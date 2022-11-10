package org.jvault.beanloader;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.beans.Bean;
import org.jvault.beans.BeanBuilderFactory;
import org.jvault.beans.Type;
import org.jvault.exceptions.BeanCycledException;
import org.jvault.exceptions.DisallowedAccessException;
import org.jvault.exceptions.DuplicateBeanNameException;
import org.jvault.exceptions.NoDefinedInternalBeanException;
import org.jvault.factory.extensible.BeanLoaderExtensiblePoint;
import org.jvault.metadata.InternalAPI;
import org.jvault.util.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

@InternalAPI
public final class DefaultBeanLoader implements BeanLoaderExtensiblePoint {

    private final Reflection REFLECTION;
    private final BeanBuilderFactory BEAN_BUILDER_FACTORY;
    private final Map<String, Bean> BEANS;
    private final Map<String, Object> INSTANCES;
    private final Map<String, Class<?>> SCANNED_BEANS;
    private final Map<String, Integer> SCC;
    private final Map<String, Integer> FUNC_ENTER;
    private int order = 1;
    private int enter = 1;

    {
        REFLECTION = Accessors.UtilAccessor.getAccessor().getReflection();
        BEAN_BUILDER_FACTORY = Accessors.BeansAccessor.getAccessor().getBeanBuilderFactory();
        BEANS = new HashMap<>();
        INSTANCES = new HashMap<>();
        SCANNED_BEANS = new HashMap<>();
        SCC = new HashMap<>();
        FUNC_ENTER = new HashMap<>();
    }

    DefaultBeanLoader() {}

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
            if (SCANNED_BEANS.containsKey(beanName))
                throw new DuplicateBeanNameException(beanName);
            SCANNED_BEANS.put(beanName, beanClass);
        }
    }

    private void loadBean(Class<?> beanClass) {
        String beanName = getBeanName(beanClass);
        if (SCC.containsKey(beanName)) {
            throwIfBeanCycleOccurred(beanName);
            return;
        }
        SCC.put(beanName, ++order);
        FUNC_ENTER.put(beanName, enter);
        registerBean(beanClass);
    }

    private void throwIfBeanCycleOccurred(String beanName) {
        if (SCC.get(beanName) <= order && Objects.equals(FUNC_ENTER.get(beanName), enter))
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
        Parameter[] parameters = constructor.getParameters();
        List<Object> instancedParameters = new ArrayList<>();
        for (Parameter parameter : parameters) {
            Inject inject = parameter.getDeclaredAnnotation(Inject.class);
            throwIfInjectInConstructorHasNotValue(inject);
            String value = inject.value();
            if (!BEANS.containsKey(value)) {
                if (!SCANNED_BEANS.containsKey(value)) throw new NoDefinedInternalBeanException(value);
                loadBean(SCANNED_BEANS.get(value));
            }
            Bean bean = BEANS.get(value);
            if (!bean.isInjectable(beanClass))
                throw new DisallowedAccessException(value, beanClass.getPackage().getName());
            instancedParameters.add(INSTANCES.get(value));
        }
        try {
            constructor.setAccessible(true);
            createBean(beanClass, constructor.newInstance(instancedParameters.toArray()));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException IE) {
            throw new IllegalStateException("Can not call \"newInstance(params)\" of bean \"" + getBeanName(beanClass) + "\"");
        }
    }

    private void throwIfInjectInConstructorHasNotValue(Inject inject) {
        if (inject == null || inject.value().equals(""))
            throw new IllegalStateException("Constructor injection must specify \"@Inject(value = \"?\")\"");
    }

    private void registerBeanFromField(Class<?> beanClass, List<Field> fields) {
        Object bean = getBeanInstance(beanClass);
        for (Field field : fields) {
            field.setAccessible(true);
            String value = field.getName();
            if (!field.getAnnotation(Inject.class).value().equals(""))
                value = field.getAnnotation(Inject.class).value();
            if (!BEANS.containsKey(value)) {
                if (!SCANNED_BEANS.containsKey(value)) throw new NoDefinedInternalBeanException(value);
                loadBean(SCANNED_BEANS.get(value));
            }
            if (!BEANS.get(value).isInjectable(beanClass))
                throw new DisallowedAccessException(value, beanClass.getPackage().getName());
            try {
                field.set(bean, INSTANCES.get(value));
            } catch (IllegalAccessException IAE) {
                throw new IllegalStateException("Can not access field value \"" + value + "\"");
            }
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
            throw new IllegalStateException("Can not find default constructor of \"" + getBeanName(cls) + "\"");
        }
    }

    private void createBean(Class<?> beanClass, Object instance) {
        String beanName = getBeanName(beanClass);
        INSTANCES.put(beanName, instance);
        BEANS.put(beanName, BEAN_BUILDER_FACTORY.getBeanBuilder(getBeanType(beanClass))
                .name(beanName)
                .accessPackages(getBeanInjectAccessPackages(beanClass))
                .accessClasses(getBeanInjectAccessClasses(beanClass))
                .beans(BEANS)
                .instance(INSTANCES.get(beanName))
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
