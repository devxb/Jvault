package org.jvault.factory.buildinfo.extensible;

import org.jvault.metadata.ExtensiblePoint;

/**
 * Interface about package and class path where Bean is located.<br><br>
 *
 * When specifying a package path, the .* expression is available, and if the .* expression is attached to the end of the package path,<br>
 * all Beans are scanned up or excluded to the leaf directory of the child package path that contains the package path.<br>
 * (The class path does not allow .* expressions.)<br><br>
 *
 * If .* expression is not used, import Bean contained in that folder.
 *
 * @author devxb
 * @since 0.1
 */
@ExtensiblePoint
public interface BeanLocation {
    /**
     * This method must return package paths that contain beans to scan.<br>
     * If an expression is used, scan the Bean of all leaf package paths, including those package paths.<br>
     * If not, examine only the Bean contained in those package paths.<br><br>
     *
     * If Empty, No Bean is scanned.
     *
     * @return String[] Package paths containing Bean to scan.
     *
     * @author devxb
     * @since 0.1
     */
    String[] getPackages();

    /**
     * This method must return the package path of the beans to be excluded from the scan.<br>
     * If an expression is used, exclude the Bean of all leaf package paths, including those package paths.<br>
     * If not, exclude only the Bean contained in those package paths.<br><br>
     *
     * If Empty, No Bean is Excluded.
     *
     * @return String[] Package paths excluding Bean to scan.
     */
    String[] getExcludePackages();

    /**
     * This method must return the class name with package. <br>
     * The class of the return value of this method will be registered as a bean.<br>
     * <br>
     * If the class cannot be found, or there is no @InternalBean annotation in the founded class,<br>
     * {@link org.jvault.exceptions.NoDefinedInternalBeanException} is thrown.
     *
     * @return String[] Class package with name
     */
    String[] getClasses();

}
