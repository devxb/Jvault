package org.jvault.beans;

import org.jvault.metadata.InternalAPI;

@InternalAPI
final class BeansAccessorImpl extends org.jvault.beanloader.Accessors.BeansAccessor {

    @Override
    protected BeanBuilderFactory getBeanBuilderFactory() {
        return BeanBuilderFactory.getInstance();
    }

    static{
        org.jvault.beanloader.Accessors.BeansAccessor.registerAccessor(new BeansAccessorImpl());
    }

}
