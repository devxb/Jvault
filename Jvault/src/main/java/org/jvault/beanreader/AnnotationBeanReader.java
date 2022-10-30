package org.jvault.beanreader;

import org.jvault.util.PackageReader;

import java.util.List;

public final class AnnotationBeanReader implements BeanReader{

    private final PackageReader packageReader = new PackageReader();

    @Override
    public List<Class<?>> read(BeanLocation beanLocation) {
        String pkg = beanLocation.getRootPackage();
        return findClasses(pkg);
    }

    private List<Class<?>> findClasses(String pkg){
        List<Class<?>> classes = packageReader.findClasses(pkg);
        List<String> directories = packageReader.findDirectories(pkg);
        for(String directory : directories) classes.addAll(findClasses(pkg + "." + directory));
        return classes;
    }

}
