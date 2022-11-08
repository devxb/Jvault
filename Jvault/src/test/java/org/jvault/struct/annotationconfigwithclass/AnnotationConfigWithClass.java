package org.jvault.struct.annotationconfigwithclass;

import org.jvault.annotation.Inject;

public final class AnnotationConfigWithClass {

    private final AnnotationConfigWithClassBean BEAN;

    public String hello(){
        return this.getClass().getSimpleName() + BEAN.hello();
    }

    @Inject
    private AnnotationConfigWithClass(@Inject("annotationConfigWithClassBean") AnnotationConfigWithClassBean annotationConfigWithClassBean){
        BEAN = annotationConfigWithClassBean;
    }

}
