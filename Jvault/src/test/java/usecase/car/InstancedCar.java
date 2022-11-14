package usecase.car;

import org.jvault.annotation.Inject;
import org.jvault.factory.TypeVaultFactory;
import org.jvault.vault.InstanceVault;
import org.jvault.vault.VaultType;
import usecase.car.wheel.Wheel;

public final class InstancedCar implements Vehicle{

    private String carName;
    @Inject
    private Wheel roundWheel;
    @Inject
    private Wheel squareWheel;

    private InstancedCar(){}

    public InstancedCar(String carName){
        this.carName = carName;
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();
        InstanceVault instanceVault = vaultFactory.get("CAR_VAULT", VaultType.INSTANCE);
        instanceVault.inject(this);
    }

    @Override
    public String meter() {
        return carName + roundWheel.accel() + squareWheel.accel();
    }
}
