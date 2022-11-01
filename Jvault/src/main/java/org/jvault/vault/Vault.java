package org.jvault.vault;

import org.jvault.beans.Bean;

import java.util.*;

public interface Vault<P> {
    <R> R inject(P param);

    abstract class Builder<S>{

        String[] injectAccesses = new String[0];
        final Map<String, Bean> BEANS;
        {
            BEANS = new HashMap<>();
        }

        public Builder<S> injectAccesses(String... packages){
            injectAccesses = packages;
            return this;
        }

        public Builder<S> beans(Map<String, Bean> beans){
            BEANS.putAll(beans);
            return this;
        }

        public abstract S build();

    }

}
