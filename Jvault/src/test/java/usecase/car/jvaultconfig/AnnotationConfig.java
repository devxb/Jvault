package usecase.car.jvaultconfig;

import org.jvault.annotation.BeanWire;
import org.jvault.annotation.VaultConfiguration;
import usecase.car.wheel.RoundWheel;
import usecase.car.wheel.SquareWheel;

@VaultConfiguration(name = "CAR_VAULT", vaultAccessPackages = "usecase.car.*")
public final class AnnotationConfig{

    @BeanWire
    private RoundWheel roundWheel;

    @BeanWire
    private SquareWheel squareWheel;

}