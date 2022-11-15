package org.jvault.vault;

import org.jvault.annotation.Inject;
import org.jvault.annotation.InternalBean;
import org.jvault.bean.Bean;
import org.jvault.bean.Type;
import org.jvault.exceptions.DisallowedAccessException;
import org.jvault.exceptions.NoDefinedInternalBeanException;
import org.jvault.factory.extensible.Vault;
import org.jvault.metadata.API;
import org.jvault.metadata.ThreadSafe;
import org.jvault.util.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Receive the Class type as a parameter and returns the corresponding instance.<br>
 * <br>
 * ClassVault injects beans into parameters, using the @Inject annotation mapped to a field or constructor of the parameter's class.<br>
 * Examples.
 * <br>
 * <br>
 * 1. Field Inject <br>
 * <pre>
 *    {@code
 *        public class Foo { @Inject private SomeBean bean; }
 *    }
 * </pre>
 * If a Foo.class is passed to a method of ClassVault,<br>
 * First, ClassVault creates an instance of Foo.class using default constructor and injects the bean into the field marked with @Inject. <br>
 * After that, by the above process, the instance of Foo into which the bean is injected is returned.<br>
 * <br>
 * 2. Constructor Inject <br>
 * <pre>
 *     {@code
 *      public class Foo{
 *
 *          private SomeBean bean;
 *
 *          public Foo(){}
 *
 *·         @Inject
 *          private Foo(@Inject("bean") SomeBean someBean){
 *              this.bean = someBean;
 *          }
 *      }
 *     }
 * </pre>
 * If a Foo.class is passed to a method of ClassVault,<br>
 * First, ClassVault creates an instance of Foo.class using the @Inject mapped constructor. <br>
 * In this process, the bean is passed as a parameter of the @Inject marked constructor,<br>
 * and finally the instance of Foo.class into which the bean is injected is returned. <br>
 * If a class has more than one @Inject marked constructor, a DuplicateConstructorException is thrown. <br>
 * If there is a parameter that is not mapped to @Inject in the parameter of the constructor mapped to @Inject, an IllegalStateException is thrown. <br>
 * <br>
 * ClassVault can only be instantiated in the org.jvault.* package,<br>
 * and actually you can't force instantiation of Vault without using Reflection.<br>
 * To obtain ClassVault, see the {@link org.jvault.factory.TypeVaultFactory} class.
 *
 * @author devxb
 * @see org.jvault.factory.TypeVaultFactory
 * @see Vault
 * @see org.jvault.annotation.Inject
 * @see org.jvault.annotation.InternalBean
 * @since 0.1
 */
@API
@ThreadSafe
public final class ClassVault extends AbstractVault<Class<?>> {
    private final ConcurrentMap<Class<?>, Bean> CACHED_BEANS;
    private final Reflection REFLECTION;

    ClassVault(Vault.Builder<ClassVault> builder) {
        super(builder);
        CACHED_BEANS = new ConcurrentHashMap<>();
        REFLECTION = Accessors.UtilAccessor.getAccessor().getReflection();
    }

    /**
     * Same method as inject(injectTarget, returnType). <br>
     * The inject(injectTarget, returnType) method receives two parameters to prevent unchecked cast,<br>
     * but this method enables the above function to be performed with one parameter.<br>
     * Therefore, it is more convenient and recommended to use this method.
     *
     * @param type The class-type of target to be injected beans, Vault will inject beans into this parameter.
     * @return Returns an instance of the type received param.
     * @param <R> the type of return instance.
     */
    public <R> R inject(Class<R> type){
        return inject(type, type);
    }

