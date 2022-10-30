package org.jvault.struct.emptyaccess;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean
public class EmptyAccess {

    @Inject
    private EmptyAccessTargetBean emptyAccessTargetBean;

    public String hello(){
        return this.getClass().getSimpleName() + emptyAccessTargetBean.hello();
    }

}
