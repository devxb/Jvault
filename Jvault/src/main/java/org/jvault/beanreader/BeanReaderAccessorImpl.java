package org.jvault.beanreader;

final class BeanReaderAccessorImpl extends org.jvault.factory.Accessors.BeanReaderAccessor {
    @Override
    protected BeanReader getBeanReader() {
        return AnnotationBeanReader.getInstance();
    }

    static{
        org.jvault.factory.Accessors.BeanReaderAccessor.registerAccessor(new BeanReaderAccessorImpl());
    }

}