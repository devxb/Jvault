package usecase.car.wheel;

import org.jvault.annotation.InternalBean;
import org.jvault.beans.Type;

@InternalBean(
        name = "roundWheel",
        type = Type.SINGLETON,
        accessClasses = {"car.Car"},
        accessPackages = {"car.factory.*", "car.wheel"}
)
public final class RoundWheel implements Wheel{

    RoundWheel(){}

    @Override
    public int accel(){ return 10; }

}