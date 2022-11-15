package org.jvault.struct.typenew;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;

@InternalBean(type = Type.NEW, accessPackages = "org.jvault.*")
public final class TypeNewA {

    private final TypeNewB TYPE_NEW_B;

    public String hello(){
        return this.getClass().getSimpleName() + TYPE_NEW_B.hello();
    }

    @Inject
    private TypeNewA(@Inject("typeNewB") TypeNewB typeNewB){
        TYPE_NEW_B = typeNewB;
    }

}
