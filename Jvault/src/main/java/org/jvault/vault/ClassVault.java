package org.jvault.vault;

import org.jvault.annotation.Inject;
import org.jvault.beans.Bean;
import org.jvault.exceptions.DisallowedAccessPackageException;
import org.jvault.exceptions.NoDefinedInternalBeanException;
import org.jvault.util.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ClassVault implements Vault<Class<?>>{

    private final String NAME;
    private final String[] INJECT_ACCESSES;
    private final Map<String, Bean> BEANS;
    private final Reflection REFLECTION;

    private ClassVault(){
        throw new UnsupportedOperationException("Can not invoke constructor \"ClassVault()\"");
    }

    private ClassVault(Vault.Builder<ClassVault> builder){
        NAME = builder.name;
        INJECT_ACCESSES = builder.injectAccesses;
        BEANS = builder.BEANS;
        REFLECTION = Accessors.UtilAccessor.getAccessor().getReflection();
    }

    @Override
    public <R> R inject(Class<?> param) {
        if(!isVaultAccessible(param)) throw new DisallowedAccessPackageException(NAME, param.getPackageName());
        Constructor<?> constructor = REFLECTION.findConstructor(param);
        if(constructor != null) return (R) loadBeanFromConstructor(param, constructor);
        List<Field> fields = REFLECTION.findFields(param);
        return (R) loadBeanFromField(param, fields);
    }

    private boolean isVaultAccessible(Class<?> cls){
        if(INJECT_ACCESSES.length == 0) return true;
        String src = cls.getPackageName();
        for(String vaultAccess : INJECT_ACCESSES){
            if(isContainSelectAllRegex(vaultAccess)){
                String substring = vaultAccess.substring(0, vaultAccess.length()-2);
                if(substring.length() > src.length()) continue;
                if(src.contains(substring)) return true;
            }
            if(vaultAccess.equals(src)) return true;
        }
        return false;
    }

    private boolean isContainSelectAllRegex(String pkg){
        return pkg.startsWith(".*", pkg.length()-2);
    }

    private Object loadBeanFromConstructor(Class<?> cls, Constructor<?> constructor) {
        Parameter[] parameters = constructor.getParameters();
        List<Object> instancedParameters = new ArrayList<>();
        for(Parameter parameter : parameters){
            Inject inject = parameter.getDeclaredAnnotation(Inject.class);
            if(inject == null || inject.value().equals("")) throw new IllegalStateException("Constructor injection must specify \"@Inject(value = \"?\")\"");
            String value = inject.value();
            if(!BEANS.containsKey(value)) throw new NoDefinedInternalBeanException(value);
            if(!BEANS.get(value).isInjectable(cls)) throw new DisallowedAccessPackageException(value, cls.getPackageName());
            instancedParameters.add(BEANS.get(value).load());
        }
        try{
            constructor.setAccessible(true);
            return constructor.newInstance(instancedParameters.toArray());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object loadBeanFromField(Class<?> cls, List<Field> fields){
        Object bean = null;
        try{
            Constructor<?> constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);
            bean = constructor.newInstance();
        }catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Can not find default constructor of \"" + cls.getSimpleName() + "\"");
        }
        for(Field field : fields){
            field.setAccessible(true);
            String value = field.getName();
            if(!field.getAnnotation(Inject.class).value().equals("")) value = field.getAnnotation(Inject.class).value();
            if(!BEANS.containsKey(value)) throw new NoDefinedInternalBeanException(value);
            if(!BEANS.get(value).isInjectable(cls)) throw new DisallowedAccessPackageException(value, cls.getPackageName());
            Object instance = BEANS.get(value).load();
            try{
                field.set(bean, instance);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Can not access field value \"" + value + "\"");
            }
        }
        return bean;
    }

    public static Vault.Builder<ClassVault> getBuilder(){
        return new Builder<>() {
            @Override
            public ClassVault build() {
                return new ClassVault(this);
            }
        };
    }

}
