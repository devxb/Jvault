package org.jvault.util;

final class UtilAccessorImplOnLoader extends org.jvault.beanloader.Accessors.UtilAccessor {

    @Override
    protected Reflection getReflection() {
        return Reflection.getInstance();
    }

    static{
        org.jvault.beanloader.Accessors.UtilAccessor.registerAccessor(new UtilAccessorImplOnLoader());
    }

}