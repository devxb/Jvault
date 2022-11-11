package org.jvault.struct.singletoninnew;

import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(type = Type.NEW, accessPackages = "org.jvault.*")
final class TypeNewInSingleton {

    String hello(){
        return this.getClass().getSimpleName();
    }

    private TypeNewInSingleton(){}

}
