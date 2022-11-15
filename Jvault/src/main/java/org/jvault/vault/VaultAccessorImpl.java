package org.jvault.vault;

import org.jvault.factory.Accessors;
import org.jvault.factory.extensible.Vault;
import org.jvault.metadata.InternalAPI;

@InternalAPI
@SuppressWarnings("unused")
final class VaultAccessorImpl extends Accessors.VaultAccessor {

    @Override
    protected <S extends Vault<?>> Vault.Builder<S> getBuilder(VaultType vaultType) {
        return vaultType.getBuilder();
    }

    static{
        Accessors.VaultAccessor.registerAccessor(new VaultAccessorImpl());
    }

}
