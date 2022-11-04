package org.jvault.beans;

import org.jvault.annotation.Inject;
import org.jvault.exceptions.NoDefinedInternalBeanException;
import org.jvault.util.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class NewBean implements Bean{

    private final String NAME;
    private final String[] ACCESSES;
    private final Object INSTANCE;
    private final Map<String, Bean> BEANS;
    private final Reflection REFLECTION;

    private NewBean(Bean.Builder<NewBean> builder){
        NAME = builder.name;
        ACCESSES = builder.accesses;
        INSTANCE = builder.instance;
        BEANS = builder.beans;
        REFLECTION = builder.reflection;
    }

    @Override
    public boolean isInjectable(Class<?> cls) {
        if(ACCESSES.length == 0) return true;
        String clsSrc = cls.getPackage().getName();
        for(String access : ACCESSES){
            if(isContainSelectAllRegex(access)) {
                String substring = access.substring(0, access.length()-2);
                if (substring.length() > clsSrc.length()) continue;
                if (clsSrc.contains(substring)) return true;
                continue;
            }
            if(access.equals(clsSrc)) return true;
        }
        return false;
    }

    private boolean isContainSelectAllRegex(String pkg){
        return pkg.startsWith(".*", pkg.length()-2);
    }

    @Override
    public <R> R load() {
        Class<?> cls = INSTANCE.getClass();
        Constructor<?> constructor = REFLECTION.findConstructor(cls);
        if(constructor != null) return (R) loadBeanFromConstructor(constructor);
        List<Field> fields = REFLECTION.findFields(cls);
        return (R) loadBeanFromField(cls, fields);
    }

    private Object loadBeanFromConstructor(Constructor<?> constructor) {
        Parameter[] parameters = constructor.getParameters();
        List<Object> instancedParameters = new ArrayList<>();
        for(Parameter parameter : parameters){
            Inject inject = parameter.getDeclaredAnnotation(Inject.class);
            if(inject == null || inject.value().equals("")) throw new IllegalStateException("Constructor injection must specify \"@Inject(value = \"?\")\"");
            String value = inject.value();
            if(!BEANS.containsKey(value)) throw new NoDefinedInternalBeanException(value);
            instancedParameters.add(BEANS.get(value).load());
        }
        try{
            constructor.setAccessible(true);
            return constructor.newInstance(instancedParameters.toArray());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Can not call \"newInstance(params)\" of bean \"" + NAME + "\"");
        }
    }

    private Object loadBeanFromField(Class<?> cls, List<Field> fields){
        Object bean = null;
        try{
            Constructor<?> constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);
            bean = constructor.newInstance();
        }catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Can not find default constructor in this class \"" + NAME + "\"");
        }
        for(Field field : fields){
            field.setAccessible(true);
            String value = field.getName();
            if(!field.getAnnotation(Inject.class).value().equals("")) value = field.getAnnotation(Inject.class).value();
            if(!BEANS.containsKey(value)) throw new NoDefinedInternalBeanException(value);
            Object instance = BEANS.get(value).load();
            try{
                field.set(bean, instance);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Can not access field value \"" + value + "\"");
            }
        }
        return bean;
    }

    static Builder<NewBean> getBuilder(){
        return new Builder<NewBean>() {
            @Override
            protected NewBean create() {
                return new NewBean(this);
            }
        };
    }

}
