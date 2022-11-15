package org.jvault.struct.privateconstructor;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = "org.jvault.*")
public final class PrivateConstructor {

    private final PrivateBean PRIVATE_BEAN;

    @Override
    public String toString(){
        return PRIVATE_BEAN.hello();
    }

    private PrivateConstructor(){
        throw new UnsupportedOperationException("Can not invoke constructor \"PrivateConstructor()\"");
    }

    @Inject
    private PrivateConstructor(@Inject("pb") PrivateBean privateBean){
        PRIVATE_BEAN = privateBean;
    }

}
