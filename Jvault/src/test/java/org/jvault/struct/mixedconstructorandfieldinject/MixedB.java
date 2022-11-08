package org.jvault.struct.mixedconstructorandfieldinject;

import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = "org.jvault.*")
final class MixedB implements Mixed{

    @Override
    public String hello(){
        return "MixedB";
    }

}
