package org.jvault.struct.singletoninnew;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(type = Type.SINGLETON, accesses = "org.jvault")
public final class TypeSingleton {

    private final TypeNewInSingleton TYPE_NEW_IN_SINGLETON;

    public String hello(){
        return this.getClass().getSimpleName() + TYPE_NEW_IN_SINGLETON.hello();
    }

    @Inject
    private TypeSingleton(@Inject("typeNewInSingleton") TypeNewInSingleton typeNewInSingleton){
        TYPE_NEW_IN_SINGLETON = typeNewInSingleton;
    }

}
