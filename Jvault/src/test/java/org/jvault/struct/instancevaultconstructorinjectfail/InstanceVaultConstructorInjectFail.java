package org.jvault.struct.instancevaultconstructorinjectfail;

import org.jvault.annotation.Inject;

public final class InstanceVaultConstructorInjectFail {

    private InstanceVaultConstructorInjectFailBean failBean;

    public InstanceVaultConstructorInjectFail(){}

    @Inject
    private InstanceVaultConstructorInjectFail(@Inject("instanceVaultConstructorInjectFailBean") InstanceVaultConstructorInjectFailBean failBean){}

}
