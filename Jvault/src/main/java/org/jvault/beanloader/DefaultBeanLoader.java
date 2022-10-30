package org.jvault.beanloader;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.beans.Bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

public final class DefaultBeanLoader implements BeanLoader{

    private int order = 1;
    private int enter = 1;
    private final Map<String, Bean> BEANS;
    private final Map<String, Object> INSTANCES;
    private final Map<String, Class<?>> LAZY_LOAD_BEANS;
    private final Map<String, Integer> SCC;
    private final Map<String, Integer> FUNC_ENTER;
    {
        BEANS = new HashMap<>();
        INSTANCES = new HashMap<>();
        LAZY_LOAD_BEANS = new HashMap<>();
        SCC = new HashMap<>();
        FUNC_ENTER = new HashMap<>();
    }

    @Override
    public Map<String, Bean> load(List<Class<?>> classes) {
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

    private void initLazyLoadBeans(List<Class<?>> classes) {
        for (Class<?> cls : classes) {
            String beanName = getBeanName(cls);
            if (LAZY_LOAD_BEANS.containsKey(beanName))
                throw new IllegalStateException("Duplicate bean name was founded : " + beanName);
            LAZY_LOAD_BEANS.put(beanName, cls);
        }
    }
    private void loadBean(Class<?> cls) {
        String beanName = getBeanName(cls);
        if (SCC.containsKey(beanName)) {
            if (SCC.get(beanName) < order && Objects.equals(FUNC_ENTER.get(beanName), enter))
                throw new IllegalStateException("Bean cycle was founded");
            return;
        }
        SCC.put(beanName, ++order);
        FUNC_ENTER.put(beanName, enter);
        registerBean(beanName, cls);
    }

    private String getBeanName(Class<?> cls){
        String beanName = cls.getSimpleName();
        beanName = beanName.substring(0, 1).toLowerCase() + beanName.subSequence(1, beanName.length());
        if(!cls.getDeclaredAnnotation(InternalBean.class).name().equals("")) beanName = cls.getDeclaredAnnotation(InternalBean.class).name();
        return beanName;
    }

    private void registerBean(String beanName, Class<?> cls){
        Constructor<?> constructor = findConstructor(cls);
        if(!cls.getDeclaredAnnotation(InternalBean.class).name().equals("")) beanName = cls.getDeclaredAnnotation(InternalBean.class).name();
        if(constructor != null) {
            registerBeanFromConstructor(beanName, cls, constructor);
            return;
        }
        List<Field> fields = findFields(cls);
        registerBeanFromField(beanName, cls, fields);
    }

    private Constructor<?> findConstructor(Class<?> cls){
        Constructor<?>[] constructors = cls.getDeclaredConstructors();
        Constructor<?> ans = null;
        for(Constructor<?> constructor : constructors){
            constructor.setAccessible(true);
            if(constructor.getDeclaredAnnotation(Inject.class) == null) continue;
            if(ans != null) throw new IllegalStateException("Duplicate @Inject annotation marked on constructor in " + cls.getName());
            ans = constructor;
        }
        return ans;
    }

    private void registerBeanFromConstructor(String beanName, Class<?> cls, Constructor<?> constructor) {
        Parameter[] parameters = constructor.getParameters();
        List<Object> instancedParameters = new ArrayList<>();
        for(Parameter parameter : parameters){
           Inject inject = parameter.getDeclaredAnnotation(Inject.class);
           if(inject == null || inject.name().equals("")) throw new IllegalStateException("Constructor injection must specify \"@Inject(name = ?)\"");
           String name = inject.name();
           if(!INSTANCES.containsKey(name)) {
               if(!LAZY_LOAD_BEANS.containsKey(name)) throw new IllegalStateException("Can not find InternalBean named : " + name + " int : " + beanName);
               loadBean(LAZY_LOAD_BEANS.get(name));
           }
           instancedParameters.add(INSTANCES.get(name));
        }
        try {
            constructor.setAccessible(true);
            INSTANCES.put(beanName, constructor.newInstance(instancedParameters.toArray()));
            BEANS.put(beanName,
                    cls.getDeclaredAnnotation(InternalBean.class).type().getBuilder()
                            .name(beanName)
                            .beans(BEANS)
                            .instance(INSTANCES.get(beanName)).build());
        }catch(InvocationTargetException | InstantiationException | IllegalAccessException IE){
            throw new IllegalStateException("Can not call \"newInstance()\"");
        }
    }

    private List<Field> findFields(Class<?> cls){
        Field[] fields = cls.getDeclaredFields();
        List<Field> ans = new ArrayList<>();
        for(Field field : fields){
            field.setAccessible(true);
            Inject inject = field.getDeclaredAnnotation(Inject.class);
            if(inject == null) continue;
            ans.add(field);
        }
        return ans;
    }

    private void registerBeanFromField(String beanName, Class<?> cls, List<Field> fields){
        Object bean = null;
        try {
            Constructor<?> constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);
            bean = constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Can not find default constructor of " + beanName);
        }
        for(Field field : fields){
            field.setAccessible(true);
            String name = field.getName();
            if(!field.getAnnotation(Inject.class).name().equals("")) name = field.getAnnotation(Inject.class).name();
            if(!INSTANCES.containsKey(name)){
                if(!LAZY_LOAD_BEANS.containsKey(name)) throw new IllegalStateException("Can not find InternalBean named : " + name + " in : " + beanName);
                loadBean(LAZY_LOAD_BEANS.get(name));
            }
            Object instance = INSTANCES.get(name);
            try {
                field.set(bean, instance);
            }catch(IllegalAccessException IAE){
                throw new IllegalStateException("Can not access field named : " + name);
            }
        }
        INSTANCES.put(beanName, bean);
        BEANS.put(beanName, cls.getDeclaredAnnotation(InternalBean.class).type().getBuilder()
                .name(beanName)
                .instance(bean)
                .beans(BEANS)
                .build());
    }

}
