package org.jvault.struct.injectInInternalBean;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(name = "A", type = Type.SINGLETON, accessVaults = "org.jvault")
public class A {

    private final B B;

    public String hello(){
        return "A" + B.hello();
    }

    @Inject
    private A(@Inject(name = "B") B B){
        this.B = B;
    }

}
