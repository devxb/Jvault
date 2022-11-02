package org.jvault.beanreader;

import java.util.List;

public interface BeanReader {

    List<Class<?>> read(BeanLocation beanLocation);

}
