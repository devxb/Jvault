package org.jvault.struct.instancevaultfieldinject;

import org.jvault.annotation.Inject;

public final class InstanceVaultFieldInject {

    @Inject("instanceVaultFieldInjectBean")
    private InstanceVaultFieldInjectBean instanceVaultFieldInjectBean;

    public String hello(){
        return this.getClass().getSimpleName() + instanceVaultFieldInjectBean.hello();
    }

}
