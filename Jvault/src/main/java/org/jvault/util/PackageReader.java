package org.jvault.util;

import org.jvault.annotation.InternalBean;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class PackageReader {

    private final static PackageReader INSTANCE = new PackageReader();

    private PackageReader(){}

    static PackageReader getInstance(){
        return INSTANCE;
    }

    public List<String> findDirectories(String pkg){
        String pkgSrc = pkg.replace(".", "/");
        List<String> directories = new ArrayList<>();
        URL url = ClassLoader.getSystemClassLoader().getResource(pkgSrc);
        File directory = new File(url.getFile());
        if(directory == null) return directories;
        String[] fileNames = directory.list();
        for(String fileName : fileNames){
            if(isJavaFile(fileName)) continue;
            directories.add(fileName);
        }
        return directories;
    }

    public List<Class<?>> findClasses(String pkg){
        String pkgSrc = pkg.replace(".", "/");
        URL url = ClassLoader.getSystemClassLoader().getResource(pkgSrc);
        List<Class<?>> classes = new ArrayList<>();
        File directory = new File(url.getFile());
        if(directory == null) return classes;
        String[] fileNames = directory.list();
        for(String fileName : fileNames){
            if(!isJavaFile(fileName)) continue;
            String javaFileName = fileName.substring(0, fileName.length()-6);
            Class<?> cls = loadClass(pkg, javaFileName);
            if(cls == null) continue;
            classes.add(cls);
        }
        return classes;
    }

    private boolean isJavaFile(String name){
        return name.contains(".class");
    }

    private Class<?> loadClass(String currentPackage, String javaFileName){
        try{
            Class<?> cls = Class.forName(currentPackage + "." + javaFileName);
            if(Modifier.isAbstract(cls.getModifiers()) || Modifier.isInterface(cls.getModifiers()) || !isInternalBean(cls)) return null;
            return cls;
        }catch(ClassNotFoundException CNFE) {
            return null;
        }
    }

    private boolean isInternalBean(Class<?> cls){
        return cls.getDeclaredAnnotation(InternalBean.class) != null;
    }

}
