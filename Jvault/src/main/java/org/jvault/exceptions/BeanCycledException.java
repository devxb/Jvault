package org.jvault.exceptions;

/**
 * Thrown when, a cycle occurs between beans in the process of creating beans.
 *
 * @author devxb
 * @since 0.1
 */
public final class BeanCycledException extends RuntimeException{

    public BeanCycledException(){
        super("Bean cycle detected");
    }

}
