package org.jvault.beanreader;

import org.jvault.factory.buildinfo.Accessors;

final class BeanReaderAccessorImplOnBuildInfoSide extends Accessors.BeanReaderAccessor {

    @Override
    protected BeanReader getBeanReader() {
        return AnnotatedBeanReader.getInstance();
    }

    static{
        Accessors.BeanReaderAccessor.registerAccessor(new BeanReaderAccessorImplOnBuildInfoSide());
    }

}
