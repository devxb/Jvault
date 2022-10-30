package org.jvault.struct.fieldInjectBean;

import org.jvault.annotation.InternalBean;

@InternalBean(accessVaults = "org.jvault")
public class FC {

    public String hello(){
        return "C";
    }

}
