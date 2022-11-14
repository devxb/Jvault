package org.jvault.vault;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvault.factory.TypeVaultFactory;
import org.jvault.factory.buildinfo.AbstractVaultFactoryBuildInfo;
import org.jvault.struct.classvaultthreadsafe.ClassVaultThreadSafe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClassVaultThreadSafeTest {

    @Test
    public void VAULT_THREAD_SAFE_TEST(){
        // given
        ExecutorService executor = Executors.newCachedThreadPool();
        Collection<Callable<Void>> runners = getRunner(100);

        // then
        try {
            executor.invokeAll(runners);
        } catch (InterruptedException ignored){}
    }

    private List<Callable<Void>> getRunner(int runnerCount){
        List<Callable<Void>> runners = new ArrayList<>();
        for(int i = 1; i <= runnerCount+1; i++)
            runners.add(getThreadTestUnit(i + ""));
        Collections.shuffle(runners);
        return runners;
    }

    private Callable<Void> getThreadTestUnit(String name){
        return ()-> {
            TypeVaultFactory factory = TypeVaultFactory.getInstance();
            factory.get(getAbstractBuildInfo(name), VaultType.CLASS);
            ExecutorService executor = Executors.newCachedThreadPool();
            List<Callable<ClassVaultThreadSafe>> injectors = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                injectors.add(
                    () -> {
                        ClassVault classVault = factory.get(name, VaultType.CLASS);
                        return classVault.inject(ClassVaultThreadSafe.class);
                    }
                );
            }
            List<Future<ClassVaultThreadSafe>> futures = executor.invokeAll(injectors);
            for (int j = 1; j < futures.size(); j++) {
                Assertions.assertTrue(futures.get(j - 1) == futures.get(j));
            }
            return null;
        };
    }

    private AbstractVaultFactoryBuildInfo getAbstractBuildInfo(String name){
        return new AbstractVaultFactoryBuildInfo() {
            @Override
            public String getVaultName() {
                return name;
            }

            @Override
            protected String[] getPackagesImpl() {
                return new String[0];
            }

            @Override
            protected String[] getExcludePackagesImpl() {
                return new String[0];
            }

            @Override
            protected String[] getClassesImpl() {
                return new String[]{"org.jvault.struct.classvaultthreadsafe.ClassVaultThreadSafe"};
            }
        };
    }

}
