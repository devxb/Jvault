package org.jvault.factory.buildinfo.extensible;

import org.jvault.metadata.ExtensiblePoint;

import java.util.List;

/**
 * Extension points of BeanReader. <br>
 * <br>
 * The implementation of this interface should receive BeanLocation as an argument and return the classes that will be beans. <br>
 * <br>
 * VaultFactoryBuildInfo uses the implementation of this extension point to find the bean. <br>
 *
 * @author devxb
 * @since 0.1
 */
@ExtensiblePoint
public interface BeanReader {

    List<Class<?>> read(BeanLocation param);

}
