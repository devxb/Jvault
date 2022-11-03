package org.jvault.factory;

import org.jvault.annotation.BeanArea;
import org.jvault.annotation.BeanWire;
import org.jvault.struct.annotationconfig.AnnotationC;
import org.jvault.struct.annotationconfig.son.AnnotationCSon;

@BeanArea(name = "annotationConfigBean", accesses = "org.jvault.struct.annotationconfig")
public final class AnnotationConfigBean {

    @BeanWire
    private AnnotationC annotationC;

    @BeanWire
    private AnnotationCSon annotationCSon;

}
