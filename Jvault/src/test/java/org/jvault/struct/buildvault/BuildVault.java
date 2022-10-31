package org.jvault.struct.buildvault;

import org.jvault.annotation.Inject;

public class BuildVault {

    private final BuildVaultBean BUILD_VAULT_BEANA;
    private final BuildVaultBean BUILD_VAULT_BEANB;
    private final BuildVaultBean BUILD_VAULT_BEANC;

    public String imNotBean(){
        return this.getClass().getSimpleName()
                + BUILD_VAULT_BEANA.imBean()
                + BUILD_VAULT_BEANB.imBean()
                + BUILD_VAULT_BEANC.imBean();
    }

    @Inject
    private BuildVault(@Inject("buildVaultBeanA") BuildVaultBean buildVaultBeanA,
                       @Inject("buildVaultBeanB") BuildVaultBean buildVaultBeanB,
                       @Inject("buildVaultBeanC") BuildVaultBean buildVaultBeanC){
        this.BUILD_VAULT_BEANA = buildVaultBeanA;
        this.BUILD_VAULT_BEANB = buildVaultBeanB;
        this.BUILD_VAULT_BEANC = buildVaultBeanC;
    }

}
