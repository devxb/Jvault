package org.jvault.struct.underbar_in_package_src;

import org.jvault.annotation.InternalBean;

@InternalBean
public class Can_Read_Underbar {

    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }

}
