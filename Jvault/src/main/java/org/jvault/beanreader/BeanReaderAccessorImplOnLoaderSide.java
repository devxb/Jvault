package org.jvault.beanreader;

final class BeanReaderAccessorImplOnLoaderSide extends org.jvault.beanloader.Accessors.BeanReaderAccessor{
    @Override
    protected BeanReader getBeanReader() {
        return AnnotationBeanReader.getInstance();
    }

    static{
        org.jvault.beanloader.Accessors.BeanReaderAccessor.registerAccessor(new BeanReaderAccessorImplOnLoaderSide());
    }

}