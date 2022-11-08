package org.jvault.struct.annotationconfig;

import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = "org.jvault.struct.annotationconfig")
public final class ACBean2 {

    String hello(){
        return this.getClass().getSimpleName();
    }

    private ACBean2(){}

}