    /**
     * Method of inject beans into class-type-parameter and return instance of class-type-parameter.
     *
     * If parameter was mapped to @InternalBean(type = Type.SINGLETON) and included in the scope of Vault's bean scan, <br>
     * ClassVault caches this parameter and returns the cached instance from the next request.
     *
     * @param <R> the type of return instance.
     * @param injectTarget The class-type of target to be injected beans, Vault will inject beans into this parameter.
     * @param returnType The class-type to be returned. This must be a type that matches the param parameter.
     * @return Returns an instance of the injectTarget received param.
     * @throws DisallowedAccessException Occurs when the package in param is a package that does not have access to Vault,
     *                                   * or the Beans to be injected into param cannot be injected into the package in Param.
     */
    @Override
    public <R> R inject(Class<?> injectTarget, Class<R> returnType) {
        throwIfParamDoesNotAccessible(injectTarget);

        if(CACHED_BEANS.containsKey(injectTarget)) return CACHED_BEANS.get(injectTarget).loadIfInjectable(injectTarget);
        cacheBean(injectTarget);
        if(CACHED_BEANS.containsKey(injectTarget)) return CACHED_BEANS.get(injectTarget).loadIfInjectable(injectTarget);

        Constructor<?> constructor = REFLECTION.findConstructor(injectTarget);
        if (constructor != null) return returnType.cast(loadBeanFromConstructor(injectTarget, constructor));
        List<Field> fields = REFLECTION.findFields(injectTarget);
        return returnType.cast(loadBeanFromField(injectTarget, fields));
    }

    @SuppressWarnings("all")
    private void cacheBean(Class<?> cls){
        if(isDoseNotNeedCache(cls)) return;
        String cachedBeanName = getBeanName(cls);
        synchronized (cls) {
            if (BEANS.containsKey(cachedBeanName)) CACHED_BEANS.put(cls, BEANS.get(cachedBeanName));
        }
    }

    private boolean isDoseNotNeedCache(Class<?> cls){
        InternalBean internalBean = cls.getDeclaredAnnotation(InternalBean.class);
        return internalBean == null || internalBean.type() != Type.SINGLETON;
    }

    private String getBeanName(Class<?> cls) {
        String name = convertToBeanName(cls.getSimpleName());
        InternalBean internalBean = cls.getDeclaredAnnotation(InternalBean.class);
        if (internalBean.name().equals("")) return name;
        return internalBean.name();
    }

    private String convertToBeanName(String name) {
        return name.substring(0, 1).toLowerCase() + name.subSequence(1, name.length());
    }

    private Object loadBeanFromConstructor(Class<?> cls, Constructor<?> constructor) {
        List<Parameter> parameters = REFLECTION.getAnnotatedConstructorParameters(constructor);
        List<Object> instancedParameters = new ArrayList<>();
        for (Parameter parameter : parameters) {
            Inject inject = parameter.getDeclaredAnnotation(Inject.class);
            String value = inject.value();

            throwIfCanNotFindDefinedBean(value);

            instancedParameters.add(BEANS.get(value).loadIfInjectable(cls));
        }
        return invokeConstructor(cls.getSimpleName(), constructor, instancedParameters.toArray());
    }

    private Object invokeConstructor(String name, Constructor<?> constructor, Object[] parameters){
        try{
            constructor.setAccessible(true);
            return constructor.newInstance(parameters);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Fail to invoke constructor of \"" + name + "\"");
        }
    }

    private Object loadBeanFromField(Class<?> cls, List<Field> fields) {
        Object bean = loadBeanFromDefaultConstructor(cls);
        for (Field field : fields) {
            field.setAccessible(true);
            String value = getBeanNameByField(field);

            throwIfCanNotFindDefinedBean(value);

            Object instance = BEANS.get(value).loadIfInjectable(cls);
            injectBeanToField(field, bean, instance);
        }
        return bean;
    }

    private Object loadBeanFromDefaultConstructor(Class<?> cls) {
        try {
            Constructor<?> constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new IllegalStateException("Can not find default constructor of \"" + cls.getSimpleName() + "\"");
        }
    }

    private void throwIfCanNotFindDefinedBean(String beanName) {
        if (!BEANS.containsKey(beanName)) throw new NoDefinedInternalBeanException(beanName);
    }

    private void injectBeanToField(Field field, Object bean, Object instance) {
        try {
            field.set(bean, instance);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Can not access field value \"" + getBeanNameByField(field) + "\"");
        }
    }

    private String getBeanNameByField(Field field) {
        String beanName = field.getName();
        if (!field.getAnnotation(Inject.class).value().equals(""))
            beanName = field.getAnnotation(Inject.class).value();
        return beanName;
    }

}
