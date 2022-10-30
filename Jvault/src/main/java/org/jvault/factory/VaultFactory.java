package org.jvault.factory;

import org.jvault.vault.Vault;

public interface VaultFactory {
    <T> Vault<T> get();
}
