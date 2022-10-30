package org.jvault.struct.typenew;

import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(type = Type.NEW, accessVaults = "org.jvault")
final class TypeNewC {

    String hello(){
        return this.getClass().getSimpleName();
    }

}
