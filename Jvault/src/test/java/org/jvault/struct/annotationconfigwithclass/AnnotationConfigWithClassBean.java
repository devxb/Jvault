package org.jvault.struct.annotationconfigwithclass;

import org.jvault.annotation.InternalBean;

@InternalBean(accessClasses = "org.jvault.struct.annotationconfigwithclass.AnnotationConfigWithClass")
final class AnnotationConfigWithClassBean {

    String hello(){
        return this.getClass().getSimpleName();
    }

}
