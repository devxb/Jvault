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
        String clsSrc = cls.getPackage().getName();
        for(String access : ACCESSES){
            if(isContainSelectAllRegex(access)) {
                String substring = access.substring(0, access.length()-2);
                if (substring.length() > clsSrc.length()) continue;
                if (clsSrc.contains(substring)) return true;
                continue;
            }
            if(access.equals(clsSrc)) return true;
        }
        return false;
    }

    private boolean isContainSelectAllRegex(String pkg){
        return pkg.startsWith(".*", pkg.length()-2);
    }

    @Override
    public <R> R load() {
        return (R) INSTANCE;
    }

    static Builder<SingletonBean> getBuilder(){
        return new Builder<SingletonBean>() {
            @Override
            protected SingletonBean create() {
                return new SingletonBean(this);
            }
        };
    }


}
