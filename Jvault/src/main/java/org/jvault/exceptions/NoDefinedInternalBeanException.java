package org.jvault.exceptions;

public final class NoDefinedInternalBeanException extends RuntimeException{

    public NoDefinedInternalBeanException(String beanName){
        super("Can not find InternalBean named \"" + beanName + "\"");
    }

}
