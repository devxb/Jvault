package org.jvault.struct.cyclestruct;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accesses = "org.jvault.*")
public class CC {

    @Inject
    CA CA;

}
