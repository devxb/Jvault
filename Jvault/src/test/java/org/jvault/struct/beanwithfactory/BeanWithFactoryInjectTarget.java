package org.jvault.struct.beanwithfactory;

import org.jvault.annotation.Inject;

public class BeanWithFactoryInjectTarget {

    private final BeanWithFactory BEAN_WITH_FACTORY;

    public String hello(){
        return BEAN_WITH_FACTORY.hello();
    }

    @Inject
    public BeanWithFactoryInjectTarget(@Inject("beanWithFactory") BeanWithFactory beanWithFactory){
        BEAN_WITH_FACTORY = beanWithFactory;
    }

}
