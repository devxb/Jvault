package org.jvault.exceptions;


/**
 * Throw when, trying to define beans with BeansArea annotation,<br>
 * if the class is not marked with BeansArea annotation.
 *
 * @author devxb
 * @since 0.1
 */
public final class InvalidAnnotationConfigClassException extends RuntimeException{

    public InvalidAnnotationConfigClassException(String name){
        super("\"" + name + "\"  is not marked as @BeansArea");
    }

}
