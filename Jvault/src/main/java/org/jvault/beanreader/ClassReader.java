package org.jvault.beanreader;

import org.jvault.annotation.InternalBean;
import org.jvault.exceptions.NoDefinedInternalBeanException;
import org.jvault.metadata.InternalAPI;

import java.lang.reflect.Modifier;

@InternalAPI
final class ClassReader {

    private static ClassReader INSTANCE = new ClassReader();

    private ClassReader(){}

    static ClassReader getInstance(){
        return ClassReader.INSTANCE;
    }

    Class<?> readClass(String classSrc){
        Class<?> cls = getClassForSrc(classSrc);
        throwIfModifierInterface(cls);
        throwIfIsNotAnnotatedInternalBean(cls);
        return cls;
    }

    private Class<?> getClassForSrc(String src){
        try{
            return Class.forName(src);
        } catch (ClassNotFoundException CNFE){
            throw new IllegalStateException("Can not find Class \"" + src + "\"");
        }
    }

    private void throwIfModifierInterface(Class<?> cls){
        if(Modifier.isAbstract(cls.getModifiers()) || Modifier.isInterface(cls.getModifiers()))
            throw new IllegalStateException("\"@InternalBean\" annotation Could not marked \"jnterface\" or \"abstract\"");
    }

    private void throwIfIsNotAnnotatedInternalBean(Class<?> cls) {
        if (cls.getDeclaredAnnotation(InternalBean.class) == null)
            throw new NoDefinedInternalBeanException(cls.getSimpleName());
    }

}
