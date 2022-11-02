package org.jvault.util;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.exceptions.DuplicateInjectConstructorException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class Reflection {

    private final static Reflection INSTANCE = new Reflection();

    private Reflection(){}

    static Reflection getInstance(){
        return INSTANCE;
    }

    public Constructor<?> findConstructor(Class<?> cls){
        Constructor<?>[] constructors = cls.getDeclaredConstructors();
        Constructor<?> ans = null;
        for(Constructor<?> constructor : constructors){
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

}
