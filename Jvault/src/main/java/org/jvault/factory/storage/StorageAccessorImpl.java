package org.jvault.factory.storage;

import org.jvault.factory.Accessors;
import org.jvault.factory.extensible.BuildStorage;
import org.jvault.metadata.InternalAPI;

@InternalAPI
final class StorageAccessorImpl extends Accessors.StorageAccessor {

    @Override
    protected BuildStorage getBuildStorage() {
        return DefaultBuildStorage.getInstance();
    }

    static{
        Accessors.StorageAccessor.registerAccessor(new StorageAccessorImpl());
    }

}
