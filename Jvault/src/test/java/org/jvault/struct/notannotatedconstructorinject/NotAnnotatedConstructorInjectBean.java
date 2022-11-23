package org.jvault.struct.notannotatedconstructorinject;

import org.jvault.annotation.InternalBean;

@InternalBean(accessClasses = "org.jvault.struct.notannotatedconstructorinject.NotAnnotatedConstructorInject")
final class NotAnnotatedConstructorInjectBean {

    String hello(){
        return this.getClass().getSimpleName();
    }

}
