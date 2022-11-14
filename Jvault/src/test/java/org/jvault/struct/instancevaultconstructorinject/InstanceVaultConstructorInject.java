package org.jvault.struct.instancevaultconstructorinject;

import org.jvault.annotation.Inject;

public final class InstanceVaultConstructorInject {

    private InstanceVaultConstructorInjectBean instanceVaultConstructorInjectBean;

    public InstanceVaultConstructorInject(){}

    @Inject
    private InstanceVaultConstructorInject(@Inject("instanceVaultConstructorInjectBean") InstanceVaultConstructorInjectBean bean){}

    public String hello(){
        return this.getClass().getSimpleName() + instanceVaultConstructorInjectBean.hello();
    }

}
