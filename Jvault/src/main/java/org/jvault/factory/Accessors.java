package org.jvault.factory;

import org.jvault.factory.extensible.BeanLoader;
import org.jvault.factory.extensible.BuildStorage;
import org.jvault.factory.extensible.Vault;
import org.jvault.metadata.InternalAPI;
import org.jvault.vault.VaultType;

@InternalAPI
@SuppressWarnings("unused")
public final class Accessors {

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

    public abstract static class VaultAccessor{
        private static VaultAccessor accessor;
        private final static Class<?> init = init();

        private static java.lang.Class<?> init(){
            try{
                return java.lang.Class.forName("org.jvault.vault.VaultAccessorImpl");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public static void registerAccessor(VaultAccessor accessor){
            if(VaultAccessor.accessor != null) throw new IllegalStateException();
            VaultAccessor.accessor = accessor;
        }

        static VaultAccessor getAccessor(){
            return accessor;
        }

        protected abstract <S extends Vault<?>> Vault.Builder<S> getBuilder(VaultType vaultType);

    }

    public abstract static class StorageAccessor{

        private static StorageAccessor accessor;

        private final static Class<?> init = init();

        private static java.lang.Class<?> init(){
            try{
                return java.lang.Class.forName("org.jvault.factory.storage.StorageAccessorImpl");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public static void registerAccessor(StorageAccessor accessor){
            if(StorageAccessor.accessor != null) throw new IllegalStateException();
            StorageAccessor.accessor = accessor;
        }

        static StorageAccessor getAccessor(){
            return accessor;
        }

        protected abstract BuildStorage getBuildStorage();

    }

}
