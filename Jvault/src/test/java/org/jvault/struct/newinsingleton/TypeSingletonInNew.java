package org.jvault.struct.newinsingleton;

import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(type = Type.SINGLETON, accessVaults = "org.jvault")
final class TypeSingletonInNew {

    String hello(){
        return this.getClass().getSimpleName();
    }

}
