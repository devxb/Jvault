package org.jvault.factory.buildinfo;

import org.jvault.extension.JvaultRuntimeExtension;
import org.jvault.factory.buildinfo.extensible.BeanReader;
import org.jvault.metadata.InternalAPI;

@InternalAPI
@SuppressWarnings("unused")
public final class Accessors {

    public abstract static class BeanReaderAccessor{

        private static BeanReaderAccessor accessor;
        private final static Class<?> init = init();

        private static Class<?> init(){
            try{
                return Class.forName("org.jvault.beanreader.BeanReaderAccessorImplOnBuildInfoSide");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public static void registerAccessor(BeanReaderAccessor accessor){
            if(BeanReaderAccessor.accessor != null) throw new IllegalStateException();
            BeanReaderAccessor.accessor = accessor;
        }

        static BeanReaderAccessor getAccessor(){
            return accessor;
        }

        protected abstract BeanReader getBeanReader();

    }

    public abstract static class RuntimeExtensionAccessor{

        private static RuntimeExtensionAccessor accessor;
        private final static Class<?> init = init();

        private static Class<?> init(){
            try {
                return Class.forName("org.jvault.extension.RuntimeExtensionAccessorImpl");
            } catch (ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }

        public static void registerAccessor(RuntimeExtensionAccessor accessor){
            if(RuntimeExtensionAccessor.accessor != null) throw new IllegalStateException();
            RuntimeExtensionAccessor.accessor = accessor;
        }

        static RuntimeExtensionAccessor getAccessor(){
            return accessor;
        }

        protected abstract JvaultRuntimeExtension getRuntimeExtension();

    }

}
