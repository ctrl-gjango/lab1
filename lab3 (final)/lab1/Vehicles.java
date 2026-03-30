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
    protected boolean engineRunning = false;
    protected double currentSpeed; // The current speed of the car
    protected Color color; // Color of the car
    protected String modelName; // The car model name
    protected double xCoord; // x-koordinaterna på bilen
    protected double yCoord; // y-koordinaterna på bilen
    public String[] positions = {"North", "East", "South", "West"};
    protected int currentPos = 1;

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

    public void setPosition(double x, double y) {
        this.xCoord = x;
        this.yCoord = y;
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

    public String toString() {
        return modelName;
    }

    /* Protected på start- och stopEngine då vi skall inte kunna starta bilarna om de inte
        själva klassen som vill starta dem.
    */
    protected void startEngine(){
        engineRunning = true;
    }

    protected void stopEngine(){
        engineRunning = false;
        currentSpeed = 0;
    }

    protected boolean getEngineRunning() {
        return engineRunning;
    }

    public double getYCoord() {
        return yCoord;
    }

    public double getXCoord() {
        return xCoord;
    }

    public String getDirection() {
        return positions[currentPos];
    }

    public void invertX() {
        if (currentPos == 1) currentPos = 3;      // East -> West
        else if (currentPos == 3) currentPos = 1; // West -> East
    }

    public void invertY() {
        if (currentPos == 0) currentPos = 2;      // North -> South
        else if (currentPos == 2) currentPos = 0; // South -> North
    }

    public void reverseDirection() {
        currentPos = (currentPos + 2) % 4;
    }
    /*  Eftersom "speedFactor" beter sig annorlunda beroende på bil lägger vi till en abstract speedFactor då
        följer vi arvs-reglerna och låter den specifika bilen bestämma med en override. Vi använder även
        protected här eftersom speedFactor är bara en mall som sedan bestäms mer specifikt utav klasserna då
        detta är något de inte har gemensamt.
     */
    protected abstract double speedFactor();

    public void move() {
        double coordInc = (currentSpeed/10);

        if(positions[currentPos].equals("North")) {
            yCoord -= coordInc;
        }
        if(positions[currentPos].equals("East")) {
            xCoord += coordInc;
        }
        if(positions[currentPos].equals("South")) {
            yCoord += coordInc;
        }
        if(positions[currentPos].equals("West")) {
            xCoord -= coordInc;
        }
    }

    /* Gasmetod som ser till att endast ta värden mellan 0 till 1. Och uppdaterar sedan koordinater då vi höjer
     *  hastigheten.
     */
    public void gas(double amount) {
        if(amount < 0.00 || amount > 1.00) {
            throw new IllegalArgumentException("Värdet måste vara mellan 0.00 -> 1.00");
        }
        if(engineRunning) {
            incrementSpeed(amount);
        }
    }

    /* Bromsmetod som bromsar bilen sedan uppdaterar koordinaterna.
     */
    public void brake(double amount) {
        if(amount < 0.00 || amount > 1.00) {
            throw new IllegalArgumentException("Värdet måste vara mellan 0.00 -> 1.00");
        }
        decrementSpeed(amount);
    }

    /* Svänger enligt väderstrecken vi har.
     */
    public void turnRight() {
        currentPos = (currentPos + 1) % 4;
    }

    public void turnLeft() {
        currentPos = (currentPos - 1);
        if(Math.signum(currentPos) == -1) {
            currentPos = 3;
        }
    }

    public void direction() {
        System.out.println(positions[currentPos]);
    }

    protected void incrementSpeed(double amount){
        currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount, enginePower);
    }

    protected void decrementSpeed(double amount){
        currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount,0);
    }
}
