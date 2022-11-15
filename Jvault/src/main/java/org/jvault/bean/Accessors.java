package org.jvault.bean;

import org.jvault.metadata.InternalAPI;

@InternalAPI
@SuppressWarnings("unused")
public final class Accessors {

    public abstract static class BeanCompositionAccessor{

        private static BeanCompositionAccessor accessor;
        private final static Class<?> init = init();

        private static Class<?> init(){
            try{
                return Class.forName("org.jvault.bean.composition.BeanCompositionAccessorImpl");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public static void registerAccessor(BeanCompositionAccessor accessor){
            if(BeanCompositionAccessor.accessor != null) throw new IllegalStateException();
            BeanCompositionAccessor.accessor = accessor;
        }

        static BeanCompositionAccessor getAccessor(){
            return accessor;
        }

        protected abstract Bean.Builder<? extends Bean> getBuilder(Class<? extends Bean> beanType);

    }

}
