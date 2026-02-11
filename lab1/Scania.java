import java.awt.*;

public class Scania extends Vehicles implements Movable {
    private final static double v40Engine = 1.5;
    private double rampAngle;


    public Scania() {
        nrDoors = 2;
        color = Color.PINK;
        enginePower = 300;
        rampAngle = 0;
        modelName = "Scania";
        stopEngine();
    }

    @Override
    public double speedFactor() {
        return enginePower * 0.03 * v40Engine;
    }

    /* En Override på gas-metoden då vi har restriktioner på när vi får gasa och inte.
     */
    @Override
    public void gas(double amount) {
        if(rampAngle != 0) {
            System.out.println("Du kan inte köra medans rampen är uppfälld!");
            return;
        }

        if (amount < 0.00 || amount > 1.00) {
            throw new IllegalArgumentException("Värdet måste vara mellan 0.00 -> 1.00");
        }
        incrementSpeed(amount);
        move();
    }

    public void raiseRamp(double amount) {
        if(!engineRunning) {
            System.out.println("Motorn måste vara på för att använda rampen!");
            return;
        }

        if(currentSpeed != 0) {
            System.out.println("Trucken måste stå still för att använda rampen!");
            return;
        }

        rampAngle += amount;

        if(rampAngle > 70) {
            System.out.println("Rampens vinkel kan ej överstiga 70°");
            rampAngle = 70;
        }
    }

    public void lowerRamp(double amount) {
        if(!engineRunning) {
            System.out.println("Motorn måste vara på för att använda rampen!");
            return;
        }

        if(currentSpeed != 0) {
            System.out.println("Trucken måste stå still för att använda rampen!");
            return;
        }

        rampAngle -= amount;

        if(rampAngle < 0) {
            System.out.println("Rampens vinkel kan ej understiga 0°");
            rampAngle = 0;
        }
    }

    public double getRampAngle() {
        return rampAngle;
    }

    public static void main(String[] args) {
        Scania scania = new Scania();

        scania.startEngine();
        System.out.println(scania.getCurrentSpeed());

        //scania.gas(0.5);
        scania.raiseRamp(80);
        //scania.lowerRamp(20);
        scania.gas(1);
        System.out.println(scania.getCurrentSpeed());
    }
}
