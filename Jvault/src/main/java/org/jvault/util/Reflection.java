package org.jvault.util;

import org.jvault.annotation.Inject;
import org.jvault.exceptions.DuplicateInjectConstructorException;
import org.jvault.metadata.InternalAPI;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.*;

@InternalAPI
public final class Reflection {

    private final static Reflection INSTANCE = new Reflection();

    private Reflection(){}

    static Reflection getInstance(){
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <R> Constructor<R> findConstructor(Class<R> cls){
        Constructor<R>[] constructors = (Constructor<R>[]) cls.getDeclaredConstructors();
        Constructor<R> ans = null;
        for(Constructor<R> constructor : constructors){
            constructor.setAccessible(true);
            if(constructor.getDeclaredAnnotation(Inject.class) == null) continue;
            if(ans != null) throw new DuplicateInjectConstructorException(cls.getName());
            ans = constructor;
        }
        return ans;
    }

    public List<Field> findFields(Class<?> cls){
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

    public List<String> getAnnotatedConstructorParameters(Constructor<?> constructor){
        throwIfConstructorDoesNotAnnotatedInject(constructor);
        List<String> parametersBeanName = new ArrayList<>();
        Parameter[] parameters = constructor.getParameters();
        for(Parameter parameter : parameters) {
            String beanName = getBeanNameByParameter(parameter);
            parametersBeanName.add(beanName);
        }
        return parametersBeanName;
    }

    private void throwIfConstructorDoesNotAnnotatedInject(Constructor<?> constructor){
        if(constructor.getDeclaredAnnotation(Inject.class) == null)
            throw new IllegalArgumentException("\"Method findConstructorParameter(Constructor<?> constructor) " +
                    "only accept constructors marked with \"@Inject\" as parameters.\"");
    }

    private String getBeanNameByParameter(Parameter parameter){
        Inject inject = parameter.getDeclaredAnnotation(Inject.class);
        if(inject == null) return convertToBeanName(parameter.getType().getSimpleName());
        return inject.value();
    }

    private String convertToBeanName(String name) {
        return name.substring(0, 1).toLowerCase() + name.subSequence(1, name.length());
    }

}
