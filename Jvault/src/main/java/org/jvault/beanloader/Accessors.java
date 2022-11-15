package org.jvault.beanloader;

import org.jvault.bean.BeanBuilderFactory;
import org.jvault.metadata.InternalAPI;
import org.jvault.util.Reflection;

@InternalAPI
public final class Accessors {

    public abstract static class UtilAccessor{

        private static UtilAccessor accessor;
        @SuppressWarnings("unused")
        private final static Class<?> init = init();

        private static Class<?> init(){
            try{
                return Class.forName("org.jvault.util.UtilAccessorImplOnLoader");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public static void registerAccessor(UtilAccessor accessor){
            if(UtilAccessor.accessor != null) throw new IllegalStateException();
            UtilAccessor.accessor = accessor;
        }

        static UtilAccessor getAccessor(){
            return accessor;
        }

        protected abstract Reflection getReflection();

    }

    public abstract static class BeanAccessor{

        private static BeanAccessor accessor;
        @SuppressWarnings("unused")
        private final static Class<?> init = init();

        private static Class<?> init(){
            try{
                return Class.forName("org.jvault.bean.BeanAccessorImpl");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public static void registerAccessor(BeanAccessor accessor){
            if(BeanAccessor.accessor != null) throw new IllegalStateException();
            BeanAccessor.accessor = accessor;
        }

        static BeanAccessor getAccessor(){
            return accessor;
        }

        protected abstract BeanBuilderFactory getBeanBuilderFactory();

    }

}
