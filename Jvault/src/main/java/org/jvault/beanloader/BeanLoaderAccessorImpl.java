package org.jvault.beanloader;

import org.jvault.metadata.InternalAPI;

@InternalAPI
@SuppressWarnings("unused")
final class BeanLoaderAccessorImpl extends org.jvault.factory.Accessors.BeanLoaderAccessor {

    @Override
    protected DefaultBeanLoader getBeanLoader() {
        return new DefaultBeanLoader();
    }

    static{
        org.jvault.factory.Accessors.BeanLoaderAccessor.registerAccessor(new BeanLoaderAccessorImpl());
    }

}
