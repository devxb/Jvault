package org.jvault.beanloader;

import org.jvault.beans.Type;
import org.jvault.factory.buildinfo.Accessors;

final class BeanLoaderAccessorImplOnBuildInfoSide extends Accessors.BeanLoaderAccessor {

    @Override
    protected BeanLoadable getBeanLoadable(String beanName, Type beanType, String[] packageAccesses, String[] classAccesses, Class<?> beanClass) {
        return new BeanLoadable(beanName, beanType, packageAccesses,classAccesses, beanClass);
    }

    static{
        Accessors.BeanLoaderAccessor.registerAccessor(new BeanLoaderAccessorImplOnBuildInfoSide());
    }

}
