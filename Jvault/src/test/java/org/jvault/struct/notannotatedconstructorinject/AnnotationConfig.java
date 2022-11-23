package org.jvault.struct.notannotatedconstructorinject;

import org.jvault.annotation.BeanWire;
import org.jvault.annotation.VaultConfiguration;

@VaultConfiguration(name = "NOT_ANNOTATED_CONSTRUCTOR_INJECT", vaultAccessPackages = {"org.jvault.struct.notannotatedconstructorinject"})
public class AnnotationConfig {

    @BeanWire NotAnnotatedConstructorInject notAnnotatedConstructorInject;
    @BeanWire NotAnnotatedConstructorInjectBean notAnnotatedConstructorInjectBean;

}
