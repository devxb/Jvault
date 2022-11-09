package org.jvault.factory.extensible;

import org.jvault.beans.Bean;
import org.jvault.metadata.ExtensiblePoint;

import java.util.List;
import java.util.Map;

@ExtensiblePoint
public interface BeanLoaderExtensiblePoint {

    Map<String, Bean> load(List<Class<?>> beanClasses);

}
