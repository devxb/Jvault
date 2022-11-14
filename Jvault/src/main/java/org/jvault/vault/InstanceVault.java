package org.jvault.vault;

import org.jvault.annotation.Inject;
import org.jvault.beans.Bean;
import org.jvault.exceptions.DisallowedAccessException;
import org.jvault.exceptions.NoDefinedInternalBeanException;
import org.jvault.metadata.API;
import org.jvault.util.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

/**
 * Receive instance of class as a parameter and inject bean to parameter.
 * <br>
 * <br>
 * InstanceVault can only be instantiated in the org.jvault.* package,<br>
 * and actually you can't force instantiation of Vault without using Reflection.<br>
 * This means that you should not instantiate ClassVault using Reflection.<br>
 * To obtain InstanceVault, see the {@link org.jvault.factory.TypeVaultFactory} class.
 *
 * @author devxb
 * @since 0.1
 */
@API
public final class InstanceVault extends AbstractVault<Object>{

    private final Map<String, Bean> BEANS;
    private final Reflection REFLECTION;

    InstanceVault(Vault.Builder<InstanceVault> builder) {
        super(builder);
        BEANS = builder.BEANS;
        REFLECTION = Accessors.UtilAccessor.getAccessor().getReflection();
    }

    /**
     * Same method as inject(injectTarget, returnType). <br>
     * The inject(injectTarget, returnType) method receives two parameters to prevent unchecked cast,<br>
     * but this method enables the above function to be performed with one parameter.<br>
     * Therefore, it is more convenient and recommended to use this method.
     *
     * @param injectTarget The instance of class to be injected beans, Vault will inject beans into this parameter.
     * @return Returns an instance of the type received param.
     * @param <R> the type of return instance.
     */
    @SuppressWarnings("unchecked")
    public <R> R inject(R injectTarget){
        return inject(injectTarget, (Class<R>) injectTarget.getClass());
    }


    /**
     * Method of inject beans into parameter and return same identified instance of parameter (that may be injected bean).<br>
     * <br>
     * Unlike ClassVault, InstanceVault does not cache parameters that marked @InternalBean(type = Type.SINGLETON).<br>
     * This means that the instance returned for each request has the same address value as the instance passed by the user each time.<br>
     *
     * @param <R> the type of return instance.
     * @param injectTarget The instance of target class to be injected beans, Vault will inject beans into this parameter.
     * @param returnType The class-type to be returned. This must be a type that matches the param parameter.
     * @return Returns a same identified instance (that may be injected bean) of the injectTarget received param.
     * @throws DisallowedAccessException Occurs when the package in param is a package that does not have access to Vault,
     *                                   * or the Beans to be injected into param cannot be injected into the package in Param.
     */
    @Override
    public <R> R inject(Object injectTarget, Class<R> returnType) {
        R typeOfInjectTarget = returnType.cast(injectTarget);
        throwIfParamDoesNotAccessible(typeOfInjectTarget.getClass());

        Constructor<R> constructor = REFLECTION.findConstructor(returnType);
        if(isConstructorInjectable(constructor)) return injectBeanToConstructor(typeOfInjectTarget, constructor);
        return injectBeanToTargetField(typeOfInjectTarget);
    }

    private boolean isConstructorInjectable(Constructor<?> constructor){
        return constructor != null;
    }

    private <R> R injectBeanToConstructor(R injectTarget, Constructor<R> constructor){
        Class<?> injectTargetClass = injectTarget.getClass();
        List<Parameter> parameters = REFLECTION.getAnnotatedConstructorParameters(constructor);
        for(Parameter parameter : parameters){
            Inject inject = parameter.getDeclaredAnnotation(Inject.class);
            String beanName = inject.value();

            throwIfCanNotFindDefinedBean(beanName);
            throwIfBeanDoesNotAccessInject(beanName, injectTargetClass);

            Field field = getInjectTargetField(injectTargetClass, beanName);
            field.setAccessible(true);
            injectBeanToField(injectTarget, field, beanName);
        }
        return injectTarget;
    }

    private Field getInjectTargetField(Class<?> injectTargetClass, String name){
        try {
            return injectTargetClass.getDeclaredField(name);
        } catch (NoSuchFieldException e){
            throw new IllegalStateException("Failed to find a field that matches the constructor's parameter bean name \"" + name + "\"");
        }
    }

    private <R> R injectBeanToTargetField(R injectTarget){
        List<Field> fields = REFLECTION.findFields(injectTarget.getClass());
        Class<?> injectTargetClass = injectTarget.getClass();
        for(Field field : fields){
            field.setAccessible(true);
            String beanName = getBeanNameByField(field);

            throwIfCanNotFindDefinedBean(beanName);
            throwIfBeanDoesNotAccessInject(beanName, injectTargetClass);

            injectBeanToField(injectTarget, field, beanName);
        }
        return injectTarget;
    }

    private String getBeanNameByField(Field field) {
        String beanName = field.getName();
        if (!field.getAnnotation(Inject.class).value().equals(""))
            beanName = field.getAnnotation(Inject.class).value();
        return beanName;
    }

    private void throwIfCanNotFindDefinedBean(String beanName) {
        if (!BEANS.containsKey(beanName)) throw new NoDefinedInternalBeanException(beanName);
    }

    private void throwIfBeanDoesNotAccessInject(String beanName, Class<?> injectReceiver){
        if (!BEANS.get(beanName).isInjectable(injectReceiver))
            throw new DisallowedAccessException(beanName, injectReceiver.getPackage().getName());
    }

    private void injectBeanToField(Object injectTarget, Field field, String beanName) {
        try {
            field.set(injectTarget, BEANS.get(beanName).load());
        } catch (IllegalAccessException IAE) {
            throw new IllegalStateException("Can not access field value \"" + beanName + "\"");
        }
    }

}
