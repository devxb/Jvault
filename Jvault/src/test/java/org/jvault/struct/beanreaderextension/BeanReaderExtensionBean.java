package org.jvault.struct.beanreaderextension;

import org.jvault.annotation.InternalBean;

@InternalBean(accessClasses = "org.jvault.struct.beanreaderextension.BeanReaderExtension")
public final class BeanReaderExtensionBean {

    String hello(){
        return this.getClass().getSimpleName();
    }

}
