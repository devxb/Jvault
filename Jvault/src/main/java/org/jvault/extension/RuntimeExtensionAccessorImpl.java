package org.jvault.extension;

import org.jvault.factory.buildinfo.Accessors;
import org.jvault.metadata.InternalAPI;

@InternalAPI
@SuppressWarnings("unused")
final class RuntimeExtensionAccessorImpl extends Accessors.RuntimeExtensionAccessor{

    @Override
    protected JvaultRuntimeExtension getRuntimeExtension() {
        return JvaultRuntimeExtension.getInstance();
    }

    static{
        Accessors.RuntimeExtensionAccessor.registerAccessor(new RuntimeExtensionAccessorImpl());
    }

}
