package org.jvault.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.beanreader.BeanReader;
import org.jvault.factory.buildinfo.AbstractVaultFactoryBuildInfo;
import org.jvault.factory.buildinfo.VaultFactoryBuildInfo;
import org.jvault.struct.beanwithfactory.BeanWithFactoryInjectTarget;
import org.jvault.vault.Vault;

public class ClassVaultFactoryTest {

    @Test
    public void BEAN_WITH_FACTORY_CLASS_VAULT_FACTORY_TEST(){
        // given
        ClassVaultFactory factory = ClassVaultFactory.getInstance();
        VaultFactoryBuildInfo buildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "CLASS_VAULT_FACTORY_TEST";
            }

            @Override
            public BeanReader.BeanLocation getBeanLocation() {
                return new BeanReader.BeanLocation(){
                    @Override
                    public String[] getRootPackage() {
                        return new String[]{"org.jvault.struct.beanwithfactory.*"};
                    }

                    @Override
                    public String[] getExcludePackages() {
                        return new String[0];
                    }
                };
            }
        };

        // when
        Vault<Class<?>> vault = factory.get(buildInfo);
        BeanWithFactoryInjectTarget injectTarget = vault.inject(BeanWithFactoryInjectTarget.class);

        // then
        Assertions.assertEquals("BeanWithFactoryBeanWithFactoryBeanABeanWithFactoryBeanBBeanWithFactoryBeanC"
                , injectTarget.hello());
    }

}
