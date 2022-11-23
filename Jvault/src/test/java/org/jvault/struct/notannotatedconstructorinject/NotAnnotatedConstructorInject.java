package org.jvault.struct.notannotatedconstructorinject;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.factory.TypeVaultFactory;
import org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo;
import org.jvault.vault.InstanceVault;
import org.jvault.vault.VaultType;

@InternalBean
public final class NotAnnotatedConstructorInject {

    private NotAnnotatedConstructorInjectBean notAnnotatedConstructorInjectBean;

    public String hello(){
        return this.getClass().getSimpleName() + notAnnotatedConstructorInjectBean.getClass().getSimpleName();
    }

    public NotAnnotatedConstructorInject(){
        AnnotationVaultFactoryBuildInfo buildInfo
                = new AnnotationVaultFactoryBuildInfo(AnnotationConfig.class);
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();
        InstanceVault instanceVault = vaultFactory.get(buildInfo, VaultType.INSTANCE);
        instanceVault.inject(this);
    }

    @Inject
    private NotAnnotatedConstructorInject(NotAnnotatedConstructorInjectBean notAnnotatedConstructorInjectBean){
        this.notAnnotatedConstructorInjectBean = notAnnotatedConstructorInjectBean;
    }


}
