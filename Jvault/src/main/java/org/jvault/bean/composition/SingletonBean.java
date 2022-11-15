package org.jvault.bean.composition;

import org.jvault.bean.Bean;
import org.jvault.metadata.InternalAPI;

@InternalAPI
public final class SingletonBean implements Bean {

    private final String[] ACCESS_PACKAGES;
    private final String[] ACCESS_CLASSES;
    private final Object INSTANCE;

    private SingletonBean(Bean.Builder<SingletonBean> builder) {
        INSTANCE = builder.getInstance();
        ACCESS_PACKAGES = builder.getAccessPackages();
        ACCESS_CLASSES = builder.getAccessClasses();
    }

    static Builder<SingletonBean> getBuilder() {
        return new Builder<SingletonBean>() {
            @Override
            protected SingletonBean create() {
                return new SingletonBean(this);
            }
        };
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
    @SuppressWarnings("unchecked")
    public <R> R load() {
        return (R) INSTANCE;
    }


}
