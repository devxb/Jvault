package org.jvault.factory.buildinfo;

import org.jvault.beanreader.BeanReader;

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

}
