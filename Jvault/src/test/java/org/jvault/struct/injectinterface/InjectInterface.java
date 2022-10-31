package org.jvault.struct.injectinterface;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(type = Type.SINGLETON, accesses = {"org.jvault.struct.injectinterface"})
public class InjectInterface {

    private final SomeInterface SOME_INTERFACE;

    public String hello(){
        return this.getClass().getSimpleName() + SOME_INTERFACE.hello();
    }

    @Inject
    private InjectInterface(@Inject("interfaceImplB") SomeInterface someInterface){
        SOME_INTERFACE = someInterface;
    }

}
