package org.jvault.util;

import org.jvault.metadata.InternalAPI;

@InternalAPI
final class UtilAccessorImplOnVault extends org.jvault.vault.Accessors.UtilAccessor{

    @Override
    protected Reflection getReflection() {
        return Reflection.getInstance();
    }

    static{
        org.jvault.vault.Accessors.UtilAccessor.registerAccessor(new UtilAccessorImplOnVault());
    }

}