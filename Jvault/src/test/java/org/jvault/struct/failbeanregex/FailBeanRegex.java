package org.jvault.struct.failbeanregex;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean
public class FailBeanRegex {

    @Inject
    public FailBeanRegex(@Inject("injectUnableBean") InjectUnableBean injectUnableBean,
                         @Inject("regexInjectableBean") RegexInjectnableBean regexInjectnableBean){}

}
