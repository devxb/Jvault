package org.jvault.exceptions;

public class DuplicateInjectConstructorException extends RuntimeException{

    public DuplicateInjectConstructorException(String name){
        super("Duplicate @Inject annotation marked on constructor at \"" + name + "\"");
    }

}
