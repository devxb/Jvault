package org.jvault.beans;

public enum Type {

    SINGLETON(SingletonBean.getBuilder()),
    NEW(NewBean.getBuilder());

    private final Bean.Builder<?> BUILDER;

    <T extends Bean> Type(Bean.Builder<T> builder){
        this.BUILDER = builder;
    }

    public Bean.Builder<?> getBuilder(){
        return BUILDER;
    }

}
