package org.jvault.beanloader;

import org.jvault.beans.Bean;
import org.jvault.beans.Type;

public final class BeanLoadable {

    final String BEAN_NAME;
    final Type BEAN_TYPE;
    final String[] ACCESSES;
    final Class<?> BEAN_CLASS;

    private BeanLoadable(){
        throw new UnsupportedOperationException("Can not invoke constructor \"BeanLoadable\"");
    }

    BeanLoadable(String beanName, Type beanType, String[] accesses, Class<?> beanClass){
        BEAN_NAME = beanName;
        BEAN_TYPE = beanType;
        ACCESSES = accesses;
        BEAN_CLASS = beanClass;
    }

}