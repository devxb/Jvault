package org.jvault.struct.newinsingleton;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;

@InternalBean(type = Type.NEW, accessPackages = "org.jvault.*")
public class TypeNew {

    @Inject
    private TypeSingletonInNew typeSingletonInNew;

    public String hello(){
        return this.getClass().getSimpleName() + typeSingletonInNew.hello();
    }

    public String getSonToString(){
        return typeSingletonInNew.toString();
    }

}
