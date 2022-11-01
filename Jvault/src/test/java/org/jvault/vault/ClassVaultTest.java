package org.jvault.vault;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.beanreader.BeanReader;
import org.jvault.factory.ClassVaultFactory;
import org.jvault.factory.buildinfo.AbstractVaultFactoryBuildInfo;
import org.jvault.factory.buildinfo.VaultFactoryBuildInfo;
import org.jvault.struct.buildvault.BuildVault;
import org.jvault.struct.buildvaultcannotinjectbean.BuildVaultCannotInjectBean;
import org.jvault.struct.buildvaultcannotinjectclass.BuildVaultCannotInjectClass;

public class ClassVaultTest {

    @Test
    public void BUILD_VAULT_CREATE_VAULT_TEST(){
        // given
        ClassVaultFactory factory = ClassVaultFactory.getInstance();
        VaultFactoryBuildInfo buildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "BUILD_VAULT_CREATE_VAULT_TEST";
            }

            @Override
            public BeanReader.BeanLocation getBeanLocation() {
                return new BeanReader.BeanLocation(){
                    @Override
                    public String[] getRootPackage() {
                        return new String[]{"org.jvault.struct.buildvault.*"};
                    }

                    @Override
                    public String[] getExcludePackages() {
                        return new String[0];
                    }
                };
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
        VaultFactoryBuildInfo buildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "BUILD_VAULT_CREATE_VAULT_TEST";
            }

            @Override
            public BeanReader.BeanLocation getBeanLocation() {
                return new BeanReader.BeanLocation(){
                    @Override
                    public String[] getRootPackage() {
                        return new String[]{"org.jvault.struct.buildvaultcannotinjectclass.*"};
                    }

                    @Override
                    public String[] getExcludePackages() {
                        return new String[0];
                    }
                };
            }
        };

        // when
        Vault<Class<?>> classVault = factory.get(buildInfo);

        // then
        Assertions.assertThrows(IllegalStateException.class, ()-> classVault.inject(BuildVaultCannotInjectClass.class));
    }

    @Test
    public void BUILD_VAULT_CANNOT_INJECT_BEAN_CREATE_VAULT_TEST(){
        // given
        ClassVaultFactory factory = ClassVaultFactory.getInstance();
        VaultFactoryBuildInfo buildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "BUILD_VAULT_CREATE_VAULT_TEST";
            }

            @Override
            public BeanReader.BeanLocation getBeanLocation() {
                return new BeanReader.BeanLocation(){
                    @Override
                    public String[] getRootPackage() {
                        return new String[]{"org.jvault.struct.buildvaultcannotinjectbean.*"};
                    }

                    @Override
                    public String[] getExcludePackages() {
                        return new String[0];
                    }
                };
            }
        };

        // when
        ClassVault classVault = factory.get(buildInfo);

        // then
        Assertions.assertThrows(IllegalStateException.class, ()-> classVault.inject(BuildVaultCannotInjectBean.class));
    }

}
