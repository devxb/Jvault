package org.jvault.vault;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.exceptions.DisallowedAccessException;
import org.jvault.factory.TypeVaultFactory;
import org.jvault.factory.TypeVaultFactory;
import org.jvault.factory.buildinfo.AbstractVaultFactoryBuildInfo;
import org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo;
import org.jvault.factory.extensible.VaultFactoryBuildInfo;
import org.jvault.struct.annotationconfigwithclass.AnnotationConfigWithClass;
import org.jvault.struct.buildvault.BuildVault;
import org.jvault.struct.buildvaultcannotinjectbean.BuildVaultCannotInjectBean;
import org.jvault.struct.buildvaultcannotinjectclass.BuildVaultCannotInjectClass;
import org.jvault.struct.duplicatevault.DuplicateVault;
import org.jvault.struct.vaultinjectsingletonbean.AnnotationConfigWithNotScan;
import org.jvault.struct.vaultinjectsingletonbean.AnnotationConfigWithScan;
import org.jvault.struct.vaultinjectsingletonbean.VaultInjectBean;

public class ClassVaultTest {

    @Test
    public void BUILD_VAULT_CREATE_VAULT_TEST(){
        // given
        TypeVaultFactory factory = TypeVaultFactory.getInstance();
        VaultFactoryBuildInfo buildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "BUILD_VAULT_CREATE_VAULT_TEST";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.buildvault.*"};
            }

            @Override
            protected String[] getExcludePackagesImpl() {
                return new String[0];
            }

            @Override
            public String[] getVaultAccessPackages(){
                return new String[]{"org.jvault.struct.buildvault"};
            }

