package org.jvault.struct.mixedconstructorandfieldinject;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accessVaults = "org.jvault")
public final class MixedConstructorAndFieldInject {

    @Inject(name = "mixedA")
    private Mixed mixed;

    public String hello(){
        return mixed.hello();
    }

    @Inject
    public MixedConstructorAndFieldInject(@Inject(name = "mixedB") Mixed mixed){
        this.mixed = mixed;
    }

}
