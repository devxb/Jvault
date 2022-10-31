package org.jvault.struct.buildvaultcannotinjectbean;

import org.jvault.annotation.Inject;

public class BuildVaultCannotInjectBean {

    @Inject
    private BuildVaultCannotInjectBean(@Inject("buildVaultCannotInjectBeanBean")BuildVaultCannotInjectBeanBean bb){}

}
