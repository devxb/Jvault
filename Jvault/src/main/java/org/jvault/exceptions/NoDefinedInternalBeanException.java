package org.jvault.exceptions;

/**
 * Thrown when, the Bean with the name corresponding to the value() of the @Inject annotation does not exist.
 *
 * @author devxb
 * @since 0.1
 */
public final class NoDefinedInternalBeanException extends RuntimeException{

    public NoDefinedInternalBeanException(String beanName){
        super("Can not find InternalBean named \"" + beanName + "\"");
    }

}
