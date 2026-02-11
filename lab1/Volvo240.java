import java.awt.*;

public class Volvo240 extends Vehicles implements Movable {

    private final static double trimFactor = 1.25; // private då trimFactor endast används i metoderna.
    
    public Volvo240(){
        nrDoors = 4;
        color = Color.BLACK;
        enginePower = 100;
        modelName = "Volvo240";
        stopEngine();
    }

    @Override
    public double speedFactor() {
        return enginePower * 0.01 * trimFactor;
    }
}
