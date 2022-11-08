package org.jvault.struct.multipleaccesses;

import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = {"java.lang.*",
        "org.jvault",
        "org.jvault.struct",
        "org.jvault.struct.multipleaccesses"})
public class MultipleAccessesTargetBean {

    public String hello(){
        return this.getClass().getSimpleName();
    }

}
