package org.jvault.factory.storage;

import org.jvault.factory.extensible.BuildStorage;
import org.jvault.metadata.InternalAPI;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Storage that stores the information of the vault created at least once.
 *
 * @author devxb
 * @since 0.1
 */
@InternalAPI
public final class DefaultBuildStorage implements BuildStorage {

    private final static DefaultBuildStorage INSTANCE = new DefaultBuildStorage();
    private final ConcurrentMap<String, StorageInfo> STORAGE_INFO;

    {
        STORAGE_INFO = new ConcurrentHashMap<>();
    }

    private DefaultBuildStorage(){}

    static DefaultBuildStorage getInstance(){
        return INSTANCE;
    }

    @Override
    public void cache(StorageInfo request) {
        STORAGE_INFO.put(request.getName(), request);
    }

    @Override
    public StorageInfo get(String name) {
        return STORAGE_INFO.get(name);
    }

}
