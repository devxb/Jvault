package org.jvault.beanreader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.factory.buildinfo.extensible.BeanLocationExtensiblePoint;
import org.jvault.factory.buildinfo.extensible.BeanReaderExtensiblePoint;

import java.util.List;

public class AnnotatedBeanReaderTest {

    @Test
    public void ANNOTATION_BEAN_READER_TEST(){
        // given
        BeanReaderExtensiblePoint annotationBeanReader = AnnotatedBeanReader.getInstance();
        BeanLocationExtensiblePoint location = new BeanLocationExtensiblePoint() {
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
        BeanReaderExtensiblePoint annotationBeanReader = AnnotatedBeanReader.getInstance();
        BeanLocationExtensiblePoint location = new BeanLocationExtensiblePoint() {
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
        BeanReaderExtensiblePoint annotationBeanReader = AnnotatedBeanReader.getInstance();
        BeanLocationExtensiblePoint location = new BeanLocationExtensiblePoint() {
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
        BeanReaderExtensiblePoint annotationBeanReader = AnnotatedBeanReader.getInstance();
        BeanLocationExtensiblePoint location = new BeanLocationExtensiblePoint() {
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
        BeanReaderExtensiblePoint beanReader = AnnotatedBeanReader.getInstance();

        BeanLocationExtensiblePoint location = new BeanLocationExtensiblePoint() {
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
        BeanReaderExtensiblePoint beanReader = AnnotatedBeanReader.getInstance();

        BeanLocationExtensiblePoint location = new BeanLocationExtensiblePoint() {
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
        BeanReaderExtensiblePoint beanReader = AnnotatedBeanReader.getInstance();

        BeanLocationExtensiblePoint location = new BeanLocationExtensiblePoint() {
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
        BeanReaderExtensiblePoint beanReader = AnnotatedBeanReader.getInstance();

        BeanLocationExtensiblePoint location = new BeanLocationExtensiblePoint() {
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
        BeanReaderExtensiblePoint beanReader = AnnotatedBeanReader.getInstance();
        BeanLocationExtensiblePoint location = new BeanLocationExtensiblePoint() {
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

}
