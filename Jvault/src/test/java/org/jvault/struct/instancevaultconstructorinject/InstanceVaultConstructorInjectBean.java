package org.jvault.struct.instancevaultconstructorinject;

import org.jvault.annotation.InternalBean;

@InternalBean(accessClasses = "org.jvault.struct.instancevaultconstructorinject.InstanceVaultConstructorInject")
final class InstanceVaultConstructorInjectBean {

    public String hello(){
        return this.getClass().getSimpleName();
    }

}
