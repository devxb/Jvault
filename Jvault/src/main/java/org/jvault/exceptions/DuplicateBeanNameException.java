package org.jvault.exceptions;

public final class DuplicateBeanNameException extends RuntimeException{

    public DuplicateBeanNameException(String beanName){
        super("Duplicate bean name was founded \"" + beanName + "\"");
    }

}
