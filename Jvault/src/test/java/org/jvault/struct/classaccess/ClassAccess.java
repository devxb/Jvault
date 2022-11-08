package org.jvault.struct.classaccess;


import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean
public final class ClassAccess {

    @Inject
    private ClassAccessBean classAccessBean;

    public String hello(){
        return this.getClass().getSimpleName() + classAccessBean.hello();
    }

    private ClassAccess(){}

}
