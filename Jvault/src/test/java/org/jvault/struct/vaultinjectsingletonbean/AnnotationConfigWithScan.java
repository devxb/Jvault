package org.jvault.struct.vaultinjectsingletonbean;

import org.jvault.annotation.BeanWire;
import org.jvault.annotation.VaultConfiguration;

@VaultConfiguration(name = "VAULT_INJECT_SINGLETON_BEAN"
        , vaultAccessClasses = "org.jvault.struct.vaultinjectsingletonbean.VaultInjectBean")
public class AnnotationConfigWithScan {

    @BeanWire InternalVaultInjectBean internalVaultInjectBean;
    @BeanWire VaultInjectBean vaultInjectBean;

}
