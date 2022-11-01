package org.jvault.struct.beanwithfactory;

import org.jvault.annotation.InternalBean;

@InternalBean(accesses = "org.jvault.struct.beanwithfactory.*")
final class BeanWithFactoryBeanC implements BeanWithFactoryBean{
    @Override
    public String helloBean() {
        return this.getClass().getSimpleName();
    }
}