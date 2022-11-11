package org.jvault.struct.emptyaccess;

import org.jvault.annotation.InternalBean;

@InternalBean
public class EmptyAccessTargetBean {

    String hello(){
        return this.getClass().getSimpleName();
    }

}
