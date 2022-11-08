package org.jvault.beanloader;

import org.jvault.beans.Type;

public final class BeanLoadable {

    final String BEAN_NAME;
    final Type BEAN_TYPE;
    final String[] PACKAGE_ACCESSES;
    final String[] CLASS_ACCSSES;
    final Class<?> BEAN_CLASS;

    private BeanLoadable(){
        throw new UnsupportedOperationException("Can not invoke constructor \"BeanLoadable\"");
    }

    BeanLoadable(String beanName, Type beanType, String[] packageAccesses, String[] classAccesses, Class<?> beanClass){
        BEAN_NAME = beanName;
        BEAN_TYPE = beanType;
        PACKAGE_ACCESSES = packageAccesses;
        CLASS_ACCSSES = classAccesses;
        BEAN_CLASS = beanClass;
    }

}
