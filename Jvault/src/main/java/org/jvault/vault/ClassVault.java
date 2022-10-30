package org.jvault.vault;

import org.jvault.beans.Bean;

import java.util.Map;
import java.util.Set;

public final class ClassVault implements Vault<Class<?>>{

    private final Set<String> PRIVILEGE_ACCESS_PACKAGES;
    private final Map<String, Bean> BEANS;

    private ClassVault(){
        throw new UnsupportedOperationException("Can not invoke constructor \"ClassVault()\"");
    }

    private ClassVault(Vault.Builder<ClassVault> builder){
        PRIVILEGE_ACCESS_PACKAGES = builder.PRIVILEGE_ACCESS_PACKAGES;
        BEANS = builder.BEANS;
    }

    @Override
    public <R> R inject(Class<?> param) {
        return null;
    }

    public static Vault.Builder<ClassVault> getBuilder(){
        return new Builder<>() {
            @Override
            ClassVault build() {
                return new ClassVault(this);
            }
        };
    }

}
