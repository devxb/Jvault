package org.jvault.beanreader;

import org.jvault.factory.buildinfo.extensible.BeanLocation;
import org.jvault.factory.buildinfo.extensible.BeanReader;
import org.jvault.metadata.InternalAPI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@InternalAPI
final class AnnotatedBeanReader implements BeanReader {

    private static final BeanReader INSTANCE = new AnnotatedBeanReader();
    private final PackageReader PACKAGE_READER;
    private final ClassReader CLASS_READER;

    {
        PACKAGE_READER = PackageReader.getInstance();
        CLASS_READER = ClassReader.getInstance();
    }

    private AnnotatedBeanReader() {}

    static BeanReader getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Class<?>> read(BeanLocation beanLocation) {
        List<Class<?>> classes = readFromPackage(beanLocation);
        classes.addAll(readFromClass(beanLocation));
        return classes;
    }

    private List<Class<?>> readFromPackage(BeanLocation beanLocation) {
        List<Class<?>> classes = new ArrayList<>();
        String[] packages = beanLocation.getPackages();
        Set<String> excludePackages = initExcludePackages(beanLocation.getExcludePackages());
        for (String pkg : packages) {
            if (isContainSelectAllRegex(pkg)) {
                classes.addAll(getAllClasses(pkg, excludePackages));
                continue;
            }
            if (excludePackages.contains(pkg)) continue;
            classes.addAll(PACKAGE_READER.findClasses(pkg));
        }
        return classes;
    }

    private Set<String> initExcludePackages(String[] excludePackages) {
        Set<String> excludedPackages = new HashSet<>();
        for (String excludePackage : excludePackages) {
            if (isContainSelectAllRegex(excludePackage)) {
                excludedPackages.addAll(findExcludedPackages(excludePackage.substring(0, excludePackage.length() - 2)));
                continue;
            }
            excludedPackages.add(excludePackage);
        }
        return excludedPackages;
    }

    private boolean isContainSelectAllRegex(String pkg) {
        return pkg.startsWith(".*", pkg.length() - 2);
    }

    private List<String> findExcludedPackages(String pkg) {
        List<String> excludePackages = new ArrayList<>();
        excludePackages.add(pkg);
        List<String> directories = PACKAGE_READER.findDirectories(pkg);
        for (String directory : directories) excludePackages.addAll(findExcludedPackages(pkg + "." + directory));
        return excludePackages;
    }

    private List<Class<?>> getAllClasses(String pkg, Set<String> excludePackages){
        String stringWithoutRegex = pkg.substring(0, pkg.length() - 2);
        return new ArrayList<>(findClasses(stringWithoutRegex, excludePackages));
    }

    private List<Class<?>> findClasses(String pkg, Set<String> excludePackages) {
        List<Class<?>> classes = PACKAGE_READER.findClasses(pkg);
        List<Class<?>> sonClasses = new ArrayList<>();
        List<String> directories = PACKAGE_READER.findDirectories(pkg);
        for (String directory : directories) sonClasses.addAll(findClasses(pkg + "." + directory, excludePackages));
        if (excludePackages.contains(pkg)) return sonClasses;
        classes.addAll(sonClasses);
        return classes;
    }

    private List<Class<?>> readFromClass(BeanLocation beanLocation) {
        List<Class<?>> classes = new ArrayList<>();
        String[] classSrcs = beanLocation.getClasses();
        if (isEmptyClassSrcs(classSrcs)) return classes;
        for (String classSrc : classSrcs) classes.add(CLASS_READER.readClass(classSrc));
        return classes;
    }

    private boolean isEmptyClassSrcs(String[] classSrcs) {
        return (classSrcs.length == 1 && classSrcs[0].equals(""))
                || classSrcs.length == 0;
    }

}
