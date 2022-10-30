package org.jvault.beans;


import org.jvault.annotation.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class NewBean implements Bean{

    private final String NAME;
    private final Set<String> ACCESS_VAULTS;
    private final Object INSTANCE;
    private final Map<String, Bean> BEANS;

    private NewBean(Bean.Builder<NewBean> builder){
        NAME = builder.name;
        ACCESS_VAULTS = builder.ACCESS_VAULTS;
        INSTANCE = builder.instance;
        BEANS = builder.beans;
    }

    @Override
    public <R> R load() {
        Class<?> cls = INSTANCE.getClass();
        Constructor<?> constructor = findConstructor(cls);
        if(constructor != null) return (R) loadBeanFromConstructor(constructor);
        List<Field> fields = findFields(cls);
        return (R) loadBeanFromField(cls, fields);
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

    private Object loadBeanFromConstructor(Constructor<?> constructor) {
        Parameter[] parameters = constructor.getParameters();
        List<Object> instancedParameters = new ArrayList<>();
        for(Parameter parameter : parameters){
            Inject inject = parameter.getDeclaredAnnotation(Inject.class);
            if(inject == null || inject.name().equals("")) throw new IllegalStateException("Constructor injection must specify \"@Inject(name = ?)\"");
            String name = inject.name();
            if(!BEANS.containsKey(name)) throw new IllegalStateException("Can not find bean named : " + name);
            instancedParameters.add(BEANS.get(name).load());
        }
        try{
            constructor.setAccessible(true);
            return constructor.newInstance(instancedParameters.toArray());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
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

    private Object loadBeanFromField(Class<?> cls, List<Field> fields){
        Object bean = null;
        try{
            Constructor<?> constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);
            bean = constructor.newInstance();
        }catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Can not find default constructor of " + NAME);
        }
        for(Field field : fields){
            field.setAccessible(true);
            String name = field.getName();
            if(!field.getAnnotation(Inject.class).name().equals("")) name = field.getAnnotation(Inject.class).name();
            if(!BEANS.containsKey(name)) throw new IllegalStateException("Can not find bean named : " + name);
            Object instance = BEANS.get(name).load();
            try{
                field.set(bean, instance);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Can not access field named : " + name);
            }
        }
        return bean;
    }

    static Builder<NewBean> getBuilder(){
        return new Builder<>() {
            @Override
            protected NewBean create() {
                return new NewBean(this);
            }
        };
    }

}
