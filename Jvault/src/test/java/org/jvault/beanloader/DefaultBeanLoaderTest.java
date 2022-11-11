package org.jvault.beanloader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.beans.Bean;
import org.jvault.exceptions.*;
import org.jvault.factory.buildinfo.AbstractVaultFactoryBuildInfo;
import org.jvault.struct.beanregex.BeanRegex;
import org.jvault.struct.classaccess.ClassAccess;
import org.jvault.struct.emptyaccess.EmptyAccess;
import org.jvault.struct.fieldInjectBean.FA;
import org.jvault.struct.injectInInternalBean.A;
import org.jvault.struct.injectinterface.InjectInterface;
import org.jvault.struct.mixedconstructorandfieldinject.MixedConstructorAndFieldInject;
import org.jvault.struct.multipleaccesses.MultipleAccesses;
import org.jvault.struct.newinsingleton.TypeNew;
import org.jvault.struct.readfromclass.ReadFromClass;
import org.jvault.struct.singletoninnew.TypeSingleton;
import org.jvault.struct.typenew.TypeNewA;
import org.jvault.struct.underbar_in_package_src.Can_Read_Underbar;

import java.util.Map;

public class DefaultBeanLoaderTest {

    @Test
    public void DEFAULT_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "DEFAULT_BEAN_LOADER_TEST";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.defaultstruct.*"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());

