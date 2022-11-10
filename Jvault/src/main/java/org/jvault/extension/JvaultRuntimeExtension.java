package org.jvault.extension;

import org.jvault.factory.buildinfo.extensible.BeanReaderExtensiblePoint;
import org.jvault.metadata.API;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A point to change the internal behavior of the Jvault library. <br>
 * If the client implements the extensiblepoint of Jvault library and passes it to this class,
 * <br> the behavior of the library implementations is changed. <br>
 * <br>
 * JvaultRuntimeExtension uses {@link Optional} to mean that no extension is given. Therefore, be careful not to pass type parameter {@link Optional}.
 *
 * @author devxb
 * @since 0.1
 */
@API
public final class JvaultRuntimeExtension {

    private final static JvaultRuntimeExtension INSTANCE = new JvaultRuntimeExtension();

    private final static ConcurrentMap<Class<?>, Object> EXPANDED;

    static {
        EXPANDED = new ConcurrentHashMap<>();
        EXPANDED.put(BeanReaderExtensiblePoint.class, Optional.empty());
    }

    private JvaultRuntimeExtension() {
    }

    /**
     * Receive extensiblepoint implementation of Jvault and change library behavior.<br>
     * <br>
     * If a non-extensible type parameter is passed, an {@link IllegalArgumentException} is thrown. <br>
     *
     * @param instance the instance of ExtensiblePoint
     * @param type     the class type of ExtensiblePoint
     * @param <T>      the real type of ExtensiblePoint
     * @author devxb
     * @since 0.1
     */
    public static <T> void extend(T instance, Class<T> type) {
        INSTANCE.throwIfIsNotExtensible(type);
        EXPANDED.replace(type, instance);
    }

    /**
     * Receive extensiblepoint implementation of Jvault and reset to the default implementation of the Jvault library. <br>
     * <br>
     * Ignored if there is no ExtensiblePoint implementation corresponding to the passed parameter.
     *
     * @param type the class type of ExtensiblePoint
     * @param <T>  the real type of ExtensiblePoint
     * @author devxb
     * @since 0.1
     */
    public static <T> void reset(Class<T> type) {
        try {
            EXPANDED.replace(type, Optional.empty());
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * Reset all registered extensiblepoint implementations to the Jvault default implementations of extensiblepoint.
     *
     * @author devxb
     * @since 0.1
     */
    public static void resetAll() {
        EXPANDED.replaceAll((k, v) -> Optional.empty());
    }

    static JvaultRuntimeExtension getInstance() {
        return INSTANCE;
    }

    /**
     * Internal API. <br>
     * Jvault libraries accessible to JvaultRuntimeExtension are used to obtain extension implementations.
     *
     * @param cls the class type of ExtensiblePoint
     * @param <T> the real type of ExtensiblePoint
     * @return T Implement of ExtensiblePoint.
     */
    public <T> T getExtension(Class<T> cls) {
        throwIfIsNotExtensible(cls);
        if (EXPANDED.get(cls).getClass() == Optional.class) return null;
        return cls.cast(EXPANDED.get(cls));
    }

    private <T> void throwIfIsNotExtensible(Class<T> type) {
        if (EXPANDED.containsKey(type)) return;
        throw new IllegalArgumentException("Cannot be expanded cause there is no extension point that matched \"" + type + "\"");
    }

}
