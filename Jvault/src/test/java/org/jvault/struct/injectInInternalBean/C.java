package org.jvault.struct.injectInInternalBean;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(name = "C", accessPackages = "org.jvault.*")
public class C {

    public String hello(){
        return "C";
    }

    @Inject
    private C(){}

}
