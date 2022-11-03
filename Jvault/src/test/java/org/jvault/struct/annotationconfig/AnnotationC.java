package org.jvault.struct.annotationconfig;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.struct.annotationconfig.son.AnnotationCSon;

@InternalBean(accesses = "org.jvault.*")
public final class AnnotationC {

    private final AnnotationCSon ANNOTATION_C_SON;

    public String hello(){
        return this.getClass().getSimpleName() + ANNOTATION_C_SON.hello();
    }

    @Inject
    public AnnotationC(@Inject("annotationCSon") AnnotationCSon annotationCSon){
        ANNOTATION_C_SON = annotationCSon;
    }

}