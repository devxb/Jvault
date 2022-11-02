package org.jvault.exceptions;

public final class BeanCycledException extends RuntimeException{

    public BeanCycledException(){
        super("Bean cycle detected");
    }

}
