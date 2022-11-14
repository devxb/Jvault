package org.jvault.struct.instancevaultconstructorinject;

import org.jvault.annotation.BeanWire;
import org.jvault.annotation.VaultConfiguration;

@VaultConfiguration(name = "INSTANCE_VAULT_CONSTRUCTOR_INJECT", vaultAccessClasses = "org.jvault.struct.instancevaultconstructorinject.InstanceVaultConstructorInject")
public final class AnnotationConfig {

    @BeanWire
    private InstanceVaultConstructorInjectBean bean;

}
