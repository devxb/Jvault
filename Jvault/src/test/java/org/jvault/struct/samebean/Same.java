package org.jvault.struct.samebean;

import org.jvault.annotation.Inject;

public final class Same {

    public SameBean sameBean;

    public Same(){}

    @Inject
    private Same(@Inject("sameBean") SameBean sameBean){
        this.sameBean = sameBean;
    }

}
