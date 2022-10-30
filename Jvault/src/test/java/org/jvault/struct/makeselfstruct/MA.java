package org.jvault.struct.makeselfstruct;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accessVaults = "org.jvault")
public class MA {

    @Inject(name = "mA")
    MA MA;

    public String hello(){
        return "MA";
    }

}
