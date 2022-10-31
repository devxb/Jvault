package org.jvault.struct.excludepackage;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.struct.excludepackage.exclude.ExcludeBean;
import org.jvault.struct.excludepackage.notexclude.NotExcludeBean;

@InternalBean
public class ExcludePackage {

    @Inject
    public ExcludePackage(@Inject("notExcludeBean") NotExcludeBean notExcludeBean
            , @Inject("excludeBean") ExcludeBean excludeBean){}

}
