package org.jvault.beanloader;

import org.jvault.beans.BeanBuilderFactory;
import org.jvault.metadata.InternalAPI;
import org.jvault.util.Reflection;

@InternalAPI
public final class Accessors {

    public abstract static class UtilAccessor{

        private static UtilAccessor accessor;
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

    public abstract static class BeansAccessor{

        private static BeansAccessor accessor;
        private final static Class<?> init = init();

        private static Class<?> init(){
            try{
                return Class.forName("org.jvault.beans.BeansAccessorImpl");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public static void registerAccessor(BeansAccessor accessor){
            if(BeansAccessor.accessor != null) throw new IllegalStateException();
            BeansAccessor.accessor = accessor;
        }

        static BeansAccessor getAccessor(){
            return accessor;
        }

        protected abstract BeanBuilderFactory getBeanBuilderFactory();

    }

}
