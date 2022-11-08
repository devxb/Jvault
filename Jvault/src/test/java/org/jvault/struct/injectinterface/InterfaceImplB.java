package org.jvault.struct.injectinterface;

import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = "org.jvault.struct.injectinterface.*")
final class InterfaceImplB implements SomeInterface{

    @Override
    public String hello() {
        return this.getClass().getSimpleName();
    }

}
