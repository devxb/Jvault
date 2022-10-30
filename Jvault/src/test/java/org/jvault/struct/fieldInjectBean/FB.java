package org.jvault.struct.fieldInjectBean;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accessVaults = "org.jvault")
public class FB {

    @Inject(name = "fC")
    private FC FC;

    public String hello(){
        return "B" + FC.hello();
    }

}
