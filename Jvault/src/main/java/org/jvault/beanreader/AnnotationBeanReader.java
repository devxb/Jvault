package org.jvault.beanreader;

import org.jvault.util.PackageReader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class AnnotationBeanReader implements BeanReader{

    private static final BeanReader INSTANCE = new AnnotationBeanReader();
    private final PackageReader PACKAGE_READER;
    {
        PACKAGE_READER = Accessors.UtilAccessor.getAccessor().getPackageReader();
    }

    @Override
    public List<Class<?>> read(BeanLocation beanLocation) {
        String pkg = beanLocation.getRootPackage();
        Set<String> excludePackages = new HashSet<>(Arrays.asList(beanLocation.getExcludePackages()));
        return findClasses(pkg, excludePackages);
    }

    private List<Class<?>> findClasses(String pkg, Set<String> excludePackages){
        List<Class<?>> classes = PACKAGE_READER.findClasses(pkg);
        List<String> directories = PACKAGE_READER.findDirectories(pkg);
        for(String directory : directories) classes.addAll(findClasses(pkg + "." + directory, excludePackages));
        if(excludePackages.contains(pkg)) return List.of();
        return classes;
    }

    static BeanReader getInstance(){
        return INSTANCE;
    }

    private AnnotationBeanReader(){}

}
