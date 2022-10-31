package org.jvault.beans;

final class BeansAccessorImpl extends org.jvault.beanloader.Accessors.BeansAccessor {

    @Override
    protected BeanBuilderFactory getBeanBuilderFactory() {
        return BeanBuilderFactory.getInstance();
    }

    static{
        org.jvault.beanloader.Accessors.BeansAccessor.registerAccessor(new BeansAccessorImpl());
    }

}
