package org.jvault.beans;

import org.jvault.metadata.InternalAPI;
import org.jvault.util.Reflection;

@InternalAPI
public final class Accessors {

    public abstract static class UtilAccessor{

        private static UtilAccessor accessor;
        private final static Class<?> init = init();

        private static Class<?> init(){
            try{
                return Class.forName("org.jvault.util.UtilAccessorImplOnBean");
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

}
