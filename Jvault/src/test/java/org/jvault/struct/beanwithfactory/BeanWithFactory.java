package org.jvault.struct.beanwithfactory;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accesses = "org.jvault.struct.beanwithfactory")
final class BeanWithFactory {

    private final BeanWithFactoryBean BEAN_WITH_FACTORY_BEAN_A;
    private final BeanWithFactoryBean BEAN_WITH_FACTORY_BEAN_B;
    private final BeanWithFactoryBean BEAN_WITH_FACTORY_BEAN_C;

    String hello(){
        return this.getClass().getSimpleName()
                + BEAN_WITH_FACTORY_BEAN_A.helloBean()
                + BEAN_WITH_FACTORY_BEAN_B.helloBean()
                + BEAN_WITH_FACTORY_BEAN_C.helloBean();
    }

    @Inject
    private BeanWithFactory(@Inject("beanWithFactoryBeanA") BeanWithFactoryBean ba,
                            @Inject("beanWithFactoryBeanB") BeanWithFactoryBean bb,
                            @Inject("beanWithFactoryBeanC") BeanWithFactoryBean bc){
        BEAN_WITH_FACTORY_BEAN_A = ba;
        BEAN_WITH_FACTORY_BEAN_B = bb;
        BEAN_WITH_FACTORY_BEAN_C = bc;
    }

}
