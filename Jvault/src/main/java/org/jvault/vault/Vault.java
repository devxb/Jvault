package org.jvault.vault;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Vault<P> {
    <R> R inject(P param);

    abstract class Builder<S>{

        protected final Set<String> PRIVILEGE_ACCESS_PACKAGES;
        {
            PRIVILEGE_ACCESS_PACKAGES = new HashSet<>();
        }

        public Builder<S> accessPackages(String... packages){
            for(String pkg : packages) setAccessPackages(pkg);
            return this;
        }

        private void setAccessPackages(String pkg){
            String pkgSrc = pkg.replace(".", "/");
            if(PRIVILEGE_ACCESS_PACKAGES.contains(pkgSrc)) return;
            PRIVILEGE_ACCESS_PACKAGES.add(pkgSrc);
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

        abstract S build();


    }

}
