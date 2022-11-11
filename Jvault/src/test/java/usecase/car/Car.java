package usecase.car;

import org.jvault.annotation.Inject;
import usecase.car.wheel.Wheel;

public final class Car implements Vehicle{

    private final Wheel WHEEL;

    @Inject
    private Car(@Inject("squareWheel") Wheel wheel){
        WHEEL = wheel;
    }

    @Override
    public String meter() {
        return "current meter : " + WHEEL.accel();
    }
}