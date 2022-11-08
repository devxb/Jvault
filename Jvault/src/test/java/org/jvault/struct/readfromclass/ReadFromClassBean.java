package org.jvault.struct.readfromclass;

import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = "org.jvault.struct.readfromclass")
final class ReadFromClassBean {

    public String hello(){
        return this.getClass().getSimpleName();
    }

}
