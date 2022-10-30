package org.jvault.beans;

import org.jvault.util.PackageReader;

import java.io.File;
import java.net.URL;
import java.util.*;

public interface Bean {
    <R> R load();
    boolean isInjectable(Class<?> cls);

    abstract class Builder<S extends Bean>{

        String name;
        String[] accesses = new String[0];
        Object instance;
        Map<String, Bean> beans;

        public Builder<S> name(String name){
            this.name = name;
            return this;
        }

        public Builder<S> accesses(String... accesses){
            this.accesses = accesses;
            return this;
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
