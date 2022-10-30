package org.jvault.beanreader;

import org.jvault.annotation.InternalBean;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class AnnotationBeanReader implements BeanReader{

    @Override
    public List<Class<?>> read(BeanLocation beanLocation) {
        List<Class<?>> classes = new ArrayList<>();
        findClasses(beanLocation.getRootPackage(), classes);
        return classes;
    }

    private void findClasses(String pkg, List<Class<?>> classes){
        String pkgSrc = pkg.replace(".", "/");
        URL url = ClassLoader.getSystemClassLoader().getResource(pkgSrc);
        File directory = null;
        try {
            directory = new File(url.getFile());
        } catch(NullPointerException NPE){
            throw new IllegalArgumentException("Can not find directory src : \"" + pkgSrc + "\"");
        }
        String[] fileNames = directory.list();
        for(String name : fileNames) {
            if(isJavaFile(name)){
                String javaFileName = name.substring(0, name.length()-6);
                Class<?> cls = loadClass(pkg, javaFileName);
                if(cls == null) continue;
                classes.add(cls);
                continue;
            }
            findClasses(pkg + "." + name, classes);
        }
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
        return cls.getAnnotation(InternalBean.class) != null;
    }

}
