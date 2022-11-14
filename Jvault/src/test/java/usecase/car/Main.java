package usecase.car;

import org.jvault.annotation.BeanWire;
import org.jvault.annotation.VaultConfiguration;
import org.jvault.factory.TypeVaultFactory;
import org.jvault.factory.buildinfo.AnnotationVaultFactoryBuildInfo;
import org.jvault.vault.ClassVault;
import org.jvault.vault.VaultType;
import usecase.car.wheel.RoundWheel;
import usecase.car.wheel.SquareWheel;

public class Main {

    public static void main(String[] args){
        AnnotationVaultFactoryBuildInfo buildInfo = new AnnotationVaultFactoryBuildInfo(AnnotationConfig.class);
        TypeVaultFactory typeVaultFactory = TypeVaultFactory.getInstance();
        typeVaultFactory.get(buildInfo, VaultType.CLASS);

        Car car = VehicleFactory.getVehicle(Car.class);
        InstancedCar instancedCar = VehicleFactory.getVehicle(InstancedCar.class);
        System.out.println(car.meter() + ", " + instancedCar.meter());
    }

    @VaultConfiguration(name = "CAR_VAULT", vaultAccessPackages = "usecase.car.*")
    private static class AnnotationConfig{

        @BeanWire
        private RoundWheel roundWheel;

        @BeanWire
        private SquareWheel squareWheel;

    }

}
