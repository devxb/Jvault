package org.jvault.struct.duplicatevault;

import org.jvault.annotation.BeanWire;
import org.jvault.annotation.VaultConfiguration;

@VaultConfiguration(name = "DUPLICATE_VAULT")
public class DuplicatedAnnotationConfig {

    @BeanWire private DuplicateVault duplicateVault;
    @BeanWire private DuplicateVaultBean duplicateVaultBean;

}
