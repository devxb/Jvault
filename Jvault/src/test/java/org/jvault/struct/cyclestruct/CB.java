package org.jvault.struct.cyclestruct;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = "org.jvault.*")
public class CB {

    @Inject
    CC CC;

}
