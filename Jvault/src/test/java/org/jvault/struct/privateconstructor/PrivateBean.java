package org.jvault.struct.privateconstructor;

import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;

@InternalBean(name = "pb", type = Type.SINGLETON, accessPackages = "org.jvault.*")
final class PrivateBean {

    String hello(){
        return "private-bean";
    }

}
