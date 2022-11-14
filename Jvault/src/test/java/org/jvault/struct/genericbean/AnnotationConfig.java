package org.jvault.struct.genericbean;

import org.jvault.annotation.BeanWire;
import org.jvault.annotation.VaultConfiguration;

@VaultConfiguration(name = "GenericVault", vaultAccessClasses = "org.jvault.struct.genericbean.Generic")
public final class AnnotationConfig {

    @BeanWire
    private Generic generic;

    @BeanWire
    private GenericBean genericBean;

}
