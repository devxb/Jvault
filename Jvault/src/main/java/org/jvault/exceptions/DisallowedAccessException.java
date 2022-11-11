package org.jvault.exceptions;


/**
 * Thrown when, a package or class that is not included in @InternalBean's accesses tries to receive bean injection,<br>
 * or when a package that is not included in Vault's accesses is passed as a parameter of the Vault.
 *
 * @author devxb
 * @since 0.1
 */
public final class DisallowedAccessException extends RuntimeException{

    public DisallowedAccessException(String target, String accessInfo){
        super("Can not access \"" + target + "\" cause \"" + target + "\" did not allow access package or classes \"" + accessInfo + "\"");
    }

}
