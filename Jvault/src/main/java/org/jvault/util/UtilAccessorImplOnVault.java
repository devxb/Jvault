package org.jvault.util;

final class UtilAccessorImplOnVault extends org.jvault.vault.Accessors.UtilAccessor{

    @Override
    protected Reflection getReflection() {
        return Reflection.getInstance();
    }

    static{
        org.jvault.vault.Accessors.UtilAccessor.registerAccessor(new UtilAccessorImplOnVault());
    }

}