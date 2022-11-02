package org.jvault.exceptions;

public final class DisallowedAccessPackageException extends RuntimeException{

    public DisallowedAccessPackageException(String target, String injectedPackage){
        super("Can not access \"" + target + "\" cause \"" + target + "\" did not allow access package \"" + injectedPackage + "\"");
    }

}
