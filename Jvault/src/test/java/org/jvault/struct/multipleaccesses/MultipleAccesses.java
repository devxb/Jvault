package org.jvault.struct.multipleaccesses;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean
public final class MultipleAccesses {

    @Inject
    private MultipleAccessesTargetBean multipleAccessesTargetBean;

    public String hello(){
        return this.getClass().getSimpleName() + multipleAccessesTargetBean.hello();
    }

}
