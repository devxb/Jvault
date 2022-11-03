package org.jvault.struct.annotationconfig.son;

import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(accesses = "org.jvault.struct.annotationconfig", type = Type.NEW)
public class AnnotationCSon {

    public String hello(){
        return this.getClass().getSimpleName();
    }

}
