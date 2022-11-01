package org.jvault.struct.scanwithproperties.propertiesbeans;

import org.jvault.annotation.InternalBean;

@InternalBean(accesses = "org.jvault.struct.scanwithproperties")
public class PropertiesBean1 {

    public String hello(){
        return this.getClass().getSimpleName();
    }

}
