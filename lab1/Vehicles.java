import java.awt.*;
/*  Nu har vi skapat en konstruktor för bilar generellt och genom att vi har använt oss av metoder och
        abstrakta klasser så är det väldigt enkelt att lägga till gemensamma egenskaper som bilarna skulle kunna ha nu.

        Vi har nu skapat klassen "Fordon" och genom subtyppolymorfism kan vi nu skapa subklasser utav "fordon"

        Vi skyddar även folk från att bara skapa "new Vehicles" då klassen är abstract. På detta vis kan vi nu alltid
        skapa enkla objekt av bilar.
     */

public abstract class Vehicles {
    /* Allting protected då "Vehicles" är superklassen och alla andra fordon använder sig av dessa variabler
        för att instansieras.
     */
    protected int nrDoors; // Number of doors on the car
    protected double enginePower; // Engine power of the car
    protected double currentSpeed; // The current speed of the car
    protected Color color; // Color of the car
    protected String modelName; // The car model name
    protected double xCoord; // x-koordinaterna på bilen
    protected double yCoord; // y-koordinaterna på bilen
    public String[] positions = {"North", "East", "South", "West"};
    public int currentPos = 0;

    /* Public på alla metoder då de skall ärvas (protected hade funkat då med) men om vi har en klass som inte ärver
        från Vehicles vill vi fortfarande kunna kalla på dessa metoder.
     */
    public String getPosition(String[] positions) {
        for(int i = 0; i < positions.length - 1; i++) {
            if(i == currentPos) {
                currentPos = i;                
            }
        } return positions[currentPos];
    }

    public int getNrDoors(){
        return nrDoors;
    }

    public double getEnginePower(){
        return enginePower;
    }

    public double getCurrentSpeed(){
        return currentSpeed;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color clr){
        color = clr;
    }

    public String getModelName() {
        return modelName;
    }

    /* Protected på start- och stopEngine då vi skall inte kunna starta bilarna om de inte
        själva klassen som vill starta dem.
    */
    protected void startEngine(){
        currentSpeed = 0.1;
    }

    protected void stopEngine(){
        currentSpeed = 0;
    }

    public double getYCoord() {
        return yCoord;
    }

    public double getXCoord() {
        return xCoord;
    }

    /*  Eftersom "speedFactor" beter sig annorlunda beroende på bil lägger vi till en abstract speedFactor då
        följer vi arvs-reglerna och låter den specifika bilen bestämma med en override. Vi använder även
        protected här eftersom speedFactor är bara en mall som sedan bestäms mer specifikt utav klasserna då
        detta är något de inte har gemensamt.
     */
    protected abstract double speedFactor();

    /*  Public på dessa med då om vi vill ändra hastighet skall det komma från subklassen själv och inget annat.
        Te.x om vi lägger till fåglar skall inte ett fågelobjekt kunna sänga och höja hastigheten av en bil.
    */


    protected void incrementSpeed(double amount){
        currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount, enginePower);
    }

    protected void decrementSpeed(double amount){
        currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount,0);
    }
}
