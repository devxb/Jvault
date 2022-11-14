package org.jvault.struct.instancevaultfieldinject;


import org.jvault.annotation.BeanWire;
import org.jvault.annotation.VaultConfiguration;

@VaultConfiguration(name = "INSTANCE_VAULT_FIELD_INJECT", vaultAccessPackages = "org.jvault.struct.instancevaultfieldinject")
public final class AnnotationConfig {

    @BeanWire
    private InstanceVaultFieldInjectBean instanceVaultFieldInjectBean;

}
