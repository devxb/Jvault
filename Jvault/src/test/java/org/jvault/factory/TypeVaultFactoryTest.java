package org.jvault.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.exceptions.InvalidAnnotationConfigClassException;
import org.jvault.factory.buildinfo.AbstractVaultFactoryBuildInfo;
import org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo;
import org.jvault.factory.buildinfo.PropertiesVaultFactoryBuildInfo;
import org.jvault.factory.extensible.VaultFactoryBuildInfo;
import org.jvault.struct.annotationconfig.AnnotationConfigBean;
import org.jvault.struct.beanwithfactory.BeanWithFactoryInjectTarget;
import org.jvault.struct.failannotationconfig.FailConfig;
import org.jvault.struct.readfromclass.ReadFromClass;
import org.jvault.struct.scanwithproperties.ScanProperties;
import org.jvault.factory.extensible.Vault;
import org.jvault.vault.VaultType;

public class TypeVaultFactoryTest {

    @Test
    public void BEAN_WITH_FACTORY_CLASS_VAULT_FACTORY_TEST(){
        // given
        TypeVaultFactory factory = TypeVaultFactory.getInstance();
        VaultFactoryBuildInfo buildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "CLASS_VAULT_FACTORY_TEST";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.beanwithfactory.*"};
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
        Vault<Class<?>> vault = factory.get(buildInfo, VaultType.CLASS);
        BeanWithFactoryInjectTarget injectTarget = vault.inject(BeanWithFactoryInjectTarget.class, BeanWithFactoryInjectTarget.class);

        // then
        Assertions.assertEquals("BeanWithFactoryBeanWithFactoryBeanABeanWithFactoryBeanBBeanWithFactoryBeanC"
                , injectTarget.hello());
    }

    @Test
    public void READ_WITH_PROPERTIES_VAULT_FACTORY_TEST(){
        // given
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();
        PropertiesVaultFactoryBuildInfo buildInfo = new PropertiesVaultFactoryBuildInfo("/Users/devxb/develop/Jvault/Jvault/Jvault/src/test/java/org/jvault/struct/scanwithproperties/buildinfo.properties");

        // when
        Vault<Class<?>> vault = vaultFactory.get(buildInfo, VaultType.CLASS);
        ScanProperties scanProperties = vault.inject(ScanProperties.class, ScanProperties.class);

        // then
        Assertions.assertEquals("ScanPropertiesPropertiesBean1PropertiesBean2", scanProperties.hello());
    }

    @Test
    public void READ_WITH_ANNOTATION_CONFIG_VAULT_FACTORY_TEST(){
        // given
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();
        AnnotationVaultFactoryBuildInfo buildInfo = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.annotationconfig.AnnotationConfig.class);

        // when
        Vault<Class<?>> vault = vaultFactory.get(buildInfo, VaultType.CLASS);
        AnnotationConfigBean annotationConfigBean = vault.inject(AnnotationConfigBean.class, AnnotationConfigBean.class);

        // then
        Assertions.assertEquals("AnnotationConfigBeanACBean1ACBean2", annotationConfigBean.hello());
    }

    @Test
    public void FAIL_READ_WITH_ANNOTATION_CONFIG_VAULT_FACTORY_TEST(){
        Assertions.assertThrows(InvalidAnnotationConfigClassException.class,
                ()-> new AnnotationVaultFactoryBuildInfo(FailConfig.class));
    }

    @Test
    public void READ_FROM_CLASS_WITH_PROPERTIES_CONFIG_VAULT_FACTORY_TEST(){
        // given
        TypeVaultFactory vaultFactory = org.jvault.factory.TypeVaultFactory.getInstance();
        PropertiesVaultFactoryBuildInfo buildInfo = new PropertiesVaultFactoryBuildInfo("/Users/devxb/develop/Jvault/Jvault/Jvault/src/test/java/org/jvault/struct/readfromclass/readfromclass.properties");

        // when
        Vault<Class<?>> vault = vaultFactory.get(buildInfo, VaultType.CLASS);
        ReadFromClass readFromClass = vault.inject(ReadFromClass.class, ReadFromClass.class);

        // then
        Assertions.assertEquals("ReadFromClassReadFromClassBean", readFromClass.hello());
    }

}
