package org.jvault.vault;

import org.jvault.bean.Bean;
import org.jvault.exceptions.DisallowedAccessException;
import org.jvault.factory.extensible.Vault;

import java.util.Map;

abstract class AbstractVault<P> implements Vault<P> {

    protected final String NAME;
    protected final String[] ACCESS_PACKAGES;
    protected final String[] ACCESS_CLASSES;
    protected final Map<String, Bean> BEANS;

    protected AbstractVault(Vault.Builder<? extends Vault<P>> builder){
        NAME = builder.getName();
        ACCESS_PACKAGES = builder.getAccessPackages();
        ACCESS_CLASSES = builder.getAccessClasses();
        BEANS = builder.getBeans();
    }

    protected final void throwIfParamDoesNotAccessible(Class<?> param){
        if (!isVaultAccessible(param)) throw new DisallowedAccessException(NAME, param.getPackage().getName());
    }
    private boolean isVaultAccessible(Class<?> cls) {
        if (ACCESS_CLASSES.length == 0 && ACCESS_PACKAGES.length == 0) return true;
        if (isVaultAccessibleClass(cls)) return true;
        return isVaultAccessiblePackage(cls);
    }
    private boolean isVaultAccessibleClass(Class<?> cls) {
        String name = cls.getName().replace("$", ".");
        for (String access : ACCESS_CLASSES)
            if (access.equals(name)) return true;
        return false;
    }

    private boolean isVaultAccessiblePackage(Class<?> cls) {
        String src = cls.getPackage().getName();
        for (String vaultAccess : ACCESS_PACKAGES) {
            if (isContainSelectAllRegex(vaultAccess)) {
                String substring = vaultAccess.substring(0, vaultAccess.length() - 2);
                if (substring.length() > src.length()) continue;
                if (src.contains(substring)) return true;
            }
            if (vaultAccess.equals(src)) return true;
        }
        return false;
    }

    private boolean isContainSelectAllRegex(String pkg) {
        return pkg.startsWith(".*", pkg.length() - 2);
    }

}
