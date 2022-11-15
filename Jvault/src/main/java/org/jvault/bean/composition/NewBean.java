package org.jvault.bean.composition;

import org.jvault.annotation.Inject;
import org.jvault.bean.Bean;
import org.jvault.exceptions.NoDefinedInternalBeanException;
import org.jvault.metadata.InternalAPI;
import org.jvault.util.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@InternalAPI
public final class NewBean implements Bean {

    private static final Reflection REFLECTION;

    static {
        REFLECTION = Accessors.UtilAccessor.getAccessor().getReflection();
    }

    private final String NAME;
    private final String[] ACCESS_PACKAGES;
    private final String[] ACCESS_CLASSES;
    private final Object INSTANCE;
    private final Map<String, Bean> BEANS;

    private NewBean(Bean.Builder<NewBean> builder) {
        NAME = builder.getName();
        ACCESS_PACKAGES = builder.getAccessPackages();
        ACCESS_CLASSES = builder.getAccessClasses();
        INSTANCE = builder.getInstance();
        BEANS = builder.getBeans();
    }

    static Builder<NewBean> getBuilder() {
        return new Builder<NewBean>() {
            @Override
            protected NewBean create() {
                return new NewBean(this);
            }
        };
    }

    @Override
    public boolean isInjectable(Class<?> cls) {
        if (ACCESS_PACKAGES.length == 0 && ACCESS_CLASSES.length == 0) return true;
        return isClassInjectable(cls) || isPackageInjectable(cls);
    }

    private boolean isClassInjectable(Class<?> cls) {
        String name = cls.getName().replace("$", ".");
        for (String access : ACCESS_CLASSES)
            if (access.equals(name)) return true;
        return false;
    }

    private boolean isPackageInjectable(Class<?> cls) {
        String clsSrc = cls.getPackage().getName();
        for (String access : ACCESS_PACKAGES) {
            if (isContainSelectAllRegex(access)) {
                String substring = access.substring(0, access.length() - 2);
                if (substring.length() > clsSrc.length()) continue;
                if (clsSrc.contains(substring)) return true;
                continue;
            }
            if (access.equals(clsSrc)) return true;
        }
        return false;
    }

    private boolean isContainSelectAllRegex(String pkg) {
        return pkg.startsWith(".*", pkg.length() - 2);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R load() {
        Class<?> cls = INSTANCE.getClass();
        Constructor<?> constructor = REFLECTION.findConstructor(cls);
        if (constructor != null) return (R) loadBeanFromConstructor(constructor);
        List<Field> fields = REFLECTION.findFields(cls);
        return (R) loadBeanFromField(cls, fields);
    }

    private Object loadBeanFromConstructor(Constructor<?> constructor) {
        Parameter[] parameters = constructor.getParameters();
        List<Object> instancedParameters = new ArrayList<>();
        fillParameters(instancedParameters, parameters);
        try {
            constructor.setAccessible(true);
            return constructor.newInstance(instancedParameters.toArray());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Can not call \"newInstance(params)\" of bean \"" + NAME + "\"");
        }
    }

    private void fillParameters(List<Object> instancedParameters, Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            Inject inject = parameter.getDeclaredAnnotation(Inject.class);
            throwIfParameterDoesNotAnnotatedInject(inject);
            String value = inject.value();
            throwIfCanNotFindDefinedBean(value);
            instancedParameters.add(BEANS.get(value).load());
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
            Object instance = BEANS.get(value).load();
            injectBeanToField(field, bean, instance);
        }
        return bean;
    }

    private void throwIfCanNotFindDefinedBean(String beanName) {
        if (!BEANS.containsKey(beanName)) throw new NoDefinedInternalBeanException(beanName);
    }

    private Object loadBeanFromDefaultConstructor(Class<?> cls) {
        try {
            Constructor<?> constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new IllegalStateException("Can not find default constructor in this class \"" + NAME + "\"");
        }
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
