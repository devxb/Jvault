package org.jvault.struct.typenew;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(type = Type.NEW, accessVaults = "org.jvault")
public final class TypeNewA {

    private final TypeNewB TYPE_NEW_B;

    public String hello(){
        return this.getClass().getSimpleName() + TYPE_NEW_B.hello();
    }

    @Inject
    private TypeNewA(@Inject(name = "typeNewB") TypeNewB typeNewB){
        TYPE_NEW_B = typeNewB;
    }

}
