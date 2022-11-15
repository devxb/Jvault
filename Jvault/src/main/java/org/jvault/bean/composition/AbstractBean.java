package org.jvault.bean.composition;

import org.jvault.bean.Bean;
import org.jvault.exceptions.DisallowedAccessException;
import org.jvault.metadata.InternalAPI;

import java.util.Map;

@InternalAPI
abstract class AbstractBean implements Bean {

    protected final String NAME;
    protected final String[] ACCESS_PACKAGES;
    protected final String[] ACCESS_CLASSES;
    protected final Object INSTANCE;
    protected final Map<String, Bean> BEANS;

    AbstractBean(Bean.Builder<? extends Bean> builder){
        NAME = builder.getName();
        ACCESS_CLASSES = builder.getAccessClasses();
        ACCESS_PACKAGES = builder.getAccessPackages();
        INSTANCE = builder.getInstance();
        BEANS = builder.getBeans();
    }

    @Override
    public boolean isInjectable(Class<?> cls) {
        if (ACCESS_PACKAGES.length == 0 && ACCESS_CLASSES.length == 0) return true;
        return isClassInjectable(cls) || isPackageInjectable(cls);
    }

    private boolean isClassInjectable(Class<?> cls) {
        String name = cls.getName().replace("$", ".");
        for (String access : ACCESS_CLASSES)
            if (access.equals(name)) return true;
        return false;
    }

    private boolean isPackageInjectable(Class<?> cls) {
        String clsSrc = cls.getPackage().getName();
        for (String access : ACCESS_PACKAGES) {
            if (isContainSelectAllRegex(access)) {
                String substring = access.substring(0, access.length() - 2);
                if (substring.length() > clsSrc.length()) continue;
                if (clsSrc.contains(substring)) return true;
                continue;
            }
            if (access.equals(clsSrc)) return true;
        }
        return false;
    }

    private boolean isContainSelectAllRegex(String pkg) {
        return pkg.startsWith(".*", pkg.length() - 2);
    }

    @Override
    public <R> R loadIfInjectable(Class<?> cls){
        if(!isInjectable(cls)) throw new DisallowedAccessException(INSTANCE.getClass().getName(), cls.getName());
        return load();
    }

    abstract <R> R load();


}
