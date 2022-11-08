package org.jvault.beanreader;

import org.jvault.annotation.InternalBean;
import org.jvault.exceptions.NoDefinedInternalBeanException;
import org.jvault.util.PackageReader;

import java.util.*;

public final class AnnotatedBeanReader implements BeanReader{

    private static final BeanReader INSTANCE = new AnnotatedBeanReader();
    private final PackageReader PACKAGE_READER;
    {
        PACKAGE_READER = Accessors.UtilAccessor.getAccessor().getPackageReader();
    }

    @Override
    public List<Class<?>> read(BeanLocation beanLocation) {
        List<Class<?>> classes = readFromPackage(beanLocation);
        classes.addAll(readFromClass(beanLocation));
        return classes;
    }

    private List<Class<?>> readFromPackage(BeanLocation beanLocation){
        List<Class<?>> classes = new ArrayList<>();
        String[] packages = beanLocation.getPackages();
        Set<String> excludePackages = initExcludePackages(beanLocation.getExcludePackages());
        for(String pkg : packages){
            if(isContainSelectAllRegex(pkg)) {
                String stringWithoutRegex = pkg.substring(0, pkg.length() - 2);
                if(excludePackages.contains(stringWithoutRegex)) continue;
                classes.addAll(findClasses(stringWithoutRegex, excludePackages));
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
        List<String> excludePackages = new ArrayList<>();
        excludePackages.add(pkg);
        List<String> directories = PACKAGE_READER.findDirectories(pkg);
        for(String directory : directories) excludePackages.addAll(findExcludedPackages(pkg + "." + directory));
        return excludePackages;
    }

    private List<Class<?>> findClasses(String pkg, Set<String> excludePackages){
        List<Class<?>> classes = PACKAGE_READER.findClasses(pkg);
        List<String> directories = PACKAGE_READER.findDirectories(pkg);
        for(String directory : directories) classes.addAll(findClasses(pkg + "." + directory, excludePackages));
        if(excludePackages.contains(pkg)) return new ArrayList<>();
        return classes;
    }

    private List<Class<?>> readFromClass(BeanLocation beanLocation){
        List<Class<?>> classes = new ArrayList<>();
        String[] classSrcs = beanLocation.getClasses();
        if(isEmptyClassSrcs(classSrcs)) return classes;
        for(String classSrc : classSrcs){
            try {
                Class<?> cls = Class.forName(classSrc);
                throwIfIsNotAnnotatedInternalBean(cls);
                classes.add(cls);
            }catch(ClassNotFoundException CNFE){
                throw new NoDefinedInternalBeanException(classSrc);
            }
        }
        return classes;
    }

    private boolean isEmptyClassSrcs(String[] classSrcs){
        return classSrcs.length == 1 && classSrcs[0].equals("");
    }

    private void throwIfIsNotAnnotatedInternalBean(Class<?> cls){
        if(cls.getDeclaredAnnotation(InternalBean.class) == null) throw new NoDefinedInternalBeanException(cls.getSimpleName());
    }

    static BeanReader getInstance(){
        return INSTANCE;
    }

    private AnnotatedBeanReader(){}

}
