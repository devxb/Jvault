package org.jvault.struct.beanregex;

import org.jvault.annotation.InternalBean;

@InternalBean(accesses = "org.jvault.struct.*")
public class RegexInjectableBean {

    String hello(){
        return this.getClass().getSimpleName();
    }

}
