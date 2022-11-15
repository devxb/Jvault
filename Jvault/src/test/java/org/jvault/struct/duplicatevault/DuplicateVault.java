package org.jvault.struct.duplicatevault;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;

@InternalBean(type = Type.SINGLETON)
public class DuplicateVault {

    @Inject
    private DuplicateVault(@Inject("duplicateVaultBean") DuplicateVaultBean duplicateVaultBean){}

}
