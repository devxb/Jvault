package org.jvault.exceptions;

/**
 * Thrown when, a class has more than two constructor that marked @Inject annotation
 *
 * @author devxb
 * @since 0.1
 */
public final class DuplicateInjectConstructorException extends RuntimeException{

    public DuplicateInjectConstructorException(String name){
        super("Duplicate @Inject annotation marked on constructor at \"" + name + "\"");
    }

}
