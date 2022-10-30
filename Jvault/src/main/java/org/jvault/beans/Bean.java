package org.jvault.beans;

import java.io.File;
import java.net.URL;
import java.util.*;

public interface Bean {
    <R> R load();

    abstract class Builder<S extends Bean>{

        String name;
        final Set<String> ACCESS_VAULTS;
        Object instance;
        Map<String, Bean> beans;
        {
            ACCESS_VAULTS = new HashSet<>();
        }

        public Builder<S> name(String name){
            this.name = name;
            return this;
        }

        public Builder<S> accessPackage(String... accessVaults){
            for(String pkg : accessVaults) setAccessPackages(pkg);
            return this;
        }

        private void setAccessPackages(String pkg){
            String pkgSrc = pkg.replace(".", "/");
            if(ACCESS_VAULTS.contains(pkgSrc)) return;
            ACCESS_VAULTS.add(pkgSrc);
            List<String> directories = findDirectories(pkgSrc);
            for(String directory : directories)
                setAccessPackages(pkg + "." + directory);
        }

        private List<String> findDirectories(String pkgSrc){
            List<String> directories = new ArrayList<>();
            URL url = ClassLoader.getSystemClassLoader().getResource(pkgSrc);
            File directory = new File(url.getFile());
            if(directory == null) return directories;
            String[] fileNames = directory.list();
            for(String fileName : fileNames){
                if(isJavaFile(fileName)) continue;
                directories.add(fileName);
            }
            return directories;
        }

        private boolean isJavaFile(String name){
            return name.contains(".class");
        }

        public Builder<S> instance(Object instance){
            this.instance = instance;
            return this;
        }

        public Builder<S> beans(Map<String, Bean> beans){
            this.beans = beans;
            return this;
        }

        public S build(){
            return create();
        }

        protected abstract S create();

    }

}