        // then
        Assertions.assertEquals(4, beans.size());
    }

    @Test
    public void INJECT_IN_INTERNALBEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "INJECT_IN_INTERNALBEAN_LOADER_TEST";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.injectInInternalBean.*"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());

        // then
        A a = beans.get("A").load();
        Assertions.assertEquals("ABC", a.hello());
    }

    @Test
    public void FIELD_INJECT_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();

        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.fieldInjectBean.*"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());

        // then
        FA fa = beans.get("fA").load();
        Assertions.assertEquals("ABC", fa.hello());
    }

    @Test
    public void CYCLE_OCCURRED_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.cyclestruct.*"};
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

        // then
        Assertions.assertThrows(NoDefinedInternalBeanException.class, ()-> beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses()));
    }

    @Test
    public void MAKE_SELF_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.makeselfstruct.*"};
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

        // then
        Assertions.assertThrows(BeanCycledException.class, ()-> beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses()));
    }

    @Test
    public void DUPLICATED_CONSTRUCTOR_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();

        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.duplicateconstructor.*"};
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

        // then
        Assertions.assertThrows(DuplicateInjectConstructorException.class, ()-> beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses()));
    }

    @Test
    public void PRIVATE_CONSTRUCTOR_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.privateconstructor.*"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());

        // then
        Assertions.assertEquals("private-bean", beans.get("privateConstructor").load().toString());
    }

    @Test
    public void TYPE_NEW_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.typenew.*"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());
        TypeNewA typeNewA = beans.get("typeNewA").load();
        TypeNewA difTypeNewA = beans.get("typeNewA").load();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals("TypeNewATypeNewBTypeNewC", typeNewA.hello()),
                () -> Assertions.assertEquals("TypeNewATypeNewBTypeNewC", difTypeNewA.hello()),
                () -> Assertions.assertNotSame(typeNewA, difTypeNewA)
        );
    }

    @Test
    public void TYPE_NEW_IN_SINGLETON_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.singletoninnew.*"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());
        TypeSingleton singleton = beans.get("typeSingleton").load();
        TypeSingleton sameSingleton = beans.get("typeSingleton").load();

        // then
        Assertions.assertAll(
                ()-> Assertions.assertEquals("TypeSingletonTypeNewInSingleton", singleton.hello()),
                ()-> Assertions.assertEquals("TypeSingletonTypeNewInSingleton", sameSingleton.hello()),
                ()-> Assertions.assertSame(singleton, sameSingleton)
        );
    }

    @Test
    public void TYPE_SINGLETON_NEW_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.newinsingleton.*"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());
        TypeNew newInstance = beans.get("typeNew").load();
        TypeNew difNewInstance = beans.get("typeNew").load();

        // then
        Assertions.assertAll(
                ()-> Assertions.assertEquals("TypeNewTypeSingletonInNew", newInstance.hello()),
                ()-> Assertions.assertEquals("TypeNewTypeSingletonInNew", difNewInstance.hello()),
                ()-> Assertions.assertNotSame(newInstance, difNewInstance),
                ()-> Assertions.assertEquals(newInstance.getSonToString(), difNewInstance.getSonToString())
        );
    }

    @Test
    public void MIXED_CONSTRUCTOR_AND_FIELD_INJECT_BEAN_LOADER_TEST() {
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.mixedconstructorandfieldinject.*"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());
        MixedConstructorAndFieldInject result = beans.get("mixedConstructorAndFieldInject").load();

        // then
        Assertions.assertEquals("MixedB", result.hello());
    }

    @Test
    public void CAN_NOT_ACCESS_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.cannotaccess.*"};
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

        // then
        Assertions.assertThrows(DisallowedAccessException.class, ()-> beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses()));
    }

    @Test
    public void MULTIPLE_ACCESS_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.multipleaccesses.*"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());
        MultipleAccesses result = beans.get("multipleAccesses").load();

        // then
        Assertions.assertEquals("MultipleAccessesMultipleAccessesTargetBean", result.hello());
    }

    @Test
    public void EMPTY_ACCESS_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.emptyaccess.*"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());
        EmptyAccess result = beans.get("emptyAccess").load();

        // then
        Assertions.assertEquals("EmptyAccessEmptyAccessTargetBean", result.hello());
    }

    @Test
    public void UNDERBAR_IN_PACKAGE_SRC_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.underbar_in_package_src.*"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());
        Can_Read_Underbar can_read_underbar = beans.get("can_Read_Underbar").load();

        // then
        Assertions.assertEquals(Can_Read_Underbar.class.getSimpleName(), can_read_underbar.toString());
    }

    @Test
    public void INJECT_INTERFACE_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.injectinterface.*"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());
        InjectInterface injectInterface = beans.get("injectInterface").load();

        // then
        Assertions.assertEquals("InjectInterfaceInterfaceImplB", injectInterface.hello());
    }

    @Test
    public void EXCLUDE_PACKAGE_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.excludepackage.*"};
            }

            @Override
            protected String[] getExcludePackagesImpl() {
                return new String[]{"org.jvault.struct.excludepackage.exclude"};
            }

            @Override
            protected String[] getClassesImpl() {
                return new String[0];
            }
        };

        // then
        Assertions.assertThrows(NoDefinedInternalBeanException.class, ()-> beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses()));
    }

    @Test
    public void BEAN_REGEX_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.beanregex"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());
        BeanRegex beanRegex = beans.get("beanRegex").load();

        // then
        Assertions.assertEquals("BeanRegexInjectableBeanRegexInjectableBean", beanRegex.hello());
    }

    @Test
    public void FAIL_BEAN_REGEX_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "TEST_ABSTRACT_FACTORY_BUILD_INFO";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.failbeanregex"};
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

        // then
        Assertions.assertThrows(DisallowedAccessException.class, ()-> beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses()));
    }

    @Test
    public void READ_FROM_CLASS_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "READ_FROM_CLASS_BEAN_LOADER_TEST";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[0];
            }

            @Override
            protected String[] getExcludePackagesImpl() {
                return new String[0];
            }

            @Override
            protected String[] getClassesImpl() {
                return new String[]{"org.jvault.struct.readfromclass.ReadFromClass", "org.jvault.struct.readfromclass.ReadFromClassBean"};
            }
        };

        // when
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());
        ReadFromClass readFromClass = beans.get("readFromClass").load();

        // then
        Assertions.assertEquals("ReadFromClassReadFromClassBean", readFromClass.hello());
    }

    @Test
    public void CLASS_ACCESS_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "CLASS_ACCESS_BEAN_LOADER_TEST";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.classaccess"};
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
        Map<String, Bean> beans = beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses());
        ClassAccess classAccess = beans.get("classAccess").load();

        // then
        Assertions.assertEquals("ClassAccessClassAccessBean", classAccess.hello());
    }

    @Test
    public void DUPLICATE_BEAN_NAME_DETECTED_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AbstractVaultFactoryBuildInfo abstractVaultFactoryBuildInfo = new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return "DUPLICATE_BEAN_NAME_DETECTED";
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[]{"org.jvault.struct.duplicatebean"};
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

        // then
        Assertions.assertThrows(DuplicateBeanNameException.class, ()-> beanLoader.load(abstractVaultFactoryBuildInfo.getBeanClasses()));
    }

}
