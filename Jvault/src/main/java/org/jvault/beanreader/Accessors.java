package org.jvault.beanreader;

import org.jvault.metadata.InternalAPI;
import org.jvault.util.PackageReader;

@InternalAPI
public final class Accessors {

    public abstract static class UtilAccessor{
        private static UtilAccessor accessor;
        private final static Class<?> init = init();

        private static Class<?> init(){
            try{
                return Class.forName("org.jvault.util.UtilAccessorImplOnReader");
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

        protected abstract PackageReader getPackageReader();
    }

}
