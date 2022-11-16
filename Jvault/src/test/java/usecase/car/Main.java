package usecase.car;

import org.jvault.factory.TypeVaultFactory;
import org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo;
import org.jvault.vault.ClassVault;
import org.jvault.vault.VaultType;
import usecase.car.jvaultconfig.AnnotationConfig;

public class Main {

    public static void main(String[] args){
        initJvault();

        caseOfClassVault();

        caseOfVehicleFactory();

        caseOfVehicleFactory();
    }

    private static void initJvault(){
        AnnotationVaultFactoryBuildInfo buildInfo = new AnnotationVaultFactoryBuildInfo(AnnotationConfig.class);
        TypeVaultFactory typeVaultFactory = TypeVaultFactory.getInstance();
        typeVaultFactory.get(buildInfo, VaultType.CLASS);
    }

    private static void caseOfVehicleFactory(){
        Car car = VehicleFactory.getVehicle(Car.class);
        InstancedCar instancedCar = VehicleFactory.getVehicle(InstancedCar.class);
        assert car.meter().equals("current meter : 1");
        assert instancedCar.meter().equals("default name101");
    }

    private static void caseOfClassVault(){
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();
        ClassVault classVault = vaultFactory.get("CAR_VAULT", VaultType.CLASS);
        Car car = classVault.inject(Car.class);
        assert car.meter().equals("current meter : 1");
    }

    private static void caseOfInstanceVault(){
        InstancedCar instancedCar = new InstancedCar("hello car");
        assert instancedCar.meter().equals("hello car101");
    }

}
