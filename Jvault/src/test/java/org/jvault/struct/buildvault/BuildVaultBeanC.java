package org.jvault.struct.buildvault;

import org.jvault.annotation.InternalBean;

@InternalBean(accesses = "org.jvault.struct.buildvault")
public class BuildVaultBeanC implements BuildVaultBean{
    @Override
    public String imBean() {
        return this.getClass().getSimpleName();
    }
}
