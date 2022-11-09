package org.jvault.beans;

import org.jvault.metadata.InternalAPI;

import java.util.*;

@InternalAPI
public interface Bean {
    <R> R load();
    boolean isInjectable(Class<?> cls);

    abstract class Builder<S extends Bean>{

        String name;
        String[] accessPackages;
        String[] accessClasses;
        Object instance;
        Map<String, Bean> beans;

        public Builder<S> name(String name){
            this.name = name;
            return this;
        }

        public Builder<S> accessPackages(String... accessPackages){
            this.accessPackages = accessPackages;
            return this;
        }

        public Builder<S> accessClasses(String... accessClasses){
            this.accessClasses = accessClasses;
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
