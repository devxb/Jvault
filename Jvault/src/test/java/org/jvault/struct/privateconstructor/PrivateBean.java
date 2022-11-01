package org.jvault.struct.privateconstructor;

import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(name = "pb", type = Type.SINGLETON, accesses = "org.jvault.*")
final class PrivateBean {

    String hello(){
        return "private-bean";
    }

}
