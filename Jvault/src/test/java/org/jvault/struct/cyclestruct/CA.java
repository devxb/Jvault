package org.jvault.struct.cyclestruct;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accessVaults = "org.jvault")
public class CA {

    @Inject
    CB CB;

}
