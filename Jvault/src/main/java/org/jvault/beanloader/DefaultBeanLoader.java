package org.jvault.beanloader;

import org.jvault.annotation.Inject;
import org.jvault.beans.Bean;
import org.jvault.beans.BeanBuilderFactory;
import org.jvault.exceptions.BeanCycledException;
import org.jvault.exceptions.DisallowedAccessException;
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
    private final Map<String, BeanLoadable> LAZY_LOAD_BEANS;
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
    public Map<String, Bean> load(List<BeanLoadable> beanLoadables) {
        if(beanLoadables.isEmpty()) return BEANS;
        initLazyLoadBeans(beanLoadables);
        for(BeanLoadable beanLoadable : beanLoadables){
            if(BEANS.containsKey(beanLoadable.BEAN_NAME)) continue;
            loadBean(beanLoadable);
            enter++;
        }
        return BEANS;
    }

    private void initLazyLoadBeans(List<BeanLoadable> beanLoadables) {
        for (BeanLoadable beanLoadable : beanLoadables) {
            String beanName = beanLoadable.BEAN_NAME;
            if (LAZY_LOAD_BEANS.containsKey(beanName))
                throw new DuplicateBeanNameException(beanName);
            LAZY_LOAD_BEANS.put(beanName, beanLoadable);
        }
    }

    private void loadBean(BeanLoadable beanLoadable) {
        String beanName = beanLoadable.BEAN_NAME;
        if (SCC.containsKey(beanName)) {
            if (SCC.get(beanName) <= order && Objects.equals(FUNC_ENTER.get(beanName), enter))
                throw new BeanCycledException();
            return;
        }
        SCC.put(beanName, ++order);
        FUNC_ENTER.put(beanName, enter);
        registerBean(beanLoadable);
    }

    private void registerBean(BeanLoadable beanLoadable){
        Constructor<?> constructor = REFLECTION.findConstructor(beanLoadable.BEAN_CLASS);
        if(constructor != null) {
            registerBeanFromConstructor(beanLoadable, constructor);
            return;
        }
        List<Field> fields = REFLECTION.findFields(beanLoadable.BEAN_CLASS);
        registerBeanFromField(beanLoadable, fields);
    }

    private void registerBeanFromConstructor(BeanLoadable beanLoadable, Constructor<?> constructor) {
        Parameter[] parameters = constructor.getParameters();
        List<Object> instancedParameters = new ArrayList<>();
        for(Parameter parameter : parameters){
           Inject inject = parameter.getDeclaredAnnotation(Inject.class);
           throwIfInjectNotHasValue(inject);
           String value = inject.value();
           if(!BEANS.containsKey(value)) {
               if(!LAZY_LOAD_BEANS.containsKey(value)) throw new NoDefinedInternalBeanException(value);
               loadBean(LAZY_LOAD_BEANS.get(value));
           }
           Bean bean = BEANS.get(value);
           if(!bean.isInjectable(beanLoadable.BEAN_CLASS))
               throw new DisallowedAccessException(value, beanLoadable.BEAN_CLASS.getPackage().getName());
           instancedParameters.add(INSTANCES.get(value));
        }
        try {
            constructor.setAccessible(true);
            INSTANCES.put(beanLoadable.BEAN_NAME, constructor.newInstance(instancedParameters.toArray()));
            BEANS.put(beanLoadable.BEAN_NAME,
                    BEAN_BUILDER_FACTORY.getBeanBuilder(beanLoadable.BEAN_TYPE)
                            .name(beanLoadable.BEAN_NAME)
                            .beans(BEANS)
                            .accessPackages(beanLoadable.PACKAGE_ACCESSES)
                            .accessClasses(beanLoadable.CLASS_ACCSSES)
                            .reflection(REFLECTION)
                            .instance(INSTANCES.get(beanLoadable.BEAN_NAME)).build());
        }catch(InvocationTargetException | InstantiationException | IllegalAccessException IE){
            throw new IllegalStateException("Can not call \"newInstance(params)\" of bean \"" + beanLoadable.BEAN_NAME + "\"");
        }
    }

    private void throwIfInjectNotHasValue(Inject inject){
        if(inject == null || inject.value().equals("")) throw new IllegalStateException("Constructor injection must specify \"@Inject(value = \"?\")\"");
    }

    private void registerBeanFromField(BeanLoadable beanLoadable, List<Field> fields){
        Object bean = null;
        try {
            Constructor<?> constructor = beanLoadable.BEAN_CLASS.getDeclaredConstructor();
            constructor.setAccessible(true);
            bean = constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Can not find default constructor of \"" + beanLoadable.BEAN_NAME + "\"");
        }
        for(Field field : fields){
            field.setAccessible(true);
            String value = field.getName();
            if(!field.getAnnotation(Inject.class).value().equals("")) value = field.getAnnotation(Inject.class).value();
            if(!BEANS.containsKey(value)){
                if(!LAZY_LOAD_BEANS.containsKey(value)) throw new NoDefinedInternalBeanException(value);
                loadBean(LAZY_LOAD_BEANS.get(value));
            }
            if(!BEANS.get(value).isInjectable(beanLoadable.BEAN_CLASS))
                throw new DisallowedAccessException(value, beanLoadable.BEAN_CLASS.getPackage().getName());
            try {
                field.set(bean, INSTANCES.get(value));
            }catch(IllegalAccessException IAE){
                throw new IllegalStateException("Can not access field value \"" + value + "\"");
            }
        }
        INSTANCES.put(beanLoadable.BEAN_NAME, bean);
        BEANS.put(beanLoadable.BEAN_NAME, BEAN_BUILDER_FACTORY.getBeanBuilder(beanLoadable.BEAN_TYPE)
                .name(beanLoadable.BEAN_NAME)
                .accessPackages(beanLoadable.PACKAGE_ACCESSES)
                .accessClasses(beanLoadable.CLASS_ACCSSES)
                .instance(bean)
                .beans(BEANS)
                .reflection(REFLECTION)
                .build());
    }

    DefaultBeanLoader() {}

}
