package org.jvault.bean;

import org.jvault.metadata.InternalExtensiblePoint;

import java.util.*;

@InternalExtensiblePoint
public interface Bean {
    <R> R loadIfInjectable(Class<?> cls);
    boolean isInjectable(Class<?> cls);

    abstract class Builder<S extends Bean>{

        private String name;
        private String[] accessPackages;
        private String[] accessClasses;
        private Object instance;
        private Map<String, Bean> beans;

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

        public String getName(){
            return name;
        }

        public String[] getAccessPackages(){
            return accessPackages;
        }

        public String[] getAccessClasses(){
            return accessClasses;
        }

        public Object getInstance(){
            return instance;
        }

        public Map<String, Bean> getBeans(){
            return beans;
        }

        public S build(){
            return create();
        }

        protected abstract S create();

    }

}
