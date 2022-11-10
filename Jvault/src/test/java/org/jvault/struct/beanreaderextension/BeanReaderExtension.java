package org.jvault.struct.beanreaderextension;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean
public final class BeanReaderExtension {

    private final BeanReaderExtensionBean BEAN_READER_EXTENSION_BEAN;

    public String hello(){
        return this.getClass().getSimpleName() + BEAN_READER_EXTENSION_BEAN.hello();
    }

    @Inject
    private BeanReaderExtension(@Inject("beanReaderExtensionBean") BeanReaderExtensionBean beanReaderExtensionBean){
        BEAN_READER_EXTENSION_BEAN = beanReaderExtensionBean;
    }

}
