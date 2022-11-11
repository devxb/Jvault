package org.jvault.beanreader;

import org.jvault.annotation.InternalBean;
import org.jvault.exceptions.NoDefinedInternalBeanException;
import org.jvault.factory.buildinfo.extensible.BeanLocationExtensiblePoint;
import org.jvault.factory.buildinfo.extensible.BeanReaderExtensiblePoint;
import org.jvault.metadata.InternalAPI;
import org.jvault.util.PackageReader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@InternalAPI
public final class AnnotatedBeanReader implements BeanReaderExtensiblePoint {

    private static final BeanReaderExtensiblePoint INSTANCE = new AnnotatedBeanReader();
    private final PackageReader PACKAGE_READER;

    {
        PACKAGE_READER = Accessors.UtilAccessor.getAccessor().getPackageReader();
    }

    private AnnotatedBeanReader() {}

    static BeanReaderExtensiblePoint getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Class<?>> read(BeanLocationExtensiblePoint beanLocationExtensiblePoint) {
        List<Class<?>> classes = readFromPackage(beanLocationExtensiblePoint);
        classes.addAll(readFromClass(beanLocationExtensiblePoint));
        return classes;
    }

    private List<Class<?>> readFromPackage(BeanLocationExtensiblePoint beanLocationExtensiblePoint) {
        List<Class<?>> classes = new ArrayList<>();
        String[] packages = beanLocationExtensiblePoint.getPackages();
        Set<String> excludePackages = initExcludePackages(beanLocationExtensiblePoint.getExcludePackages());
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
        List<String> directories = PACKAGE_READER.findDirectories(pkg);
        for (String directory : directories) classes.addAll(findClasses(pkg + "." + directory, excludePackages));
        if (excludePackages.contains(pkg)) return new ArrayList<>();
        return classes;
    }

    private List<Class<?>> readFromClass(BeanLocationExtensiblePoint beanLocationExtensiblePoint) {
        List<Class<?>> classes = new ArrayList<>();
        String[] classSrcs = beanLocationExtensiblePoint.getClasses();
        if (isEmptyClassSrcs(classSrcs)) return classes;
        for (String classSrc : classSrcs) {
            Class<?> cls = getClassForSrc(classSrc);
            throwIfIsNotAnnotatedInternalBean(cls);
            classes.add(cls);
        }
        return classes;
    }

    private boolean isEmptyClassSrcs(String[] classSrcs) {
        return classSrcs.length == 1 && classSrcs[0].equals("");
    }

    private Class<?> getClassForSrc(String src){
        try{
            return Class.forName(src);
        } catch (ClassNotFoundException CNFE){
            throw new NoDefinedInternalBeanException(src);
        }
    }

    private void throwIfIsNotAnnotatedInternalBean(Class<?> cls) {
        if (cls.getDeclaredAnnotation(InternalBean.class) == null)
            throw new NoDefinedInternalBeanException(cls.getSimpleName());
    }

}
