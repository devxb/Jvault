package org.jvault.struct.fieldInjectBean;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accessVaults = "org.jvault")
public class FA {

    @Inject(name = "fB")
    private FB FB;

    public String hello(){
        return "A" + FB.hello();
    }

}
