package org.jvault.util;

import org.jvault.bean.composition.Accessors;
import org.jvault.metadata.InternalAPI;

@InternalAPI
@SuppressWarnings("unused")
final class UtilAccessorImplOnBean extends Accessors.UtilAccessor{
    @Override
    protected Reflection getReflection() {
        return Reflection.getInstance();
    }

    static{
        Accessors.UtilAccessor.registerAccessor(new UtilAccessorImplOnBean());
    }
}
