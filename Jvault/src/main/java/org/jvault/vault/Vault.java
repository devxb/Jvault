package org.jvault.vault;

import org.jvault.beans.Bean;
import org.jvault.exceptions.DisallowedAccessException;
import org.jvault.metadata.API;

import java.util.*;

/**
 * As a bean container, containing scanned Bean. <br>
 * Inject beans to parameter and returns an instance of the parameter
 * <br>
 * <br>
 * Vault can only be instantiated in the org.jvault.* package,<br>
 * and actually you can't force instantiation of Vault without using Reflection.<br>
 * This means that you should not instantiate Vault using Reflection.<br>
 * To obtain Vault, see {@link org.jvault.factory.VaultFactory} interface.
 *
 * @param <P> The type of target to be injected beans, it was determined by the implementation of the Vault interface.
 *
 * @see org.jvault.vault.ClassVault
 * @see org.jvault.factory.VaultFactory
 * @see org.jvault.annotation.Inject
 * @see org.jvault.annotation.InternalBean
 *
 * @author devxb
 * @since 0.1
 */
@API
public interface Vault<P> {

    /**
     * Method of injecting beans into param.<br>
     * The package of param is included in the package list with access to the vault, <br>
     * and the injection succeeds only if the accesses paths of bean contains the package of param.
     * <br>
     * <br>
     * Vault reads the declared @Inject in the param object and injects the corresponding beans,<br>
     * where the accesses of the beans must contain the package path of the param.
     * <br>
     * <br>
     * If the param object is marked with @InternalBean,<br>
     * it finds the corresponding Bean in the Beans stored inside the Vault and returns an instance of param, <br>
     * or if not, it creates a new instance of param, injects the required Bean, and returns an instance of param.
     *
     * @param <R> the type of return instance
     * @param param The type of target to be injected beans, Vault will inject beans into the param.
     * @return Returns an instance of the type received param.
     *
     * @throws DisallowedAccessException Occurs when the package in param is a package that does not have access to Vault,
     * or the Beans to be injected into param cannot be injected into the package in Param.
     *
     * @see org.jvault.annotation.Inject
     * @see org.jvault.annotation.InternalBean
     * @see DisallowedAccessException
     *
     * @author devxb
     * @since 0.1
     */
    <R> R inject(P param);

    abstract class Builder<S>{

        String name;
        String[] accessPackages = new String[0];
        String[] accessClasses = new String[0];
        final Map<String, Bean> BEANS;
        {
            BEANS = new HashMap<>();
        }

        public Builder<S> name(String name){
            this.name = name;
            return this;
        }

        public Builder<S> accessPackages(String... packages){
            accessPackages = packages;
            return this;
        }

        public Builder<S> accessClasses(String... classes){
            accessClasses = classes;
            return this;
        }

        public Builder<S> beans(Map<String, Bean> beans){
            BEANS.putAll(beans);
            return this;
        }

        public abstract S build();

    }

}
