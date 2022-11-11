package org.jvault.beanreader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.exceptions.NoDefinedInternalBeanException;
import org.jvault.factory.buildinfo.extensible.BeanLocation;
import org.jvault.factory.buildinfo.extensible.BeanReader;
import org.jvault.struct.excludeallpackage.ExcludeAllOuter;
import org.jvault.struct.excludeonlyone.ExcludeOnlyOneOuter;
import org.jvault.struct.excludeonlyone.middle.end.ExcludeOnlyOneEnd;

import java.util.List;

public class AnnotatedBeanReaderTest {

    @Test
    public void ANNOTATION_BEAN_READER_TEST(){
        // given
        BeanReader annotationBeanReader = AnnotatedBeanReader.getInstance();
        BeanLocation location = new BeanLocation() {
            @Override
            public String[] getPackages() {
                return new String[]{"org.jvault.struct.defaultstruct.*"};
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }

            @Override
            public String[] getClasses(){ return new String[0]; }
        };

        // when
        List<Class<?>> classes = annotationBeanReader.read(location);

        // then
        Assertions.assertEquals(4, classes.size());
    }

    @Test
    public void FILED_INJECT_BEAN_READER_TEST(){
        // given
        BeanReader annotationBeanReader = AnnotatedBeanReader.getInstance();
        BeanLocation location = new BeanLocation() {
            @Override
            public String[] getPackages() {
                return new String[]{"org.jvault.struct.fieldInjectBean.*"};
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }

            @Override
            public String[] getClasses(){ return new String[0]; }
        };

        // when
        List<Class<?>> classes = annotationBeanReader.read(location);

        // then
        Assertions.assertEquals(3, classes.size());
    }

    @Test
    public void UNDER_BAR_IN_PACKAGE_SRC_BEAN_LEADER_TEST(){
        // given
        BeanReader annotationBeanReader = AnnotatedBeanReader.getInstance();
        BeanLocation location = new BeanLocation() {
            @Override
            public String[] getPackages() {
                return new String[]{"org.jvault.struct.underbar_in_package_src.*"};
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }

            @Override
            public String[] getClasses(){ return new String[0]; }
        };

        // when
        List<Class<?>> classes = annotationBeanReader.read(location);

        // then
        Assertions.assertEquals(1, classes.size());
    }

    @Test
    public void EXCLUDE_PACKAGE_BEAN_LEADER_TEST(){
        // given
        BeanReader annotationBeanReader = AnnotatedBeanReader.getInstance();
        BeanLocation location = new BeanLocation() {
            @Override
            public String[] getPackages() {
                return new String[]{"org.jvault.struct.excludepackage.*"};
            }

            @Override
            public String[] getExcludePackages() {
                return new String[]{"org.jvault.struct.excludepackage.exclude.*"};
            }

            @Override
            public String[] getClasses(){ return new String[0]; }
        };

        // when
        List<Class<?>> classes = annotationBeanReader.read(location);

        // then
        Assertions.assertEquals(2, classes.size());
    }

    @Test
    public void REGEX_TEST_READ_ALL_EACH_PACKAGE_BEAN_READER_TEST(){
        // given
        BeanReader beanReader = AnnotatedBeanReader.getInstance();

        BeanLocation location = new BeanLocation() {
            @Override
            public String[] getPackages() {
                return new String[]{"org.jvault.struct.regextest", "org.jvault.struct.regextest.regexson1", "org.jvault.struct.regextest.regexson1.regexsonson1"
                , "org.jvault.struct.regextest.regexson2", "org.jvault.struct.regextest.regexson2.regexsonson2"};
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }

            @Override
            public String[] getClasses(){ return new String[0]; }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);

        // then
        Assertions.assertEquals(5, classes.size());
    }

    @Test
    public void REGEX_TEST_READ_ALL_WITH_REGEX_BEAN_READER_TEST(){
        // given
        BeanReader beanReader = AnnotatedBeanReader.getInstance();

        BeanLocation location = new BeanLocation() {
            @Override
            public String[] getPackages() {
                return new String[]{"org.jvault.struct.regextest.*"};
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }

            @Override
            public String[] getClasses(){ return new String[0]; }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);

        // then
        Assertions.assertEquals(5, classes.size());
    }

    @Test
    public void REGEX_TEST_EXCLUDE_SON2_WITH_REGEX_BEAN_READER_TEST(){
        // given
        BeanReader beanReader = AnnotatedBeanReader.getInstance();

        BeanLocation location = new BeanLocation() {
            @Override
            public String[] getPackages() {
                return new String[]{"org.jvault.struct.regextest.*"};
            }

            @Override
            public String[] getExcludePackages() {
                return new String[]{"org.jvault.struct.regextest.regexson2.*"};
            }

            @Override
            public String[] getClasses(){ return new String[0]; }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);

        // then
        Assertions.assertEquals(3, classes.size());
    }

