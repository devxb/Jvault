package org.jvault.struct.typenew;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(type = Type.NEW, accesses = "org.jvault.*")
final class TypeNewB {

    private final TypeNewC TYPE_NEW_C;

    String hello(){
        return this.getClass().getSimpleName() + TYPE_NEW_C.hello();
    }

    @Inject
    private TypeNewB(@Inject("typeNewC") TypeNewC typeNewC){
        TYPE_NEW_C = typeNewC;
    }

}
