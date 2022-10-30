package org.jvault.struct.multipleaccesses;

import org.jvault.annotation.InternalBean;

@InternalBean(accesses = {"java.lang",
        "org.jvault",
        "org.jvault.struct",
        "org.jvault.struct.multiplaccesses"})
public class MultipleAccessesTargetBean {

    public String hello(){
        return this.getClass().getSimpleName();
    }

}
