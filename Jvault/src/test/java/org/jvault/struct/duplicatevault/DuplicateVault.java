package org.jvault.struct.duplicatevault;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(type = Type.NEW)
public class DuplicateVault {

    @Inject
    private DuplicateVault(@Inject("duplicateVaultBean") DuplicateVaultBean duplicateVaultBean){}

}
