package org.jvault.vault;

import org.jvault.beans.Bean;
import org.jvault.util.PackageReader;

import java.util.*;

public interface Vault<P> {
    <R> R inject(P param);

    abstract class Builder<S>{

        final Set<String> PRIVILEGE_ACCESS_PACKAGES;
        final Map<String, Bean> BEANS;
        {
            PRIVILEGE_ACCESS_PACKAGES = new HashSet<>();
            BEANS = new HashMap<>();
        }

        public Builder<S> accessPackages(String... packages){
            for(String pkg : packages) setAccessPackages(pkg);
            return this;
        }

        private void setAccessPackages(String pkg){
            if(PRIVILEGE_ACCESS_PACKAGES.contains(pkg)) return;
            PRIVILEGE_ACCESS_PACKAGES.add(pkg);
            List<String> directories = new PackageReader().findDirectories(pkg);
            for(String directory : directories)
                setAccessPackages(pkg + "." + directory);
        }

        public Builder<S> beans(Map<String, Bean> beans){
            BEANS.putAll(beans);
            return this;
        }

        abstract S build();


    }

}
