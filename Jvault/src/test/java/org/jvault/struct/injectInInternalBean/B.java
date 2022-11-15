package org.jvault.struct.injectInInternalBean;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;

@InternalBean(name = "B", type = Type.SINGLETON, accessPackages = "org.jvault.*")
public class B {

    private final C C;

    public String hello(){
        return "B" + C.hello();
    }

    @Inject
    public B(@Inject("C") C C){
        this.C = C;
    }

}
