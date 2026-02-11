import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



public class ScaniaTest {
    Truck scania = new Scania();

    /* Testar om alla värden instansieras rätt
     */
    @Test
    public void startingValues() {
        assertEquals(2, scania.getNrDoors());
        assertEquals(Color.PINK, scania.getColor());
        assertEquals(300, scania.getEnginePower(), 0.10);
        assertEquals("Scania", scania.getModelName());
    }

    /* Testar om rampen höjs/sänks
     */
    @Test
    public void rampTest() {
        // Startar motorn och höjer rampen som test
        scania.startEngine();
        scania.raiseRamp(50);
        assertEquals(50, scania.getRampAngle(), 0.1);

        // Vinkel får ej överstiga 70 grader när man höjer rampen
        scania.raiseRamp(400);
        assertEquals(70, scania.getRampAngle(), 0.1);

        // Vinkel får ej understiga 0 grader när man sänker rampen
        scania.lowerRamp(400);
        assertEquals(0, scania.getRampAngle(), 0.1);
    }

    /* Testar ifall motorn startas
     */
    @Test
    public void engineTest() {
        scania.startEngine();
        assertTrue(scania.getEngineRunning());

        scania.stopEngine();
        assertFalse(scania.getEngineRunning());
    }

    /* Testar om bilen rör på sig och uppdaterar x och y koordinater.
     */
    @Test
    public void moveTest() {
        Scania scania = new Scania();
        double xBefore = scania.getXCoord();
        double yBefore = scania.getYCoord();
        scania.startEngine();
        scania.gas(1);
        assertTrue(yBefore < scania.getYCoord());
        scania.turnRight();
        scania.gas(1);
        assertTrue(xBefore < scania.getXCoord());
    }

    @Test
    public void turnLeftTest() {
        int[] posList = new int[4];
        String oldPos = scania.getPosition(scania.positions);

        scania.startEngine();
        for(int i = 0; i < posList.length; i++ ) {
            assertEquals(oldPos, scania.getPosition(scania.positions));
            scania.turnLeft();
            oldPos = scania.getPosition(scania.positions);
        }
    }

    @Test
    public void turnRightTest() {
        int[] posList = new int[4];
        String oldPos = scania.getPosition(scania.positions);

        scania.startEngine();
        for(int i = 0; i < posList.length; i++ ) {
            assertEquals(oldPos, scania.getPosition(scania.positions));
            scania.turnRight();
            oldPos = scania.getPosition(scania.positions);
        }
    }

    @Test
    public void speedTest() {
        double oldSpeed = scania.getCurrentSpeed();

        scania.incrementSpeed(50);
        assertTrue(oldSpeed < scania.getCurrentSpeed());

        scania.decrementSpeed(1000);
        assertEquals(0, scania.getCurrentSpeed(), 0.1);
    }

    @Test
    public void interfaceTest() {
        Movable testCar = new Scania();
        testCar.move();
        testCar.turnLeft();

        assertTrue(true);
    }

    @Test
    public void gasTest() {
        double oldSpeed = scania.getCurrentSpeed();

        scania.startEngine();
        scania.gas(1.0);
        assertTrue(oldSpeed < scania.getCurrentSpeed());
    }

    @Test
    public void rampAndGas() {
        Truck scania = new Scania();
        scania.startEngine();
        scania.raiseRamp(50);

        scania.gas(0.5);
        assertEquals(0, scania.getCurrentSpeed(), 0.001);
    }

    @Test
    public void brakeTest() {
        double oldSpeed;

        scania.startEngine();
        scania.gas(1);
        oldSpeed = scania.getCurrentSpeed();
        scania.brake(0.5);

        assertTrue(oldSpeed > scania.getCurrentSpeed());
    }

    @Test
    public void speedLimitTest() {
        scania.currentSpeed = 300;
        scania.gas(1);
        assertEquals(300, scania.getCurrentSpeed(), 0.001);
    }
}

