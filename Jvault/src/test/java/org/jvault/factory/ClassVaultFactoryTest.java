package org.jvault.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.exceptions.InvalidAnnotationConfigClassException;
import org.jvault.factory.buildinfo.AbstractVaultFactoryBuildInfo;
import org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo;
import org.jvault.factory.buildinfo.PropertiesVaultFactoryBuildInfo;
import org.jvault.factory.extensible.VaultFactoryBuildInfoExtensiblePoint;
import org.jvault.struct.annotationconfig.AnnotationConfigBean;
import org.jvault.struct.beanwithfactory.BeanWithFactoryInjectTarget;
import org.jvault.struct.failannotationconfig.FailConfig;
import org.jvault.struct.readfromclass.ReadFromClass;
import org.jvault.struct.scanwithproperties.ScanProperties;
import org.jvault.vault.Vault;

public class ClassVaultFactoryTest {

    @Test
    public void BEAN_WITH_FACTORY_CLASS_VAULT_FACTORY_TEST(){
        // given
        ClassVaultFactory factory = ClassVaultFactory.getInstance();
        VaultFactoryBuildInfoExtensiblePoint buildInfo = new AbstractVaultFactoryBuildInfo() {
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
        Vault<Class<?>> vault = factory.get(buildInfo);
        BeanWithFactoryInjectTarget injectTarget = vault.inject(BeanWithFactoryInjectTarget.class);

        // then
        Assertions.assertEquals("BeanWithFactoryBeanWithFactoryBeanABeanWithFactoryBeanBBeanWithFactoryBeanC"
                , injectTarget.hello());
    }

    @Test
    public void READ_WITH_PROPERTIES_VAULT_FACTORY_TEST(){
        // given
        ClassVaultFactory vaultFactory = ClassVaultFactory.getInstance();
        PropertiesVaultFactoryBuildInfo buildInfo = new PropertiesVaultFactoryBuildInfo("/Users/devxb/develop/Jvault/Jvault/Jvault/src/test/java/org/jvault/struct/scanwithproperties/buildinfo.properties");

        // when
        Vault<Class<?>> vault = vaultFactory.get(buildInfo);
        ScanProperties scanProperties = vault.inject(ScanProperties.class);

        // then
        Assertions.assertEquals("ScanPropertiesPropertiesBean1PropertiesBean2", scanProperties.hello());
    }

    @Test
    public void READ_WITH_ANNOTATION_CONFIG_VAULT_FACTORY_TEST(){
        // given
        ClassVaultFactory vaultFactory = ClassVaultFactory.getInstance();
        AnnotationVaultFactoryBuildInfo buildInfo = new AnnotationVaultFactoryBuildInfo(org.jvault.struct.annotationconfig.AnnotationConfig.class);

        // when
        Vault<Class<?>> vault = vaultFactory.get(buildInfo);
        AnnotationConfigBean annotationConfigBean = vault.inject(AnnotationConfigBean.class);

        // then
        Assertions.assertEquals("AnnotationConfigBeanACBean1ACBean2", annotationConfigBean.hello());
    }

    @Test
    public void FAIL_READ_WITH_ANNOTATION_CONFIG_VAULT_FACTORY_TEST(){
        // given
        ClassVaultFactory vaultFactory = ClassVaultFactory.getInstance();

        // then
        Assertions.assertThrows(InvalidAnnotationConfigClassException.class, ()-> new AnnotationVaultFactoryBuildInfo(FailConfig.class));
    }

    @Test
    public void READ_FROM_CLASS_WITH_PROPERTIES_CONFIG_VAULT_FACTORY_TEST(){
        // given
        ClassVaultFactory classVaultFactory = ClassVaultFactory.getInstance();
        PropertiesVaultFactoryBuildInfo buildInfo = new PropertiesVaultFactoryBuildInfo("/Users/devxb/develop/Jvault/Jvault/Jvault/src/test/java/org/jvault/struct/readfromclass/readfromclass.properties");

        // when
        Vault<Class<?>> vault = classVaultFactory.get(buildInfo);
        ReadFromClass readFromClass = vault.inject(ReadFromClass.class);

        // then
        Assertions.assertEquals("ReadFromClassReadFromClassBean", readFromClass.hello());
    }

}
