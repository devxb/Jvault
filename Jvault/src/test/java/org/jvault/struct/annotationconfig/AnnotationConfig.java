package org.jvault.struct.annotationconfig;

import org.jvault.annotation.BeanArea;
import org.jvault.annotation.BeanWire;

@BeanArea(name = "annotationConfig", vaultAccessPackages = "org.jvault.struct.annotationconfig")
public final class AnnotationConfig {

    @BeanWire private AnnotationConfigBean annotationConfigBean;
    @BeanWire private ACBean1 acBean1;
    @BeanWire private ACBean2 acBean2;

    private AnnotationConfig(){}

}