            @Override
            protected String[] getClassesImpl() {
                return new String[0];
            }
        };

        // when
        Vault<Class<?>> classVault = factory.get(buildInfo, VaultType.CLASS);
        BuildVault buildVault = classVault.inject(BuildVault.class);

        // then
        Assertions.assertEquals("BuildVaultBuildVaultBeanABuildVaultBeanBBuildVaultBeanC", buildVault.imNotBean());
    }

    @Test
    public void BUILD_VAULT_CANNOT_INJECT_CLASS_CREATE_VAULT_TEST(){
        // given
        TypeVaultFactory factory = TypeVaultFactory.getInstance();
        VaultFactoryBuildInfo buildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "BUILD_VAULT_CREATE_VAULT_TEST";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.buildvaultcannotinjectclass.*"};
            }

            @Override
            protected String[] getExcludePackagesImpl() {
                return new String[0];
            }

            @Override
            protected String[] getClassesImpl() {
                return new String[0];
            }
        };

        // when
        Vault<Class<?>> classVault = factory.get(buildInfo, VaultType.CLASS);

        // then
        Assertions.assertThrows(DisallowedAccessException.class, ()-> classVault.inject(BuildVaultCannotInjectClass.class));
    }

    @Test
    public void BUILD_VAULT_CANNOT_INJECT_BEAN_CREATE_VAULT_TEST(){
        // given
        TypeVaultFactory factory = TypeVaultFactory.getInstance();
        VaultFactoryBuildInfo buildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "BUILD_VAULT_CREATE_VAULT_TEST";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.buildvaultcannotinjectbean.*"};
            }

            @Override
            protected String[] getExcludePackagesImpl() {
                return new String[0];
            }

            @Override
            protected String[] getClassesImpl() {
                return new String[0];
            }
        };

        // when
        ClassVault classVault = factory.get(buildInfo, VaultType.CLASS);

        // then
        Assertions.assertThrows(DisallowedAccessException.class, ()-> classVault.inject(BuildVaultCannotInjectBean.class));
    }

    @Test
    public void ANNOTATION_CONFIG_WITH_CLASS_CREATE_VAULT_TEST(){
        // given
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();
        AnnotationVaultFactoryBuildInfo annotationVaultFactoryBuildInfo = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.annotationconfigwithclass.AnnotationConfig.class);

        // when
        Vault<Class<?>> vault = vaultFactory.get(annotationVaultFactoryBuildInfo, VaultType.CLASS);
        AnnotationConfigWithClass bean = vault.inject(AnnotationConfigWithClass.class);

        // then
        Assertions.assertEquals("AnnotationConfigWithClassAnnotationConfigWithClassBean", bean.hello());
    }

    @Test
    public void CHOICE_CONSTRUCTOR_INJECT_CLASS_VAULT_TEST(){
        // given
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();
        AnnotationVaultFactoryBuildInfo annotationVaultFactoryBuildInfo = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.choiceconstructorinject.AnnotationConfig.class);

        // then
        Assertions.assertThrows(IllegalStateException.class, ()-> vaultFactory.get(annotationVaultFactoryBuildInfo, VaultType.CLASS));
    }

    @Test
    public void DUPLICATE_VAULT_DETECTED_CLASS_VAULT_TEST(){
        // given
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();
        AnnotationVaultFactoryBuildInfo annotationVaultFactoryBuildInfo
                = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.duplicatevault.AnnotatinConfig.class);
        AnnotationVaultFactoryBuildInfo duplicateAnnotationVaultFactoryBuildInfo
                = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.duplicatevault.DuplicatedAnnotationConfig.class);

        // when
        Vault<Class<?>> vault = vaultFactory.get(annotationVaultFactoryBuildInfo, VaultType.CLASS);
        Vault<Class<?>> duplicatedVault = vaultFactory.get(duplicateAnnotationVaultFactoryBuildInfo, VaultType.CLASS);

        DuplicateVault duplicateVault = vault.inject(DuplicateVault.class);
        DuplicateVault comparedDuplicatedVault = duplicatedVault.inject(DuplicateVault.class);

        // then
        Assertions.assertEquals(duplicateVault, comparedDuplicatedVault);
    }

    @Test
    public void DUPLICATE_VAULT_DETECTED_WITH_VAULT_NAME_VAULT_TEST(){
        // given
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();
        AnnotationVaultFactoryBuildInfo annotationVaultFactoryBuildInfo
                = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.duplicatevault.AnnotatinConfig.class);

        // when
        Vault<Class<?>> vault = vaultFactory.get(annotationVaultFactoryBuildInfo, VaultType.CLASS);
        Vault<Class<?>> duplicatedVault = vaultFactory.get("DUPLICATE_VAULT", VaultType.CLASS);

        DuplicateVault duplicateVault = vault.inject(DuplicateVault.class);
        DuplicateVault comparedDuplicatedVault = duplicatedVault.inject(DuplicateVault.class);

        // then
        Assertions.assertEquals(duplicateVault, comparedDuplicatedVault);
    }

    @Test
    public void VAULT_INJECT_SINGLETON_WITH_SCAN_TEST(){
        // given
        TypeVaultFactory typeVaultFactory = TypeVaultFactory.getInstance();
        AnnotationVaultFactoryBuildInfo buildInfo = new AnnotationVaultFactoryBuildInfo(AnnotationConfigWithScan.class);

        // when
        ClassVault vault = typeVaultFactory.get(buildInfo, VaultType.CLASS);
        VaultInjectBean vaultInjectBean = vault.inject(VaultInjectBean.class);
        VaultInjectBean samePointVaultInjectBean = vault.inject(VaultInjectBean.class);

        // then
        Assertions.assertEquals(vaultInjectBean, samePointVaultInjectBean);
    }

    @Test
    public void VAULT_INJECT_SINGLETON_WITH_NOT_SCAN_TEST(){
        // given
        TypeVaultFactory typeVaultFactory = TypeVaultFactory.getInstance();
        AnnotationVaultFactoryBuildInfo buildInfo = new AnnotationVaultFactoryBuildInfo(AnnotationConfigWithNotScan.class);

        // when
        ClassVault vault = typeVaultFactory.get(buildInfo, VaultType.CLASS);
        VaultInjectBean vaultInjectBean = vault.inject(VaultInjectBean.class);
        VaultInjectBean differentPointVaultInjectBean = vault.inject(VaultInjectBean.class);

        // then
        Assertions.assertNotEquals(vaultInjectBean, differentPointVaultInjectBean);
    }

}
