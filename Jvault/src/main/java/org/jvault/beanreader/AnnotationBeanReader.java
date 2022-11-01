package org.jvault.beanreader;

import org.jvault.util.PackageReader;

import java.util.*;
import java.util.logging.Logger;

public final class AnnotationBeanReader implements BeanReader{

    private static final BeanReader INSTANCE = new AnnotationBeanReader();
    private final PackageReader PACKAGE_READER;
    {
        PACKAGE_READER = Accessors.UtilAccessor.getAccessor().getPackageReader();
    }

    @Override
    public List<Class<?>> read(BeanLocation beanLocation) {
        String[] pakages = beanLocation.getPackages();
        Set<String> excludePackages = initExcludePackages(beanLocation.getExcludePackages());
        List<Class<?>> classes = new ArrayList<>();
        for(String pkg : pakages){
            if(isContainSelectAllRegex(pkg)) {
                classes.addAll(findClasses(pkg.substring(0, pkg.length()-2), excludePackages));
                continue;
            }
            if(excludePackages.contains(pkg)) continue;
            classes.addAll(PACKAGE_READER.findClasses(pkg));
        }
        return classes;
    }

    private Set<String> initExcludePackages(String[] excludePackages){
        Set<String> excludedPackages = new HashSet<>();
        for(String excludePackage : excludePackages){
            if(isContainSelectAllRegex(excludePackage)) excludedPackages.addAll(findExcludedPackages(excludePackage.substring(0, excludePackage.length()-2)));
            else excludedPackages.add(excludePackage);
        }
        return excludedPackages;
    }

    private boolean isContainSelectAllRegex(String pkg){
        return pkg.startsWith(".*", pkg.length()-2);
    }

    private List<String> findExcludedPackages(String pkg){
        List<String> excludePackages = new ArrayList<>(List.of(pkg));
        List<String> directories = PACKAGE_READER.findDirectories(pkg);
        for(String directory : directories) excludePackages.addAll(findExcludedPackages(pkg + "." + directory));
        return excludePackages;
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
