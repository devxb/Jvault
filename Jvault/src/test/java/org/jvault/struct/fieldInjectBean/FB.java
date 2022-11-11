package org.jvault.struct.fieldInjectBean;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = "org.jvault.*")
public class FB {

    @Inject("fC")
    private FC FC;

    public String hello(){
        return "B" + FC.hello();
    }

}
