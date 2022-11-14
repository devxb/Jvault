package org.jvault.vault;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.factory.TypeVaultFactory;
import org.jvault.factory.VaultFactory;
import org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo;
import org.jvault.struct.samebean.Same;
import org.jvault.struct.samebean.SameBean;

public class VaultTest {

    @Test
    public void SAME_BEAN_OF_BOTH_CLASSVAULT_AND_INSTANCEVAULT_TEST(){
        // given
        AnnotationVaultFactoryBuildInfo buildInfo = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.samebean.AnnotationConfig.class);
        VaultFactory<VaultType> vaultFactory = TypeVaultFactory.getInstance();

        // when
        ClassVault classVault = vaultFactory.get(buildInfo, VaultType.CLASS);
        InstanceVault instanceVault = vaultFactory.get(buildInfo, VaultType.INSTANCE);

        Same sameBeanByClassVault = classVault.inject(Same.class);
        Same sameBeanByInstanceVault = instanceVault.inject(new Same());

        // then
        Assertions.assertSame(sameBeanByClassVault.sameBean, sameBeanByInstanceVault.sameBean);
    }

}
