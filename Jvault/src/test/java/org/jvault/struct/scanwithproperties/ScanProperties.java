package org.jvault.struct.scanwithproperties;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.struct.scanwithproperties.propertiesbeans.PropertiesBean1;
import org.jvault.struct.scanwithproperties.propertiesbeans.PropertiesBean2;

@InternalBean(accessPackages = "org.jvault.struct.scanwithproperties")
public class ScanProperties {

    @Inject
    private PropertiesBean1 propertiesBean1;

    @Inject
    private PropertiesBean2 propertiesBean2;

    public String hello(){
        return this.getClass().getSimpleName() + propertiesBean1.hello() + propertiesBean2.hello();
    }

}
