package org.jvault.beans;

public final class SingletonBean implements Bean {

    private final String NAME;
    private final String[] ACCESSES;
    private final Object INSTANCE;

    private SingletonBean(Bean.Builder<SingletonBean> builder){
        NAME = builder.name;
        INSTANCE = builder.instance;
        ACCESSES = builder.accesses;
    }

    @Override
    public boolean isInjectable(Class<?> cls) {
        if(ACCESSES.length == 0) return true;
        String clsSrc = cls.getPackageName();
        for(String access : ACCESSES){
            if(access.length() > clsSrc.length()) continue;
            if(clsSrc.contains(access)) return true;
        }
        return false;
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
