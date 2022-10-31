package org.jvault.factory;

import org.jvault.beanloader.BeanLoader;
import org.jvault.beanreader.BeanReader;

public final class Accessors {

    public abstract static class BeanReaderAccessor{

        private static BeanReaderAccessor accessor;
        private final static Class<?> init = init();

        private static Class<?> init(){
            try{
                return Class.forName("org.jvault.beanreader.BeanReaderAccessorImpl");
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

    public abstract static class BeanLoaderAccessor{
        private static BeanLoaderAccessor accessor;
        private final static Class<?> init = init();

        private static Class<?> init(){
            try{
                return Class.forName("org.jvault.beanloader.BeanLoaderAccessorImpl");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public static void registerAccessor(BeanLoaderAccessor accessor){
            if(BeanLoaderAccessor.accessor != null) throw new IllegalStateException();
            BeanLoaderAccessor.accessor = accessor;
        }

        static BeanLoaderAccessor getAccessor(){
            return accessor;
        }

        protected abstract BeanLoader getBeanLoader();

    }

}
