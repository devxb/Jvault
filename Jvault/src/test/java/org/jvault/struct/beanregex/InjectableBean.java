package org.jvault.struct.beanregex;

import org.jvault.annotation.InternalBean;

@InternalBean(accesses = "org.jvault.struct.beanregex")
public class InjectableBean {

    String hello(){
        return this.getClass().getSimpleName();
    }

}
