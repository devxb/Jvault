package org.jvault.struct.makeselfstruct;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accesses = "org.jvault.*")
public class MA {

    MA MA;

    @Inject
    public MA(@Inject("mA") MA MA){
        this.MA = MA;
    }

    public String hello(){
        return "MA";
    }

}
