package org.jvault.vault;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.exceptions.DisallowedAccessException;
import org.jvault.factory.TypeVaultFactory;
import org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo;
import org.jvault.struct.instancevaultconstructorinject.InstanceVaultConstructorInject;
import org.jvault.struct.instancevaultconstructorinjectfail.InstanceVaultConstructorInjectFail;
import org.jvault.struct.instancevaultfieldinject.InstanceVaultFieldInject;
import org.jvault.struct.instancevaultfieldinjectfail.InstanceVaultFieldInjectFail;
import org.jvault.struct.notannotatedconstructorinject.NotAnnotatedConstructorInject;

public class InstanceVaultTest {

    @Test
    public void INSTANCE_VAULT_FIELD_INJECT_TEST(){
        // given
        AnnotationVaultFactoryBuildInfo buildInfo
                = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.instancevaultfieldinject.AnnotationConfig.class);
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();

        // when
        InstanceVault instanceVault = vaultFactory.get(buildInfo, VaultType.INSTANCE);
        InstanceVaultFieldInject instanceVaultFieldInject = new InstanceVaultFieldInject();
        instanceVault.inject(instanceVaultFieldInject);

        // then
        Assertions.assertEquals("InstanceVaultFieldInjectInstanceVaultFieldInjectBean", instanceVaultFieldInject.hello());
    }

    @Test
    public void INSTANCE_VAULT_FIELD_INJECT_FAIL_TEST(){
        // given
        AnnotationVaultFactoryBuildInfo buildInfo
                = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.instancevaultfieldinjectfail.AnnotationConfig.class);
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();

        // when
        InstanceVault instanceVault = vaultFactory.get(buildInfo, VaultType.INSTANCE);
        InstanceVaultFieldInjectFail instanceVaultFieldInjectFail = new InstanceVaultFieldInjectFail();

        // then
        Assertions.assertThrows(DisallowedAccessException.class, ()-> instanceVault.inject(instanceVaultFieldInjectFail));
    }

    @Test
    public void INSTANCE_VAULT_CONSTRUCTOR_INJECT_TEST(){
        // given
        AnnotationVaultFactoryBuildInfo buildInfo
                = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.instancevaultconstructorinject.AnnotationConfig.class);
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();

        // when
        InstanceVault instanceVault = vaultFactory.get(buildInfo, VaultType.INSTANCE);
        InstanceVaultConstructorInject instanceVaultConstructorInject = new InstanceVaultConstructorInject();
        instanceVault.inject(instanceVaultConstructorInject);

        // then
        Assertions.assertEquals("InstanceVaultConstructorInjectInstanceVaultConstructorInjectBean", instanceVaultConstructorInject.hello());
    }

    @Test
    public void INSTANCE_VAULT_CONSTRUCTOR_INJECT_FAIL_TEST(){
        // given
        AnnotationVaultFactoryBuildInfo buildInfo
                = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.instancevaultconstructorinjectfail.AnnotationConfig.class);
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();

        // when
        InstanceVault instanceVault = vaultFactory.get(buildInfo, VaultType.INSTANCE);
        InstanceVaultConstructorInjectFail instanceVaultConstructorInjectFail = new InstanceVaultConstructorInjectFail();

        // then
        Assertions.assertThrows(IllegalStateException.class, ()-> instanceVault.inject(instanceVaultConstructorInjectFail));
    }

    @Test
    public void NOT_ANNOTATED_CONSTRUCTOR_INJECT(){
        // given
        NotAnnotatedConstructorInject notAnnotatedConstructorInject = new NotAnnotatedConstructorInject();

        // when
        String result = notAnnotatedConstructorInject.hello();

        // then
        Assertions.assertEquals("NotAnnotatedConstructorInjectNotAnnotatedConstructorInjectBean", notAnnotatedConstructorInject.hello());
    }

}
