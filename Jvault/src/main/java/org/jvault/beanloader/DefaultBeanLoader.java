package org.jvault.beanloader;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.beans.Bean;
import org.jvault.beans.BeanBuilderFactory;
import org.jvault.exceptions.BeanCycledException;
import org.jvault.exceptions.DisallowedAccessPackageException;
import org.jvault.exceptions.DuplicateBeanNameException;
import org.jvault.exceptions.NoDefinedInternalBeanException;
import org.jvault.util.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

public final class DefaultBeanLoader implements BeanLoader{

    private int order = 1;
    private int enter = 1;
    private final Reflection REFLECTION;
    private final BeanBuilderFactory BEAN_BUILDER_FACTORY;
    private final Map<String, Bean> BEANS;
    private final Map<String, Object> INSTANCES;
    private final Map<String, Class<?>> LAZY_LOAD_BEANS;
    private final Map<String, Integer> SCC;
    private final Map<String, Integer> FUNC_ENTER;
    {
        REFLECTION = Accessors.UtilAccessor.getAccessor().getReflection();
        BEAN_BUILDER_FACTORY = Accessors.BeansAccessor.getAccessor().getBeanBuilderFactory();
        BEANS = new HashMap<>();
        INSTANCES = new HashMap<>();
        LAZY_LOAD_BEANS = new HashMap<>();
        SCC = new HashMap<>();
        FUNC_ENTER = new HashMap<>();
    }

    @Override
    public Map<String, Bean> load(List<Class<?>> classes) {
        throwIfClassesContainedNotBean(classes);
        initLazyLoadBeans(classes);
        if(classes.isEmpty()) return BEANS;
        for(Class<?> cls : classes){
            if(BEANS.containsKey(cls.getDeclaredAnnotation(InternalBean.class).name())) continue;
            loadBean(cls);
            enter++;
        }
        loadBean(classes.get(0));
        return BEANS;
    }

    private void throwIfClassesContainedNotBean(List<Class<?>> classes){
        for(Class<?> cls : classes)
            if(cls.getDeclaredAnnotation(InternalBean.class) == null)
                throw new NoDefinedInternalBeanException(cls.getSimpleName());
    }

    private void initLazyLoadBeans(List<Class<?>> classes) {
        for (Class<?> cls : classes) {
            String beanName = getBeanName(cls);
            if (LAZY_LOAD_BEANS.containsKey(beanName))
                throw new DuplicateBeanNameException(beanName);
            LAZY_LOAD_BEANS.put(beanName, cls);
        }
    }

    private void loadBean(Class<?> cls) {
        String beanName = getBeanName(cls);
        if (SCC.containsKey(beanName)) {
            if (SCC.get(beanName) <= order && Objects.equals(FUNC_ENTER.get(beanName), enter))
                throw new BeanCycledException();
            return;
        }
        SCC.put(beanName, ++order);
        FUNC_ENTER.put(beanName, enter);
        registerBean(beanName, cls);
    }

    private String getBeanName(Class<?> cls){
        String beanName = cls.getSimpleName();
        beanName = beanName.substring(0, 1).toLowerCase() + beanName.subSequence(1, beanName.length());
        InternalBean internalBean = cls.getDeclaredAnnotation(InternalBean.class);
        if(!internalBean.name().equals("")) beanName = internalBean.name();
        return beanName;
    }

    private void registerBean(String beanName, Class<?> cls){
        Constructor<?> constructor = REFLECTION.findConstructor(cls);
        if(!cls.getDeclaredAnnotation(InternalBean.class).name().equals("")) beanName = cls.getDeclaredAnnotation(InternalBean.class).name();
        if(constructor != null) {
            registerBeanFromConstructor(beanName, cls, constructor);
            return;
        }
        List<Field> fields = REFLECTION.findFields(cls);
        registerBeanFromField(beanName, cls, fields);
    }

    private void registerBeanFromConstructor(String beanName, Class<?> cls, Constructor<?> constructor) {
        Parameter[] parameters = constructor.getParameters();
        List<Object> instancedParameters = new ArrayList<>();
        for(Parameter parameter : parameters){
           Inject inject = parameter.getDeclaredAnnotation(Inject.class);
           if(inject == null || inject.value().equals("")) throw new IllegalStateException("Constructor injection must specify \"@Inject(value = \"?\")\"");
           String value = inject.value();
           if(!BEANS.containsKey(value)) {
               if(!LAZY_LOAD_BEANS.containsKey(value)) throw new NoDefinedInternalBeanException(value);
               loadBean(LAZY_LOAD_BEANS.get(value));
           }
           Bean bean = BEANS.get(value);
           if(!bean.isInjectable(cls))
               throw new DisallowedAccessPackageException(value, cls.getPackageName());
           instancedParameters.add(INSTANCES.get(value));
        }
        try {
            constructor.setAccessible(true);
            INSTANCES.put(beanName, constructor.newInstance(instancedParameters.toArray()));
            InternalBean internalBean = cls.getDeclaredAnnotation(InternalBean.class);
            BEANS.put(beanName,
                    BEAN_BUILDER_FACTORY.getBeanBuilder(internalBean.type())
                            .name(beanName)
                            .beans(BEANS)
                            .accesses(internalBean.accesses())
                            .reflection(REFLECTION)
                            .instance(INSTANCES.get(beanName)).build());
        }catch(InvocationTargetException | InstantiationException | IllegalAccessException IE){
            throw new IllegalStateException("Can not call \"newInstance(params)\" of bean \"" + beanName + "\"");
        }
    }

    private void registerBeanFromField(String beanName, Class<?> cls, List<Field> fields){
        Object bean = null;
        try {
            Constructor<?> constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);
            bean = constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Can not find default constructor of \"" + beanName + "\"");
        }
        for(Field field : fields){
            field.setAccessible(true);
            String value = field.getName();
            if(!field.getAnnotation(Inject.class).value().equals("")) value = field.getAnnotation(Inject.class).value();
            if(!BEANS.containsKey(value)){
                if(!LAZY_LOAD_BEANS.containsKey(value)) throw new NoDefinedInternalBeanException(value);
                loadBean(LAZY_LOAD_BEANS.get(value));
            }
            if(!BEANS.get(value).isInjectable(cls))
                throw new DisallowedAccessPackageException(value, cls.getPackageName());
            try {
                field.set(bean, INSTANCES.get(value));
            }catch(IllegalAccessException IAE){
                throw new IllegalStateException("Can not access field value \"" + value + "\"");
            }
        }
        INSTANCES.put(beanName, bean);
        InternalBean internalBean = cls.getDeclaredAnnotation(InternalBean.class);
        BEANS.put(beanName, BEAN_BUILDER_FACTORY.getBeanBuilder(internalBean.type())
                .name(beanName)
                .accesses(internalBean.accesses())
                .instance(bean)
                .beans(BEANS)
                .reflection(REFLECTION)
                .build());
    }

    DefaultBeanLoader() {}

}
