package org.jvault.beans;

import org.jvault.metadata.InternalAPI;

@InternalAPI
public final class BeanBuilderFactory {

    private static final BeanBuilderFactory INSTANCE = new BeanBuilderFactory();

    private BeanBuilderFactory(){}

    static BeanBuilderFactory getInstance(){
        return INSTANCE;
    }

    public Bean.Builder<?> getBeanBuilder(Type beanType){
        if(beanType == Type.NEW) return NewBean.getBuilder();
        return SingletonBean.getBuilder();
    }

}
