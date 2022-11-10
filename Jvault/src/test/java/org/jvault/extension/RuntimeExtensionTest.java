package org.jvault.extension;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.factory.buildinfo.PropertiesVaultFactoryBuildInfo;
import org.jvault.factory.buildinfo.extensible.BeanReaderExtensiblePoint;
import org.jvault.struct.beanreaderextension.BeanReaderExtension;
import org.jvault.struct.beanreaderextension.BeanReaderExtensionBean;

import java.util.Arrays;
import java.util.List;

public class RuntimeExtensionTest {

    @Test
    public void BEAN_READER_RUNTIME_EXTENSION_TEST(){
        // given
        PropertiesVaultFactoryBuildInfo propertiesVaultFactoryBuildInfo
                = new PropertiesVaultFactoryBuildInfo(
                        "/Users/devxb/develop/Jvault/Jvault/Jvault" +
                                "/src/test/java/org/jvault/struct/beanreaderextension/beanreaderextension.properties");

        BeanReaderExtensiblePoint someExtensibleOfBeanReader = param -> Arrays.asList(BeanReaderExtension.class, BeanReaderExtensionBean.class);

        // when
        JvaultRuntimeExtension.extend(someExtensibleOfBeanReader, BeanReaderExtensiblePoint.class);
        List<Class<?>> classes = propertiesVaultFactoryBuildInfo.getBeanClasses();

        // then
        Assertions.assertAll(
                ()-> Assertions.assertEquals(BeanReaderExtension.class, classes.get(0)),
                ()-> Assertions.assertEquals(BeanReaderExtensionBean.class, classes.get(1))
        );
    }

    @AfterEach
    public void RESET_RUNTIME_EXTENSION(){
        JvaultRuntimeExtension.reset();
    }

}
