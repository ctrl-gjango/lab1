import java.awt.*;

public class Scania extends Truck implements Movable {
    private final static double v40Engine = 1.5;

    public Scania() {
        nrDoors = 2;
        color = Color.PINK;
        enginePower = 300;
        isRampActive = false;
        rampAngle = 0;
        modelName = "Scania";
        stopEngine();
    }

    @Override
    public double speedFactor() {
        return enginePower * 0.03 * v40Engine;
    }
}
