package org.jvault.util;

import org.jvault.beans.Accessors;
import org.jvault.metadata.InternalAPI;

@InternalAPI
final class UtilAccessorImplOnBean extends Accessors.UtilAccessor{
    @Override
    protected Reflection getReflection() {
        return Reflection.getInstance();
    }

    static{
        Accessors.UtilAccessor.registerAccessor(new UtilAccessorImplOnBean());
    }
}
