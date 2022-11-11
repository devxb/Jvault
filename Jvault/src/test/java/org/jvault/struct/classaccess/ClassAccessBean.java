package org.jvault.struct.classaccess;


import org.jvault.annotation.InternalBean;

@InternalBean(accessClasses = "org.jvault.struct.classaccess.ClassAccess")
final class ClassAccessBean {

    String hello(){
        return this.getClass().getSimpleName();
    }

    private ClassAccessBean(){}

}
