package org.jvault.beanreader;

import org.jvault.annotation.InternalBean;
import org.jvault.metadata.InternalAPI;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@InternalAPI
final class PackageReader {

    private final static PackageReader INSTANCE = new PackageReader();

    private PackageReader(){}

    static PackageReader getInstance(){
        return INSTANCE;
    }

    List<String> findDirectories(String pkg) {
        String pkgSrc = pkg.replace(".", "/");
        List<String> directories = new ArrayList<>();
        URL url = ClassLoader.getSystemClassLoader().getResource(pkgSrc);
        throwIfCanNotFindUrl(pkg, url);
        File directory = new File(url.getFile());
        String[] fileNames = directory.list();
        for (String fileName : fileNames) {
            if (isJavaFile(fileName)) continue;
            directories.add(fileName);
        }
        return directories;
    }

    List<Class<?>> findClasses(String pkg){
        String pkgSrc = pkg.replace(".", "/");
        URL url = ClassLoader.getSystemClassLoader().getResource(pkgSrc);
        throwIfCanNotFindUrl(pkg, url);
        File directory = new File(url.getFile());
        List<Class<?>> classes = new ArrayList<>();
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

    private void throwIfCanNotFindUrl(String pkg, URL url){
        if(url == null) throw new IllegalStateException("Can not find directory \"" + pkg + "\"");
    }

    private boolean isJavaFile(String name){
        return name.contains(".class");
    }

    private Class<?> loadClass(String currentPackage, String javaFileName){
        try{
            Class<?> cls = Class.forName(currentPackage + "." + javaFileName);
            if(!isInternalBean(cls)) return null;
            throwIfModifierInterface(cls);
            return cls;
        }catch(ClassNotFoundException CNFE) {
            return null;
        }
    }

    private void throwIfModifierInterface(Class<?> cls){
        if(Modifier.isAbstract(cls.getModifiers()) || Modifier.isInterface(cls.getModifiers()))
            throw new IllegalStateException("\"@InternalBean\" annotation Could not marked \"jnterface\" or \"abstract\"");
    }

    private boolean isInternalBean(Class<?> cls){
        return cls.getDeclaredAnnotation(InternalBean.class) != null;
    }

}
