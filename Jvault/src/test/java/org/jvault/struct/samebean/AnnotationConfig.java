package org.jvault.struct.samebean;

import org.jvault.annotation.BeanWire;
import org.jvault.annotation.VaultConfiguration;

@VaultConfiguration(name = "SAME_BEAN_VAULT", vaultAccessClasses = "org.jvault.struct.samebean.Same")
public final class AnnotationConfig {

    @BeanWire
    private SameBean sameBean;

}
