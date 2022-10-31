package org.jvault.util;

final class UtilAccessorImplOnReader extends org.jvault.beanreader.Accessors.UtilAccessor{

    @Override
    protected PackageReader getPackageReader() {
        return PackageReader.getInstance();
    }

    static{
        org.jvault.beanreader.Accessors.UtilAccessor.registerAccessor(new UtilAccessorImplOnReader());
    }

}