package org.jvault.beanreader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AnnotationBeanReaderTest {

    @Test
    public void ANNOTATION_BEAN_READER_TEST(){
        // given
        AnnotationBeanReader annotationBeanReader = new AnnotationBeanReader();
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
        List<Class<?>> classes = annotationBeanReader.read(location);

        // then
        Assertions.assertEquals(4, classes.size());
    }

    @Test
    public void FILED_INJECT_BEAN_READER_TEST(){
        // given
        AnnotationBeanReader annotationBeanReader = new AnnotationBeanReader();
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
        List<Class<?>> classes = annotationBeanReader.read(location);

        // then
        Assertions.assertEquals(3, classes.size());
    }

    @Test
    public void UNDERBAR_IN_PACKAGE_SRC_BEAN_LEADER_TEST(){
        // given
        AnnotationBeanReader annotationBeanReader = new AnnotationBeanReader();
        BeanReader.BeanLocation location = new BeanReader.BeanLocation() {
            @Override
            public String getRootPackage() {
                return "org.jvault.struct.underbar_in_package_src";
            }

            @Override
            public String[] getExcludePackages() {
                return new String[0];
            }
        };

        // when
        List<Class<?>> classes = annotationBeanReader.read(location);

        // then
        Assertions.assertEquals(1, classes.size());
    }

}
