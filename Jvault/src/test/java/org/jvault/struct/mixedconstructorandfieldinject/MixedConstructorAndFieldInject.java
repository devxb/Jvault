package org.jvault.struct.mixedconstructorandfieldinject;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accesses = "org.jvault")
public final class MixedConstructorAndFieldInject {

    @Inject("mixedA")
    private Mixed mixed;

    public String hello(){
        return mixed.hello();
    }

    @Inject
    public MixedConstructorAndFieldInject(@Inject("mixedB") Mixed mixed){
        this.mixed = mixed;
    }

}
