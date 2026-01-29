import java.awt.*;

public class Volvo240 extends Vehicles{

private final static double trimFactor = 1.25; // private då trimFactor endast används i metoderna.
    
    public Volvo240(){
        nrDoors = 4;
        color = Color.BLACK;
        enginePower = 100;
        modelName = "Volvo240";
        stopEngine();
    }

    public void move() {
        double coordInc = (currentSpeed/10);

        if(positions[currentPos].equals("North")) {
            yCoord += coordInc;
        }
        if(positions[currentPos].equals("East")) {
            xCoord += coordInc;
        }
        if(positions[currentPos].equals("South")) {
            yCoord += coordInc;
        }
        if(positions[currentPos].equals("West")) {
            xCoord += coordInc;
        }
    }

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

    @Override
    public double speedFactor() {
        return enginePower * 0.01 * trimFactor;
    }
}
