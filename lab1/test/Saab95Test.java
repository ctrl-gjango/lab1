import org.junit.Test;
import java.awt.*;

import static org.junit.Assert.*;

public class Saab95Test {
    Vehicles saab = new Saab95();
    @Test
    public void startingValues() {
        assertEquals(2, saab.getNrDoors());
        assertEquals(Color.RED, saab.getColor());
        assertEquals(125, saab.getEnginePower(), 0.10);
        assertEquals("Saab95", saab.getModelName());
    }

     @Test
     public void turboTest() {
        Saab95 saab95 = new Saab95();
        saab95.setTurboOn();
        assertTrue(saab95.getTurboStatus());

        saab95.setTurboOff();
        assertFalse(saab95.getTurboStatus());
     }

    @Test
    public void engineTest() {
        saab.startEngine();
        assertTrue(saab.getCurrentSpeed() > 0);

        saab.stopEngine();
        assertEquals(0, saab.getCurrentSpeed(), 0.1);
    }

    @Test
    public void moveTest() {
        double xBefore = saab.getXCoord();
        double yBefore = saab.getYCoord();
        saab.startEngine();
        saab.move();
        assertTrue(yBefore < saab.getYCoord());
        saab.turnRight();
        saab.move();
        assertTrue(xBefore < saab.getXCoord());
    }

    @Test
    public void turnLeftTest() {
        int[] posList = new int[4];
        String oldPos = saab.getPosition(saab.positions);

        saab.startEngine();
        for(int i = 0; i < posList.length; i++ ) {
            assertEquals(oldPos, saab.getPosition(saab.positions));
            saab.turnLeft();
            oldPos = saab.getPosition(saab.positions);
        }
    }

    @Test
    public void turnRightTest() {
        int[] posList = new int[4];
        String oldPos = saab.getPosition(saab.positions);

        saab.startEngine();
        for(int i = 0; i < posList.length; i++ ) {
            assertEquals(oldPos, saab.getPosition(saab.positions));
            saab.turnRight();
            oldPos = saab.getPosition(saab.positions);
        }
    }

    @Test
    public void speedTest() {
        double oldSpeed = saab.getCurrentSpeed();

        saab.incrementSpeed(50);
        assertTrue(oldSpeed < saab.getCurrentSpeed());

        saab.decrementSpeed(1000);
        assertEquals(0, saab.getCurrentSpeed(), 0.1);
    }

    @Test
    public void interFaceTest() {
        Movable testCar = new Saab95();
        testCar.move();
        testCar.turnLeft();

        assertTrue(true);
    }

    @Test
    public void gasTest() {
        double oldSpeed = saab.getCurrentSpeed();

        saab.startEngine();
        saab.gas(1.0);
        assertTrue(oldSpeed < saab.getCurrentSpeed());
    }

    @Test
    public void brakeTest() {
        double oldSpeed;

        saab.startEngine();
        saab.gas(1);
        oldSpeed = saab.getCurrentSpeed();
        saab.brake(0.5);

        assertTrue(oldSpeed > saab.getCurrentSpeed());
    }

    @Test
    public void speedLimitTest() {
        saab.currentSpeed = 130;
        assertEquals(125, saab.currentSpeed, 0.001);
    }
}

