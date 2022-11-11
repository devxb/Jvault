package usecase.car.wheel;

import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(
        name = "squareWheel",
        type = Type.SINGLETON,
        accessClasses = {"car.Car"},
        accessPackages = {"car.factory.*", "car.wheel"}
)
public final class SquareWheel implements Wheel{

    SquareWheel(){}

    @Override
    public int accel(){ return 1; }

}