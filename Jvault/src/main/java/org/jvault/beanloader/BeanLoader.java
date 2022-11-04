package org.jvault.beanloader;

import org.jvault.beans.Bean;

import java.util.List;
import java.util.Map;

public interface BeanLoader {

    Map<String, Bean> load(List<BeanLoadable> beanLoadables);

}
