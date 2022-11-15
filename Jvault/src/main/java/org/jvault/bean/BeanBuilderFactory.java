package org.jvault.bean;

import org.jvault.metadata.InternalAPI;

@InternalAPI
public final class BeanBuilderFactory {

    private static final BeanBuilderFactory INSTANCE = new BeanBuilderFactory();

    private BeanBuilderFactory() {}

    static BeanBuilderFactory getInstance() {
        return INSTANCE;
    }

    public Bean.Builder<?> getBeanBuilder(Type beanType) {
        return beanType.getBeanBuilder();
    }

}
