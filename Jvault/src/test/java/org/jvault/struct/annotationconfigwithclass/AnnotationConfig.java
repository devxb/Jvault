package org.jvault.struct.annotationconfigwithclass;

import org.jvault.annotation.BeanWire;
import org.jvault.annotation.VaultConfiguration;

@VaultConfiguration(name = "ANNOTATION_CONFIG_WITH_CLASS", vaultAccessClasses = "org.jvault.struct.annotationconfigwithclass.AnnotationConfigWithClass")
public final class AnnotationConfig {

    @BeanWire
    private AnnotationConfigWithClassBean annotationConfigWithClassBean;

}
