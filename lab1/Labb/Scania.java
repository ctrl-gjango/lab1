import java.awt.*;

public class Scania extends Trucks
{
    private final double x16engine = 1.45;
    protected int leanamount;


    public Scania()
    {
        nrDoors = 2;
        color = Color.lightGray;
        enginePower = 300;
        modelName = "ScaniaR500";
        stopEngine();
    }
    protected void lean(int x)
    {
        if(!(currentSpeed == 0))
        {
            throw new IllegalArgumentException("Truck needs to be fully stopped to initiate lean");
        }

        if(x > 70 && x < 0)
        {
            throw new IllegalArgumentException("acceptable lean is only 0-70");

        }
        this.leanamount = x;
        setRampDown();
    }

    @Override
    protected double speedFactor() {return enginePower * 0.01 * x16engine;}
}
