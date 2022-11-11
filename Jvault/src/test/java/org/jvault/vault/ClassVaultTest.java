package org.jvault.vault;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.exceptions.DisallowedAccessException;
import org.jvault.factory.ClassVaultFactory;
import org.jvault.factory.buildinfo.AbstractVaultFactoryBuildInfo;
import org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo;
import org.jvault.factory.extensible.VaultFactoryBuildInfoExtensiblePoint;
import org.jvault.struct.annotationconfigwithclass.AnnotationConfigWithClass;
import org.jvault.struct.buildvault.BuildVault;
import org.jvault.struct.buildvaultcannotinjectbean.BuildVaultCannotInjectBean;
import org.jvault.struct.buildvaultcannotinjectclass.BuildVaultCannotInjectClass;
import org.jvault.struct.choiceconstructorinject.ChoiceConstructor;

public class ClassVaultTest {

    @Test
    public void BUILD_VAULT_CREATE_VAULT_TEST(){
        // given
        ClassVaultFactory factory = ClassVaultFactory.getInstance();
        VaultFactoryBuildInfoExtensiblePoint buildInfo = new AbstractVaultFactoryBuildInfo() {
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
        Vault<Class<?>> classVault = factory.get(buildInfo);
        BuildVault buildVault = classVault.inject(BuildVault.class);

        // then
        Assertions.assertEquals("BuildVaultBuildVaultBeanABuildVaultBeanBBuildVaultBeanC", buildVault.imNotBean());
    }

    @Test
    public void BUILD_VAULT_CANNOT_INJECT_CLASS_CREATE_VAULT_TEST(){
        // given
        ClassVaultFactory factory = ClassVaultFactory.getInstance();
        VaultFactoryBuildInfoExtensiblePoint buildInfo = new AbstractVaultFactoryBuildInfo() {
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
        Vault<Class<?>> classVault = factory.get(buildInfo);

        // then
        Assertions.assertThrows(DisallowedAccessException.class, ()-> classVault.inject(BuildVaultCannotInjectClass.class));
    }

    @Test
    public void BUILD_VAULT_CANNOT_INJECT_BEAN_CREATE_VAULT_TEST(){
        // given
        ClassVaultFactory factory = ClassVaultFactory.getInstance();
        VaultFactoryBuildInfoExtensiblePoint buildInfo = new AbstractVaultFactoryBuildInfo() {
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
        ClassVault classVault = factory.get(buildInfo);

        // then
        Assertions.assertThrows(DisallowedAccessException.class, ()-> classVault.inject(BuildVaultCannotInjectBean.class));
    }

    @Test
    public void ANNOTATION_CONFIG_WITH_CLASS_CREATE_VAULT_TEST(){
        // given
        ClassVaultFactory vaultFactory = ClassVaultFactory.getInstance();
        AnnotationVaultFactoryBuildInfo annotationVaultFactoryBuildInfo = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.annotationconfigwithclass.AnnotationConfig.class);

        // when
        Vault<Class<?>> vault = vaultFactory.get(annotationVaultFactoryBuildInfo);
        AnnotationConfigWithClass bean = vault.inject(AnnotationConfigWithClass.class);

        // then
        Assertions.assertEquals("AnnotationConfigWithClassAnnotationConfigWithClassBean", bean.hello());
    }

    @Test
    public void CHOICE_CONSTRUCTOR_INJECT_CLASS_VAULT_TEST(){
        // given
        ClassVaultFactory vaultFactory = ClassVaultFactory.getInstance();
        AnnotationVaultFactoryBuildInfo annotationVaultFactoryBuildInfo = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.choiceconstructorinject.AnnotationConfig.class);

        // then
        Assertions.assertThrows(IllegalStateException.class, ()-> vaultFactory.get(annotationVaultFactoryBuildInfo));
    }

}
