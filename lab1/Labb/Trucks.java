
    public abstract class Trucks extends Vehicles
{
    protected boolean rampDown = false;

    @Override
    protected double speedFactor() {
        return 0;
    }

    public boolean isRampDown()
    {
        return rampDown;
    }
    public boolean setRampUp()
    {
        return rampDown = false;
    }
    public boolean setRampDown()
    {
        return rampDown = true;
    }

    @Override
    protected void startEngine(){
        if(isRampDown())
        {
            throw new IllegalArgumentException("Trucks ramp is lowered");
        }
        currentSpeed = 0.1;
    }
    public void gas(double amount) {
        if(isRampDown())
        {
            throw new IllegalArgumentException("Trucks ramp is lowered");
        }
        if(amount < 0 && amount>1)
        {
            throw new IllegalArgumentException("Only value between 0 - 1.0");
        }
        incrementSpeed(amount);
        move();
    }
}