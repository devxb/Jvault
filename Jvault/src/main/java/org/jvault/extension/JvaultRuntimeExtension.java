package org.jvault.extension;

import org.jvault.factory.buildinfo.extensible.BeanReaderExtensiblePoint;
import org.jvault.metadata.API;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 *
 * @author devxb
 * @since 0.1
 */
@API
public final class JvaultRuntimeExtension{

    private final static JvaultRuntimeExtension INSTANCE = new JvaultRuntimeExtension();

    private final static ConcurrentMap<Class<?>, Object> EXPANDED;
    static {
        EXPANDED = new ConcurrentHashMap<>();
        EXPANDED.put(BeanReaderExtensiblePoint.class, Optional.empty());
    }

    /**
     *
     * @param instance the instance of ExtensiblePoint
     * @param type the class type of ExtensiblePoint
     * @param <T> the real type of ExtensiblePoint
     */
    public synchronized static <T> void extend(T instance, Class<T> type){
        INSTANCE.throwIfIsNotExtensible(type);
        EXPANDED.replace(type, instance);
    }

    public synchronized static void reset(){
        EXPANDED.replaceAll((k, v) -> Optional.empty());
    }

    public <T> T getExtension(Class<T> cls){
        try {
            throwIfIsNotExtensible(cls);
            if(EXPANDED.get(cls).getClass() == Optional.class) return null;
            return cls.cast(EXPANDED.get(cls));
        } catch(ClassCastException CCE){
            throw new IllegalArgumentException("Cannot be expanded cause there is no extension point that matched \"" + cls + "\"");
        }
    }

    private <T> void throwIfIsNotExtensible(Class<T> type){
        if(EXPANDED.containsKey(type)) return;
        throw new IllegalArgumentException("Cannot be expanded cause there is no extension point that matched \"" + type + "\"");
    }

    static JvaultRuntimeExtension getInstance(){
        return INSTANCE;
    }

    private JvaultRuntimeExtension(){}

}
