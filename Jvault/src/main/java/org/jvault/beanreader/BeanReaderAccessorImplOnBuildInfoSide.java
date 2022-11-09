package org.jvault.beanreader;

import org.jvault.factory.buildinfo.Accessors;
import org.jvault.factory.buildinfo.extensible.BeanReaderExtensiblePoint;
import org.jvault.metadata.InternalAPI;

@InternalAPI
final class BeanReaderAccessorImplOnBuildInfoSide extends Accessors.BeanReaderAccessor {

    @Override
    protected BeanReaderExtensiblePoint getBeanReader() {
        return AnnotatedBeanReader.getInstance();
    }

    static{
        Accessors.BeanReaderAccessor.registerAccessor(new BeanReaderAccessorImplOnBuildInfoSide());
    }

}
