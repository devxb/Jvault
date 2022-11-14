package usecase.car;

import org.jvault.factory.TypeVaultFactory;
import org.jvault.vault.ClassVault;
import org.jvault.vault.VaultType;

public final class VehicleFactory {

    private static final VehicleFactory INSTANCE = new VehicleFactory();
    private final ClassVault CLASS_VAULT;

    {
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();
        CLASS_VAULT = vaultFactory.get("CAR_VAULT", VaultType.CLASS);
    }

    public static <R extends Vehicle> R getVehicle(Class<R> type){
        return INSTANCE.CLASS_VAULT.inject(type);
    }

}
