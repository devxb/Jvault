package org.jvault.struct.annotationconfig;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = "org.jvault.*")
public class AnnotationConfigBean {

    private final ACBean1 AC_BEAN1;
    private final ACBean2 AC_BEAN2;

    public String hello(){
        return this.getClass().getSimpleName() + AC_BEAN1.hello() + AC_BEAN2.hello();
    }

    @Inject
    private AnnotationConfigBean(@Inject("aCBean1") ACBean1 bean1, @Inject("aCBean2") ACBean2 bean2){
        AC_BEAN1 = bean1;
        AC_BEAN2 = bean2;
    }

}
