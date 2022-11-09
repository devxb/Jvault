package org.jvault.beans;

import org.jvault.metadata.API;

/**
 * Enum that determines the behavior of Bean.
 *
 * @see org.jvault.annotation.InternalBean
 *
 * @author devxb
 * @since 0.1
 */
@API
public enum Type {

    /**
     * Make bean singleton. <br><br>
     * When a Bean is injected into a {@link org.jvault.vault.Vault}'s parameter, the same Bean is always injected.<br><br>
     * Singleton is valid only in the same Vault.<br>
     * For example, comparing the address values of Singleton Bean in other Vaults to the current Vault fails.
     */
    SINGLETON,
    /**
     * Make bean prototype. <br><br>
     * When a Bean is injected into a {@link org.jvault.vault.Vault}'s parameter, the same Bean is always different.<br><br>
     * If the SINGLETON bean contains a NEW bean and the client was injected a SINGLETON bean, the same NEW bean is injected.
     */
    NEW

}
