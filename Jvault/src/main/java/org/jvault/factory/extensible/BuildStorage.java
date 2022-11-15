package org.jvault.factory.extensible;

import org.jvault.bean.Bean;
import org.jvault.metadata.InternalExtensiblePoint;

import java.util.Map;

/**
 * Interface that saves the creation information of the vault created at least once.
 *
 * @author devxb
 * @since 0.1
 */
@InternalExtensiblePoint
public interface BuildStorage {

    void cache(StorageInfo request);

    StorageInfo get(String name);

    final class StorageInfo{
        private final String NAME;
        private final String[] ACCESS_PACKAGES;
        private final String[] ACCESS_CLASSES;
        private final Map<String, Bean> BEANS;

        private StorageInfo(){
            throw new UnsupportedOperationException("Can not invoke constructor \"BuildStorage.StorageInfo()\"");
        }

        private StorageInfo(StorageInfo.Builder builder){
            this.NAME = builder.name;
            this.ACCESS_CLASSES = builder.accessClasses;
            this.ACCESS_PACKAGES = builder.accessPackages;
            this.BEANS = builder.beans;
        }

        public String getName(){
            return NAME;
        }

        public String[] getAccessPackages(){
            return ACCESS_PACKAGES;
        }

        public String[] getAccessClasses(){
            return ACCESS_CLASSES;
        }

        public Map<String, Bean> getBeans(){
            return BEANS;
        }

        public static StorageInfo.Builder getBuilder(){
            return new StorageInfo.Builder();
        }

        public static class Builder {
            private String name;
            private String[] accessPackages;
            private String[] accessClasses;
            private Map<String, Bean> beans;

            private Builder(){}

            public Builder name(String name){
                this.name = name;
                return this;
            }

            public Builder accessPackages(String[] accessPackages){
                this.accessPackages = accessPackages;
                return this;
            }

            public Builder accessClasses(String[] accessClasses){
                this.accessClasses = accessClasses;
                return this;
            }

            public Builder beans(Map<String, Bean> beans){
                this.beans = beans;
                return this;
            }

            public StorageInfo build(){
                return new StorageInfo(this);
            }

        }

    }

}
