import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class Saab95Test {
    Vehicles saab = new Saab95();

    /* Testar om alla värden instansieras rätt
     */
    @Test
    public void startingValues() {
        assertEquals(2, saab.getNrDoors());
        assertEquals(Color.RED, saab.getColor());
        assertEquals(125, saab.getEnginePower(), 0.10);
        assertEquals("Saab95", saab.getModelName());
    }

    /* Aktiverar turbo på saab och testar ifall den aktiveras som den skall
     */
     @Test
     public void turboTest() {
        Saab95 saab95 = new Saab95();
        saab95.setTurboOn();
        assertTrue(saab95.getTurboStatus());

        saab95.setTurboOff();
        assertFalse(saab95.getTurboStatus());
     }

    /* Testar ifall motorn startas
     */
    @Test
    public void engineTest() {
        saab.startEngine();
        assertTrue(saab.getEngineRunning());

        saab.stopEngine();
        assertEquals(0, saab.getCurrentSpeed(), 0.1);
        assertFalse(saab.getEngineRunning());
    }

    /* Testar om bilen rör på sig och uppdaterar x och y koordinater.
     */
    @Test
    public void moveTest() {
        Saab95 saab = new Saab95();
        double xBefore = saab.getXCoord();
        double yBefore = saab.getYCoord();
        saab.startEngine();
        saab.gas(1);
        assertTrue(yBefore < saab.getYCoord());
        saab.turnRight();
        saab.gas(1);
        assertTrue(xBefore < saab.getXCoord());
    }

    @Test
    public void turnLeftTest() {
        Saab95 saab95 = new Saab95();
        int[] posList = new int[4];
        String oldPos = saab.getPosition(saab.positions);

        saab95.startEngine();
        for(int i = 0; i < posList.length; i++ ) {
            assertEquals(oldPos, saab.getPosition(saab.positions));
            saab95.turnLeft();
            oldPos = saab.getPosition(saab.positions);
        }
    }

    @Test
    public void turnRightTest() {
        Saab95 saab = new Saab95();
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
        Saab95 saab = new Saab95();
        double oldSpeed = saab.getCurrentSpeed();

        saab.startEngine();
        saab.gas(1.0);
        assertTrue(oldSpeed < saab.getCurrentSpeed());
    }

    @Test
    public void brakeTest() {
        Saab95 saab = new Saab95();
        double oldSpeed;

        saab.startEngine();
        saab.gas(1);
        oldSpeed = saab.getCurrentSpeed();
        saab.brake(0.5);

        assertTrue(oldSpeed > saab.getCurrentSpeed());
    }

    @Test
    public void speedLimitTest() {
        Saab95 saab = new Saab95();

        saab.currentSpeed = 130;
        saab.gas(1);
        assertEquals(125, saab.getCurrentSpeed(), 0.001);
    }
}

