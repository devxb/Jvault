package org.jvault.struct.typenew;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;

@InternalBean(type = Type.NEW, accessPackages = "org.jvault.*")
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
