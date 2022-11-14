package org.jvault.struct.instancevaultconstructorinjectfail;

import org.jvault.annotation.BeanWire;
import org.jvault.annotation.VaultConfiguration;

@VaultConfiguration(name = "INSTANCE_VAULT_CONSTRUCTOR_INJECT_FAIL"
        , vaultAccessClasses = "org.jvault.struct.instancevaultconstructorinjectfail.InstanceVaultConstructorInjectFail")
public final class AnnotationConfig {

    @BeanWire InstanceVaultConstructorInjectFailBean bean;

}
