package org.jvault.struct.buildvaultcannotinjectclass;

import org.jvault.annotation.Inject;

public class BuildVaultCannotInjectClass {

    private final BuildVaultCannotInjectClassBean BUILD_VAULT_CANNOT_INJECT_BEAN;

    public String notBean(){
        return this.getClass().getSimpleName() + BUILD_VAULT_CANNOT_INJECT_BEAN.bean();
    }

    @Inject
    private BuildVaultCannotInjectClass(@Inject("buildVaultCannotInjectClassBean") BuildVaultCannotInjectClassBean buildVaultCannotInjectBean){
        this.BUILD_VAULT_CANNOT_INJECT_BEAN = buildVaultCannotInjectBean;
    }

}
