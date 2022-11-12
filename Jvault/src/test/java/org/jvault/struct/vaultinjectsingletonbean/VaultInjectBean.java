package org.jvault.struct.vaultinjectsingletonbean;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(type = Type.SINGLETON)
public class VaultInjectBean {

    @Inject
    private InternalVaultInjectBean internalVaultInjectBean;

}
