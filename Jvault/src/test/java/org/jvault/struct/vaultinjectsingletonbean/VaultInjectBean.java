package org.jvault.struct.vaultinjectsingletonbean;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;

@InternalBean(type = Type.SINGLETON)
public class VaultInjectBean {

    @Inject
    private InternalVaultInjectBean internalVaultInjectBean;

}
