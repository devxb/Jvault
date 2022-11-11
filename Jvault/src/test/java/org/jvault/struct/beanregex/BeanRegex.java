package org.jvault.struct.beanregex;


import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean
public class BeanRegex {

    private final InjectableBean INJECTABLE_BEAN;
    private final RegexInjectableBean REGEX_INJECTABLE_BEAN;

    public String hello(){
        return this.getClass().getSimpleName() + INJECTABLE_BEAN.hello() + REGEX_INJECTABLE_BEAN.hello();
    }

    @Inject
    private BeanRegex(@Inject("injectableBean") InjectableBean injectableBean,
                      @Inject("regexInjectableBean") RegexInjectableBean regexInjectableBean){
        INJECTABLE_BEAN = injectableBean;
        REGEX_INJECTABLE_BEAN = regexInjectableBean;
    }

}
