package org.jvault.struct.duplicateconstructor;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accesses = "org.jvault")
public final class DuplicateConstructor {

    private InjectBean injected;

    @Inject
    public DuplicateConstructor(){}

    @Inject
    public DuplicateConstructor(InjectBean injectBean){
        injected = injectBean;
    }

}
