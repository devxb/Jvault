package org.jvault.beanreader;

import org.jvault.factory.buildinfo.Accessors;
import org.jvault.factory.buildinfo.extensible.BeanReader;
import org.jvault.metadata.InternalAPI;

@InternalAPI
@SuppressWarnings("unused")
final class BeanReaderAccessorImplOnBuildInfoSide extends Accessors.BeanReaderAccessor {

    @Override
    protected BeanReader getBeanReader() {
        return AnnotatedBeanReader.getInstance();
    }

    static{
        Accessors.BeanReaderAccessor.registerAccessor(new BeanReaderAccessorImplOnBuildInfoSide());
    }

}
