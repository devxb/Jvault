package org.jvault.struct.beanregex;

import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = "org.jvault.struct.*")
public class RegexInjectableBean {

    String hello(){
        return this.getClass().getSimpleName();
    }

}
