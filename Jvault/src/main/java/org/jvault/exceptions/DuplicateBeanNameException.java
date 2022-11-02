package org.jvault.exceptions;

/**
 * Thrown when, a bean with the same name exists within one vault scope.
 *
 * @author devxb
 * @since 0.1
 */
public final class DuplicateBeanNameException extends RuntimeException{

    public DuplicateBeanNameException(String beanName){
        super("Duplicate bean name was founded \"" + beanName + "\"");
    }

}
