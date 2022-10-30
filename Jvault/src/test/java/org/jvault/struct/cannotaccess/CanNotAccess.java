package org.jvault.struct.cannotaccess;


import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;

@InternalBean(accesses = "org.jvault")
public final class CanNotAccess {

    private final CanNotAccessTargetBean CAN_NOT_ACCESS_TARGET_BEAN;

    @Inject
    private CanNotAccess(@Inject("canNotAccessTargetBean") CanNotAccessTargetBean canNotAccessTargetBean){
        CAN_NOT_ACCESS_TARGET_BEAN = canNotAccessTargetBean;
    }

}
