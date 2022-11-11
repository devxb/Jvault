package org.jvault.struct.duplicatevault;

import org.jvault.annotation.BeanArea;
import org.jvault.annotation.BeanWire;

@BeanArea(name = "DUPLICATE_VAULT")
public class DuplicatedAnnotationConfig {

    @BeanWire private DuplicateVault duplicateVault;
    @BeanWire private DuplicateVaultBean duplicateVaultBean;

}