    @Test
    public void REGEX_TEST_EXCLUDE_SON2_EACH_BEAN_READER_TEST(){
        // given
        BeanReader beanReader = AnnotatedBeanReader.getInstance();

        BeanLocation location = new BeanLocation() {
            @Override
            public String[] getPackages() {
                return new String[]{"org.jvault.struct.regextest.*"};
            }

            @Override
            public String[] getExcludePackages() {
                return new String[]{"org.jvault.struct.regextest.regexson2", "org.jvault.struct.regextest.regexson2.regexsonson2"};
            }

            @Override
            public String[] getClasses(){ return new String[0]; }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);

        // then
        Assertions.assertEquals(3, classes.size());
    }

    @Test
    public void READ_FROM_CLASS_BEAN_READER_TEST(){
        // given
        BeanReader beanReader = AnnotatedBeanReader.getInstance();
        BeanLocation location = new BeanLocation() {
            @Override
            public String[] getPackages() {
                return new String[]{};
            }

            @Override
            public String[] getExcludePackages() {
                return new String[]{};
            }

            @Override
            public String[] getClasses(){ return new String[]{"org.jvault.struct.readfromclass.ReadFromClassBean"}; }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);

        // then
        Assertions.assertEquals(1, classes.size());
    }

    @Test
    public void INTERNAL_BEAN_ON_INTERFACE_BEAN_READER_TEST(){
        // given
        BeanReader beanReader = AnnotatedBeanReader.getInstance();
        BeanLocation location = new BeanLocation() {
            @Override
            public String[] getPackages() {
                return new String[]{};
            }

            @Override
            public String[] getExcludePackages() {
                return new String[]{};
            }

            @Override
            public String[] getClasses(){ return new String[]{"org.jvault.struct.internalbeanoninterface.InterfaceInternalBean"}; }
        };

        // then
        Assertions.assertThrows(IllegalStateException.class, ()-> beanReader.read(location));
    }

    @Test
    public void READ_NOT_INTERNAL_BEAN_BEAN_READER_TEST(){
        // given
        BeanReader beanReader = AnnotatedBeanReader.getInstance();
        BeanLocation location = new BeanLocation() {
            @Override
            public String[] getPackages() {
                return new String[]{};
            }

            @Override
            public String[] getExcludePackages() {
                return new String[]{};
            }

            @Override
            public String[] getClasses(){ return new String[]{"org.jvault.struct.readnotinternalbean.NotInternalBean"}; }
        };

        // then
        Assertions.assertThrows(NoDefinedInternalBeanException.class, ()-> beanReader.read(location));
    }

    @Test
    public void EXCLUDE_ONLY_ONE_BEAN_READER_TEST(){
        // given
        BeanReader beanReader = AnnotatedBeanReader.getInstance();
        BeanLocation location = new BeanLocation() {
            @Override
            public String[] getPackages() {
                return new String[]{"org.jvault.struct.excludeonlyone.*"};
            }

            @Override
            public String[] getExcludePackages() {
                return new String[]{"org.jvault.struct.excludeonlyone.middle"};
            }

            @Override
            public String[] getClasses(){ return new String[]{}; }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);

        // then
        Assertions.assertAll(
                ()-> Assertions.assertEquals(2, classes.size()),
                ()-> Assertions.assertTrue(classes.contains(ExcludeOnlyOneOuter.class)),
                ()-> Assertions.assertTrue(classes.contains(ExcludeOnlyOneEnd.class))
        );
    }

    @Test
    public void EXCLUDE_ALL_BEAN_READER_TEST(){
        // given
        BeanReader beanReader = AnnotatedBeanReader.getInstance();
        BeanLocation location = new BeanLocation() {
            @Override
            public String[] getPackages() {
                return new String[]{"org.jvault.struct.excludeallpackage.*"};
            }

            @Override
            public String[] getExcludePackages() {
                return new String[]{"org.jvault.struct.excludeallpackage.middle.*"};
            }

            @Override
            public String[] getClasses(){ return new String[]{}; }
        };

        // when
        List<Class<?>> classes = beanReader.read(location);

        // then
        Assertions.assertAll(
                ()-> Assertions.assertEquals(1, classes.size()),
                ()-> Assertions.assertTrue(classes.contains(ExcludeAllOuter.class))
        );
    }

}
