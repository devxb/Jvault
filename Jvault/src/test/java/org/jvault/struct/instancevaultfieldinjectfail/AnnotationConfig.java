package org.jvault.struct.instancevaultfieldinjectfail;

import org.jvault.annotation.BeanWire;
import org.jvault.annotation.VaultConfiguration;

@VaultConfiguration(name = "INSTANCE_VAULT_FIELD_INJECT_FAIL", vaultAccessPackages = "org.jvault.struct.instancevaultfieldinjectfail")
public final class AnnotationConfig {

    @BeanWire
    private InstanceVaultFieldInjectFailBean instanceVaultFieldInjectFailBean;

}
