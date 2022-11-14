package org.jvault.struct.instancevaultfieldinjectfail;

import org.jvault.annotation.InternalBean;

@InternalBean(accessClasses = "org.jvault.struct.instancevaultfieldinjectfail")
final class InstanceVaultFieldInjectFailBean {

    public String hello(){
        return this.getClass().getSimpleName();
    }

}
