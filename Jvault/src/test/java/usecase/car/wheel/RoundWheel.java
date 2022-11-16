package usecase.car.wheel;

import org.jvault.annotation.InternalBean;
import org.jvault.bean.Type;
import usecase.car.Wheel;

@InternalBean(
        name = "roundWheel",
        type = Type.SINGLETON,
        accessClasses = {"usecase.car.Car", "usecase.car.InstancedCar"},
        accessPackages = {"usecase.car.factory.*", "usecase.car.wheel"}
)
public final class RoundWheel implements Wheel {

    RoundWheel(){}

    @Override
    public int accel(){ return 10; }

}