package org.jvault.struct.genericbean;

import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;

@InternalBean(type = Type.SINGLETON, accessClasses = "org.jvault.struct.genericbean.Generic")
final class GenericBean <V>{
}
