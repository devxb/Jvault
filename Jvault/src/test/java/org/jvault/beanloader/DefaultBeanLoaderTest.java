package org.jvault.beanloader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.beanreader.AnnotationBeanReader;
import org.jvault.beanreader.BeanReader;
import org.jvault.beans.Bean;
import org.jvault.struct.fieldInjectBean.FA;
import org.jvault.struct.injectInInternalBean.A;
import org.jvault.struct.injectInInternalBean.B;
import org.jvault.struct.makeselfstruct.MA;
import org.jvault.struct.mixedconstructorandfieldinject.MixedConstructorAndFieldInject;
import org.jvault.struct.newinsingleton.TypeNew;
import org.jvault.struct.singletoninnew.TypeSingleton;
import org.jvault.struct.typenew.TypeNewA;

import java.util.List;
import java.util.Map;

public class DefaultBeanLoaderTest {

    @Test
    public void DEFAULT_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AnnotationBeanReader beanReader = new AnnotationBeanReader();
        BeanReader.BeanLocation location = new BeanReader.BeanLocation() {
            @Override
            public String getRootPackage() {
                return "org.jvault.struct.defaultstruct";
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);
        Map<String, Bean> beans = beanLoader.load(classes);

        // then
        Assertions.assertEquals(4, beans.size());
    }

    @Test
    public void INJECT_IN_INTERNALBEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AnnotationBeanReader beanReader = new AnnotationBeanReader();
        BeanReader.BeanLocation location = new BeanReader.BeanLocation() {
            @Override
            public String getRootPackage() {
                return "org.jvault.struct.injectInInternalBean";
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);
        Map<String, Bean> beans = beanLoader.load(classes);

        // then
        A a = beans.get("A").load();
        Assertions.assertEquals("ABC", a.hello());
    }

    @Test
    public void FIELD_INJECT_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AnnotationBeanReader beanReader = new AnnotationBeanReader();
        BeanReader.BeanLocation location = new BeanReader.BeanLocation() {
            @Override
            public String getRootPackage() {
                return "org.jvault.struct.fieldInjectBean";
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);
        Map<String, Bean> beans = beanLoader.load(classes);

        // then
        FA fa = beans.get("fA").load();
        Assertions.assertEquals("ABC", fa.hello());
    }

    @Test
    public void CYCLE_OCCURRED_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AnnotationBeanReader beanReader = new AnnotationBeanReader();
        BeanReader.BeanLocation location = new BeanReader.BeanLocation() {
            @Override
            public String getRootPackage() {
                return "org.jvault.struct.cyclestruct";
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);

        // then
        Assertions.assertThrows(IllegalStateException.class, ()-> beanLoader.load(classes));
    }

    @Test
    public void MAKE_SELFT_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AnnotationBeanReader beanReader = new AnnotationBeanReader();
        BeanReader.BeanLocation location = new BeanReader.BeanLocation() {
            @Override
            public String getRootPackage() {
                return "org.jvault.struct.makeselfstruct";
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);
        Map<String, Bean> beans = beanLoader.load(classes);

        // then
        MA ma = beans.get("mA").load();
        Assertions.assertEquals("MA", ma.hello());
    }

    @Test
    public void DUPLICATED_CONSTRUCTOR_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AnnotationBeanReader beanReader = new AnnotationBeanReader();
        BeanReader.BeanLocation location = new BeanReader.BeanLocation() {
            @Override
            public String getRootPackage() {
                return "org.jvault.struct.duplicateconstructor";
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);

        // then
        Assertions.assertThrows(IllegalStateException.class, ()-> beanLoader.load(classes));
    }

    @Test
    public void PRIVATE_CONSTRUCTOR_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AnnotationBeanReader beanReader = new AnnotationBeanReader();
        BeanReader.BeanLocation location = new BeanReader.BeanLocation() {
            @Override
            public String getRootPackage() {
                return "org.jvault.struct.privateconstructor";
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);
        Map<String, Bean> beans = beanLoader.load(classes);

        // then
        Assertions.assertEquals("private-bean", beans.get("privateConstructor").load().toString());
    }

    @Test
    public void TYPE_NEW_BEAN_LOADER_TEST(){
        // given
        DefaultBeanLoader beanLoader = new DefaultBeanLoader();
        AnnotationBeanReader beanReader = new AnnotationBeanReader();
        BeanReader.BeanLocation location = new BeanReader.BeanLocation() {
            @Override
            public String getRootPackage() {
                return "org.jvault.struct.typenew";
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);
        Map<String, Bean> beans = beanLoader.load(classes);
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
        AnnotationBeanReader beanReader = new AnnotationBeanReader();
        BeanReader.BeanLocation location = new BeanReader.BeanLocation() {
            @Override
            public String getRootPackage() {
                return "org.jvault.struct.singletoninnew";
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);
        Map<String, Bean> beans = beanLoader.load(classes);
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
        AnnotationBeanReader beanReader = new AnnotationBeanReader();
        BeanReader.BeanLocation location = new BeanReader.BeanLocation() {
            @Override
            public String getRootPackage() {
                return "org.jvault.struct.newinsingleton";
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);
        Map<String, Bean> beans = beanLoader.load(classes);
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
        AnnotationBeanReader beanReader = new AnnotationBeanReader();
        BeanReader.BeanLocation location = new BeanReader.BeanLocation() {
            @Override
            public String getRootPackage() {
                return "org.jvault.struct.mixedconstructorandfieldinject";
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);
        Map<String, Bean> beans = beanLoader.load(classes);
        MixedConstructorAndFieldInject result = beans.get("mixedConstructorAndFieldInject").load();

        // then
        Assertions.assertEquals("MixedB", result.hello());
    }

}
