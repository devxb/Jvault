package org.jvault.struct.buildvault;

import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = "org.jvault.struct.buildvault.*")
public class BuildVaultBeanB implements BuildVaultBean{
    @Override
    public String imBean() {
        return this.getClass().getSimpleName();
    }
}
