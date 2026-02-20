import java.awt.*;
import java.util.*;

public class VehiclesTransport extends Trucks
{
    private final double magic = 2;
    private int maxspaces = 2 + (int)(Math.random()*5);
    private LinkedList<Vehicles> loadedvehicles = new LinkedList<>();

    public VehiclesTransport()
    {
        nrDoors = 2;
        color = Color.magenta;
        enginePower = 300;
        modelName = "VolvoFH";
        stopEngine();
    }


    protected boolean isClose(Vehicles f)
    {
        return Math.abs(getxcords() - f.getxcords()) <= 5 &&
                Math.abs(getycords() - f.getycords()) <= 5;
    }
    protected boolean loadable(Vehicles f)
    {
        if (!(f instanceof VehiclesTransport))
        {
            return true;
        }
        throw new IllegalArgumentException("Transporttrucks can't care eachother"); // see if the vehicles is a truck
    }

    protected void load(Vehicles f)
    {
        if(!isRampDown())
        {
            throw new IllegalArgumentException("Ramp not lowered");
        }
        if(!loadable(f))
        {
            throw new IllegalArgumentException("Car isn't loadable");
        }
        if(!isClose(f))
        {
            throw new IllegalArgumentException("Car is to far away");
        }
        if(loadedvehicles.size() > maxspaces)
        {
            throw new IllegalArgumentException("Too many cars for the truck");
        }
        loadedvehicles.add(f);
        f.xvalue = this.getxcords();
        f.yvalue = this.getycords();
    }
    protected Vehicles unload(){
        if(loadedvehicles.isEmpty())
        {
            throw new IllegalArgumentException("Transport vehicle is empty");
        }
        Vehicles f = loadedvehicles.removeLast();

        switch (this.dir)
        {
            case NORTH:
                f.yvalue--;
            break;
            case SOUTH:
            f.yvalue++;
                break;
            case EAST:
            f.xvalue--;
                break;
            case WEST:
            f.xvalue++;
                break;
        }
        return f;
    }

    @Override
    protected double speedFactor() {return enginePower * 0.01 * magic;}


}
