package org.jvault.struct.genericbean;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean
public final class Generic {

    private final GenericBean<Integer> INTEGER_GENERIC_BEAN;
    private final GenericBean<String> STRING_GENERIC_BEAN;
    private final GenericBean<Long> LONG_GENERIC_BEAN;

    public String hello(){
        return this.getClass().getSimpleName();
    }

    @Inject
    private Generic(@Inject("genericBean") GenericBean<Integer> integerGenericBean,
                    @Inject("genericBean") GenericBean<String> stringGenericBean,
                    @Inject("genericBean") GenericBean<Long> longGenericBean){
        INTEGER_GENERIC_BEAN = integerGenericBean;
        STRING_GENERIC_BEAN = stringGenericBean;
        LONG_GENERIC_BEAN = longGenericBean;
    }

}
