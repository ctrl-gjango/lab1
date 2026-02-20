import java.awt.*;

public abstract class NonMovableVehicles extends Vehicles
{
    /* Allting protected då "Vehicles" är superklassen och alla andra fordon använder sig av dessa variabler
    för att instansieras.
 */
    protected int nrDoors; // Number of doors on the car
    protected double enginePower; // Engine power of the car
    protected final int currentSpeed = 0; // The current speed of the car
    protected Color color; // Color of the car
    protected String modelName; // The car model name
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

    public Color getColor(){
        return color;
    }

    public void setColor(Color clr){
        color = clr;
    }

    /* Protected på start- och stopEngine då vi skall inte kunna starta bilarna om de inte
        själva klassen som vill starta dem.
    */
}
