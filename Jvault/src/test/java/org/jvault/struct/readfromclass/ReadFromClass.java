package org.jvault.struct.readfromclass;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean
public final class ReadFromClass {

    private final ReadFromClassBean READ_FROM_CLASS_BEAN;

    public String hello(){
        return this.getClass().getSimpleName() + READ_FROM_CLASS_BEAN.hello();
    }

    @Inject
    private ReadFromClass(@Inject("readFromClassBean") ReadFromClassBean readFromClassBean){
        READ_FROM_CLASS_BEAN = readFromClassBean;
    }

}
