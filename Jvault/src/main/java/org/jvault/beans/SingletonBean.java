package org.jvault.beans;

import java.util.Set;

public final class SingletonBean implements Bean {

    private final String NAME;
    private final Set<String> ACCESS_VAULTS;
    private final Object INSTANCE;

    private SingletonBean(Bean.Builder<SingletonBean> builder){
        NAME = builder.name;
        INSTANCE = builder.instance;
        ACCESS_VAULTS = builder.ACCESS_VAULTS;
    }

    @Override
    public <R> R load() {
        return (R) INSTANCE;
    }

    static Builder<SingletonBean> getBuilder(){
        return new Builder<>() {
            @Override
            protected SingletonBean create() {
                return new SingletonBean(this);
            }
        };
    }


}
