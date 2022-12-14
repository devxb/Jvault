package org.jvault.factory.extensible;

import org.jvault.bean.Bean;
import org.jvault.exceptions.DisallowedAccessException;
import org.jvault.metadata.InternalExtensiblePoint;

import java.util.*;

/**
 * As a bean container, containing scanned Bean. <br>
 * Inject beans to parameter and returns an instance of the parameter <br> <br>
 * Vault can select classes and packages that can be passed as parameters.<br>
 * If no classes and packages are selected, all classes and packages can be passed as parameters. <br>
 * <br>
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
@InternalExtensiblePoint
public interface Vault<P> {

    /**
     * Method of injecting beans into parameter.<br>
     * The package of parameter is included in the package list with access to the vault, <br>
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
     * @param <R> The type of return instance
     * @param injectTarget The type of target to be injected beans, Vault will inject beans into the param.
     * @param returnType The class-type to be returned. This must be a type that matches the param parameter.
     * @return Returns an instance of the type received param.
     *
     * @throws DisallowedAccessException Occurs when the package or class passed to param, that does not have access to Vault,
     * or the Beans to be injected into param cannot be injected package or class to Param.
     *
     * @see org.jvault.annotation.Inject
     * @see org.jvault.annotation.InternalBean
     * @see DisallowedAccessException
     *
     * @author devxb
     * @since 0.1
     */
    <R> R inject(P injectTarget, Class<R> returnType);

    abstract class Builder<S>{

        private String name;
        private String[] accessPackages;
        private String[] accessClasses;
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

        public String getName(){
            return name;
        }

        public String[] getAccessPackages(){
            return accessPackages;
        }

        public String[] getAccessClasses(){
            return accessClasses;
        }

        public Map<String, Bean> getBeans(){
            return BEANS;
        }

        public abstract S build();

    }

}
