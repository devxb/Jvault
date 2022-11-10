package org.jvault.factory.extensible;

import org.jvault.beans.Bean;
import org.jvault.metadata.InternalExtensiblePoint;

import java.util.List;
import java.util.Map;

/**
 * The implementation of this interface should return Beans by receiving the classes.
 *
 * @author devxb
 * @since 0.1
 */
@InternalExtensiblePoint
public interface BeanLoaderExtensiblePoint {

    Map<String, Bean> load(List<Class<?>> beanClasses);

}
