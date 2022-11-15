package org.jvault.struct.typenew;

import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;

@InternalBean(type = Type.NEW, accessPackages = "org.jvault.*")
final class TypeNewC {

    String hello(){
        return this.getClass().getSimpleName();
    }

}
