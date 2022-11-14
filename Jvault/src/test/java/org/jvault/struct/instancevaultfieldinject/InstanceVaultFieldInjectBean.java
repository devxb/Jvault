package org.jvault.struct.instancevaultfieldinject;

import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = "org.jvault.struct.instancevaultfieldinject")
final class InstanceVaultFieldInjectBean {

    private InstanceVaultFieldInjectBean(){}

    String hello(){
        return this.getClass().getSimpleName();
    }

}
