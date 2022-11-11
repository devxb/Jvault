package org.jvault.struct.cannotaccess;

import org.jvault.annotation.InternalBean;

@InternalBean(accessPackages = "org.jvault.beanloader.*")
public class CanNotAccessTargetBean {}
