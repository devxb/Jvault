package org.jvault.struct.buildvaultcannotinjectclass;

import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = "org.jvault.struct.buildvaultcannotinjectclass.*")
final class BuildVaultCannotInjectClassBean {

    public String bean(){
        return this.getClass().getSimpleName();
    }

}
