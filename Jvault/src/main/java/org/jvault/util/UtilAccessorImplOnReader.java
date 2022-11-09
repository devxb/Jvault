package org.jvault.util;

import org.jvault.metadata.InternalAPI;

@InternalAPI
final class UtilAccessorImplOnReader extends org.jvault.beanreader.Accessors.UtilAccessor{

    @Override
    protected PackageReader getPackageReader() {
        return PackageReader.getInstance();
    }

    static{
        org.jvault.beanreader.Accessors.UtilAccessor.registerAccessor(new UtilAccessorImplOnReader());
    }

}