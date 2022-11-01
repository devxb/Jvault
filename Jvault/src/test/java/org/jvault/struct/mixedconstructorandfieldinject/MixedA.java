package org.jvault.struct.mixedconstructorandfieldinject;

import org.jvault.annotation.InternalBean;

@InternalBean(accesses = "org.jvault.*")
final class MixedA implements Mixed{

    @Override
    public String hello(){
        return "MixedA";
    }

}
