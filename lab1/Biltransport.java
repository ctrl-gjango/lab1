import java.awt.*;
import java.util.Deque;
import java.util.ArrayDeque;

public class Biltransport extends Vehicles implements Movable {
    protected boolean isRampDown;
    private Deque<Vehicles> carsLoaded = new ArrayDeque<>();
    private int maxCars = 4;



    public Biltransport() {
        nrDoors = 2;
        color = Color.PINK;
        enginePower = 500;
        isRampDown = false;
        modelName = "Scania";
        stopEngine();
    }

    @Override
    public double speedFactor() {
        return enginePower * 0.02;
    }

    @Override
    public void gas(double amount) {
        if(isRampDown) {
            System.out.println("Du kan inte köra medans rampen är nere!!");
            return;
        }

        if (amount < 0.00 || amount > 1.00) {
            throw new IllegalArgumentException("Värdet måste vara mellan 0.00 -> 1.00");
        }
        incrementSpeed(amount);
        move();
    }

    // Samma kod som Scania. Men denna använder sig av boolean så vi kan inte lägga dessa metoder som
    // en metod i supertypen "truck"

    public void lowerRamp(boolean lower) {
        if(!engineRunning) {
            System.out.println("Motorn måste vara igång för att använda rampen!");
            return;
        }

        if(currentSpeed != 0) {
            System.out.println("Trucken måste stå still för att använda rampen");
            return;
        }

        if(lower) {
            isRampDown = true;
        }
    }

    public void raiseRamp(boolean raise) {
        if(!engineRunning) {
            System.out.println("Motorn måste vara igång för att använda rampen!");
            return;
        }

        if(currentSpeed != 0) {
            System.out.println("Trucken måste stå still för att använda rampen");
            return;
        }

        if(raise) {
            isRampDown = false;
        }
    }

    public void loadCar(Vehicles car) {
        if(!isRampDown) {
            System.out.println("Rampen är ej nere! Går ej att lasta bilar!");
            return;
        }

        if(car instanceof Biltransport) {
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

        if(carsLoaded.size() >= 5) {
            System.out.println("Biltransporten är full!");
            return;
        }

        carsLoaded.push(car);
        car.setPosition(this.xCoord, this.yCoord);
        System.out.println("Bilen är lastad!");
    }

    public Vehicles unloadCar() {
        if(!isRampDown) {
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
}
