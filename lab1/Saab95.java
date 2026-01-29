import java.awt.*;

public class Saab95 extends Vehicles implements Movable {
    private boolean turboOn; //private då den endast ändras via metoderna i klassen.

    public Saab95(){
        nrDoors = 2;
        color = Color.red;
        enginePower = 125;
        turboOn = false;
        modelName = "Saab95";
        stopEngine();
    }

    @Override
    public void move() {
        double coordInc = (currentSpeed/10);

        if(positions[currentPos].equals("North")) {
            yCoord += coordInc;
        }
        if(positions[currentPos].equals("East")) {
            xCoord += coordInc;
        }
        if(positions[currentPos].equals("South")) {
            yCoord -= coordInc;
        }
        if(positions[currentPos].equals("West")) {
            xCoord -= coordInc;
        }
    }

    @Override
    public void turnRight() {
        currentPos = (currentPos + 1) % 4;
    }

    @Override
    public void turnLeft() {
        currentPos = (currentPos - 1);
        if(Math.signum(currentPos) == -1) {
            currentPos = 3;
        }
    }

    public void direction() {
        System.out.println(positions[currentPos]);
    }

    public void setTurboOn(){
	    turboOn = true;
    }

    public void setTurboOff(){
	    turboOn = false;
    }

    public boolean getTurboStatus() {
        return turboOn;
    }

    @Override
    public double speedFactor(){
        double turbo = 1;
        if(turboOn) turbo = 1.3;
        return enginePower * 0.01 * turbo;
    }

    public static void main(String[] args) {
        Vehicles saab = new Saab95();

        saab.startEngine();
        System.out.println(saab.getYCoord());
        System.out.println(saab.getCurrentSpeed());
        saab.gas(1);
        saab.gas(1);
        saab.gas(1);
        saab.gas(1);
        saab.gas(1);
        saab.gas(1);
        saab.gas(1);
        saab.gas(1);
        saab.gas(1);
        saab.gas(1);
        saab.gas(1);

        System.out.println(saab.getCurrentSpeed());

    }
}
