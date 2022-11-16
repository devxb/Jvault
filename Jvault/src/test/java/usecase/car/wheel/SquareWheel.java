package usecase.car.wheel;

import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;
import usecase.car.Wheel;

@InternalBean(
        name = "squareWheel",
        type = Type.SINGLETON,
        accessClasses = {"usecase.car.Car", "usecase.car.InstancedCar"},
        accessPackages = {"usecase.car.factory.*", "usecase.car.wheel"}
)
public final class SquareWheel implements Wheel {

    SquareWheel(){}

    @Override
    public int accel(){ return 1; }

}