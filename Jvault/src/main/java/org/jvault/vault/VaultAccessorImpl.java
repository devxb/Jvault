package org.jvault.vault;

import org.jvault.factory.Accessors;

final class VaultAccessorImpl extends Accessors.VaultAccessor {

    @Override
    protected <S extends Vault<?>> Vault.Builder<S> getBuilder(VaultType vaultType, Class<S> cls) {
        return vaultType.getBuilder();
    }

    static{
        Accessors.VaultAccessor.registerAccessor(new VaultAccessorImpl());
    }

}
