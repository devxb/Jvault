package org.jvault.vault;

public enum VaultType {

    CLASS(new Vault.Builder<ClassVault>() {
            @Override
            public ClassVault build() {
                return new ClassVault(this);
            }
        });

    private final Vault.Builder<? extends Vault<?>> BUILDER;

    <S extends Vault<?>> VaultType(Vault.Builder<S> builder){
        BUILDER = builder;
    }

    <S extends Vault<?>> Vault.Builder<S> getBuilder(){
        return (Vault.Builder<S>) BUILDER;
    }

}
