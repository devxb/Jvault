package org.jvault.factory.extensible;

import org.jvault.bean.Bean;
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
public interface BeanLoader {

    Map<String, Bean> load(List<Class<?>> beanClasses);

}
