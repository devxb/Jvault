package org.jvault.struct.newinsingleton;

import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;

@InternalBean(type = Type.SINGLETON, accessPackages = "org.jvault.*")
final class TypeSingletonInNew {

    String hello(){
        return this.getClass().getSimpleName();
    }

}
