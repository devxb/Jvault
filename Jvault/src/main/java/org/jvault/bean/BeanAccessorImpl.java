package org.jvault.bean;

import org.jvault.metadata.InternalAPI;

@InternalAPI
@SuppressWarnings("unused")
final class BeanAccessorImpl extends org.jvault.beanloader.Accessors.BeanAccessor {

    @Override
    protected BeanBuilderFactory getBeanBuilderFactory() {
        return BeanBuilderFactory.getInstance();
    }

    static{
        org.jvault.beanloader.Accessors.BeanAccessor.registerAccessor(new BeanAccessorImpl());
    }

}
