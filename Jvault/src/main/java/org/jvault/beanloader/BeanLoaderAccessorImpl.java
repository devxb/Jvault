package org.jvault.beanloader;

final class BeanLoaderAccessorImpl extends org.jvault.factory.Accessors.BeanLoaderAccessor {

    @Override
    protected BeanLoader getBeanLoader() {
        return new DefaultBeanLoader();
    }

    static{
        org.jvault.factory.Accessors.BeanLoaderAccessor.registerAccessor(new BeanLoaderAccessorImpl());
    }

}
