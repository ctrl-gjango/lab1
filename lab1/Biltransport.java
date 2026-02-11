import java.awt.*;
import java.util.Deque;
import java.util.ArrayDeque;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Biltransport extends Truck implements Movable {
    protected Deque<Vehicles> carsLoaded = new ArrayDeque<>();
    private final int maxCars = 4;
    private int retAng;

    public Biltransport() {
        nrDoors = 2;
        color = Color.PINK;
        enginePower = 500;
        isRampActive = false;
        modelName = "Biltransport";
        stopEngine();
    }

    @Override
    public double speedFactor() {
        return enginePower * 0.02;
    }

    @Override
    public void lowerRamp(double amount) {
        if(amount >= 1) {
            isRampActive = true;
        }
    }

    // Vi använder oss av doubles för att styra boolean status på våran ramp för
    // Biltransport då denna är annorlunda från Scania
    @Override
    public void raiseRamp(double amount) {
        if(amount >= 1) {
            isRampActive = false;
        }
    }

    @Override
    public double getRampAngle() {
        if(isRampActive) {
           retAng = 1;
        }

        if(!isRampActive) {
           retAng = 0;
        } return retAng;
    }

    public void loadCar(Vehicles car) {
        if (!isRampActive) {
            System.out.println("Rampen är ej nere! Går ej att lasta bilar!");
            return;
        }

        if (car instanceof Biltransport) {
            System.out.println("Du kan inte lasta en biltransport på en biltransport!!!");
            return;
        }

        /* Vi behöver avståndet mellan bilen och biltransporten vi kan ta x, y-koord, hitta delta sedan räkna
           hypotenusan för avståndsskillnaden. 1 l.e = 1 m
         */
        double dx = car.getXCoord() - this.getXCoord();
        double dy = car.getYCoord() - this.getYCoord();
        double dist = Math.sqrt(dx*dx + dy*dy);

        if(dist > 8) {
            System.out.println("Bilen är för långt bort för att bli lastad");
            return;
        }

        if(carsLoaded.size() > 4) {
            System.out.println("Biltransporten är full!");
            return;
        }

        carsLoaded.push(car);
        car.setPosition(this.xCoord, this.yCoord);
        System.out.println("Bilen är lastad!");
    }

    public Vehicles unloadCar() {
        if(!isRampActive) {
            System.out.println("Rampen är inte nere! Omöjligt att lasta av bilar!");
            return null;
        }

        if(carsLoaded.isEmpty()) {
            System.out.println("Biltransporten är tom, inget att lossa!");
            return null;
        }

        // Tar ut det första bilen på stacken
        Vehicles car = carsLoaded.pop();
        // Lämnar car ett "rimligt" värde från biltransporten :)
        car.xCoord = this.xCoord + 1;
        car.yCoord = this.yCoord + 1;

        System.out.println("Bilen är nu lossad!");
        return car;
    }
    public static void main(String[] args) {
        Biltransport biltransport = new Biltransport();

        biltransport.startEngine();
        // Sänker ner rampen så man kan lasta bilar.
        biltransport.lowerRamp(1);
        System.out.println(biltransport.isRampActive);

        // Höjer den igen, borde fortfarande vara "1" för active.
        biltransport.lowerRamp(1);
        System.out.println(biltransport.isRampActive);

        // Sänker man den med "1", så är det som att höja den hela vägen upp
        // Alltså isRampActive = false;
        biltransport.raiseRamp(1);
    }
}
