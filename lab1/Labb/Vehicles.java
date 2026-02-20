import javax.lang.model.element.ModuleElement;
import javax.management.InvalidAttributeValueException;
import javax.swing.text.Position;
import java.awt.*;

public abstract class Vehicles implements Movable 
{
    /* Allting protected då "Vehicles" är superklassen och alla andra fordon använder sig av dessa variabler
        för att instansieras.
     */
    protected int nrDoors; // Number of doors on the car
    protected double enginePower; // Engine power of the car
    protected double currentSpeed; // The current speed of the car
    protected Color color; // Color of the car
    protected String modelName; // The car model name
    protected double yvalue = 0; // the cars starting vertical postion value
    protected double xvalue = 0; // the cars starting straight postion value
    protected Direction dir = Direction.NORTH; // The starting direction for all the vehicles
    /*  Nu har vi skapat en konstruktor för bilar generellt och genom att vi har använt oss av metoder och
        abstrakta klasser så är det väldigt enkelt att lägga till gemensamma egenskaper som bilarna skulle kunna ha nu.

        Vi har nu skapat klassen "Fordon" och genom subtyppolymorfism kan vi nu skapa subklasser utav "fordon"

        Vi skyddar även folk från att bara skapa "new Vehicles" då klassen är abstract. På detta vis kan vi nu alltid
        skapa enkla objekt av bilar.
     */

    /* Public på alla metoder då de skall ärvas (protected hade funkat då med) men om vi har en klass som inte ärver
        från Vehicles vill vi fortfarande kunna kalla på dessa metoder.
     */
    public int getNrDoors(){
        return nrDoors;
    }

    public double getEnginePower(){
        return enginePower;
    }

    public double getCurrentSpeed()
    {
        if(currentSpeed<0 && currentSpeed>enginePower)
        {
            System.out.println("ERROR: Speed out of bounds");
        }
        return currentSpeed;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color clr){
        color = clr;
    }

    /* Protected på start- och stopEngine då vi skall inte kunna starta bilarna om de inte
        själva klassen som vill starta dem.
    */

    protected void startEngine()
    {
        currentSpeed = 0.1;
    }
    public void stopEngine(){
        currentSpeed = 0;
    }

    /*  Eftersom "speedFactor" beter sig annorlunda beroende på bil lägger vi till en abstract speedFactor då
        följer vi arvs-reglerna och låter den specifika bilen bestämma med en override. Vi använder även
        protected här eftersom speedFactor är bara en mall som sedan bestäms mer specifikt utav klasserna då
        detta är något de inte har gemensamt.
     */
    protected abstract double speedFactor();

    /*  Public på dessa med då om vi vill ändra hastighet skall det komma från subklassen själv och inget annat.
        Te.x om vi lägger till fåglar skall inte ett fågelobjekt kunna sänka och höja hastigheten av en bil.
    */
    protected void incrementSpeed(double amount){
        currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount, enginePower);
    }

    protected void decrementSpeed(double amount){
        currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount,0);
    }


    public void gas(double amount) {
        if(amount >= 0 && amount<=1)
        {
            incrementSpeed(amount);
            move();
        }
        else
        {
            throw new IllegalArgumentException("Only value between 0 - 1.0");
        }
    }

    public void brake(double amount)
    {
        if(amount >= 0 && amount<=1)
        {
            decrementSpeed(amount);
            move();
        }
        else
        {
            throw new IllegalArgumentException("Only value between 0 - 1.0");
        }
    }
    public double getycords()
    {
        return yvalue;
    }

    public double getxcords()
    {
        return xvalue;
    }

    protected enum Direction
    {
        NORTH, SOUTH, EAST, WEST;
    }

    @Override
    public void move()
    {
        switch (dir)
        {
            case NORTH: yvalue += yvalue + currentSpeed; break;
            case SOUTH: yvalue -= yvalue - currentSpeed; break;
            case EAST: xvalue += xvalue + currentSpeed; break;
            case WEST: xvalue -= xvalue - currentSpeed; break;
        }

    }

    @Override
    public void turnLeft()
    {
        switch(dir)
        {
            case NORTH: dir = Direction.WEST; break;
            case SOUTH: dir = Direction.EAST; break;
            case EAST: dir = Direction.NORTH; break;
            case WEST: dir = Direction.SOUTH; break;
        }
    }

    @Override
    public void turnRight()
    {
        switch (dir)
        {
            case NORTH: dir =  Direction.EAST; break;
            case SOUTH: dir =  Direction.WEST; break;
            case EAST: dir =   Direction.SOUTH; break;
            case WEST: dir =  Direction.NORTH; break;
        }

    }


}
