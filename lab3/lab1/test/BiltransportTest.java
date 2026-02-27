import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BiltransportTest {
    Truck transport = new Biltransport();
    /* Testar om alla värden instansieras rätt
     */
    @Test
    public void startingValues() {
        assertEquals(2, transport.getNrDoors());
        assertEquals(Color.PINK, transport.getColor());
        assertEquals(500, transport.getEnginePower(), 0.10);
        assertEquals("Biltransport", transport.getModelName());
    }

    /* Testar om rampen höjs/sänks
     */
    @Test
    public void rampTest() {
        // Startar motorn och höjer rampen som test
        transport.startEngine();
        transport.lowerRamp(1);
        assertTrue(transport.isRampActive);

        // Höjer den igen, borde fortfarande vara "1" för active.
        transport.lowerRamp(1);
        assertTrue(transport.isRampActive);

        // Sänker man den med "0", så är det som att sänka den hela
        // vägen ner. Alltså isRampActive = false;
        transport.raiseRamp(1);
        assertFalse(transport.isRampActive);
    }

    @Test
    public void loadAndUnloadCarTest() {
        Vehicles volvo = new Volvo240();
        Biltransport transport = new Biltransport();

        transport.startEngine();
        transport.lowerRamp(1);
        transport.loadCar(volvo);
        assertEquals(1, transport.carsLoaded.size());

        transport.unloadCar();
        assertEquals(0, transport.carsLoaded.size());
    }

    @Test
    public void loadTransTest() {
        Truck transport1 = new Biltransport();
        Biltransport transport2 = new Biltransport();

        transport2.startEngine();
        transport2.lowerRamp(1);
        transport2.loadCar(transport1);
        assertEquals(0, transport2.carsLoaded.size());
    }

    /* Testar ifall motorn startas
     */
    @Test
    public void engineTest() {
        transport.startEngine();
        assertTrue(transport.getEngineRunning());

        transport.stopEngine();
        assertFalse(transport.getEngineRunning());
    }

    /* Testar om bilen rör på sig och uppdaterar x och y koordinater.
     */
    @Test
    public void moveTest() {
        double xBefore = transport.getXCoord();
        double yBefore = transport.getYCoord();
        transport.startEngine();
        transport.gas(1);
        assertTrue(yBefore < transport.getYCoord());
        transport.turnRight();
        transport.move();
        assertTrue(xBefore < transport.getXCoord());
    }

    @Test
    public void turnLeftTest() {
        int[] posList = new int[4];
        String oldPos = transport.getPosition(transport.positions);

        transport.startEngine();
        for(int i = 0; i < posList.length; i++ ) {
            assertEquals(oldPos, transport.getPosition(transport.positions));
            transport.turnLeft();
            oldPos = transport.getPosition(transport.positions);
        }
    }

    @Test
    public void turnRightTest() {
        int[] posList = new int[4];
        String oldPos = transport.getPosition(transport.positions);

        transport.startEngine();
        for(int i = 0; i < posList.length; i++ ) {
            assertEquals(oldPos, transport.getPosition(transport.positions));
            transport.turnRight();
            oldPos = transport.getPosition(transport.positions);
        }
    }

    @Test
    public void speedTest() {
        double oldSpeed = transport.getCurrentSpeed();

        transport.incrementSpeed(50);
        assertTrue(oldSpeed < transport.getCurrentSpeed());

        transport.decrementSpeed(1000);
        assertEquals(0, transport.getCurrentSpeed(), 0.1);
    }

    @Test
    public void interfaceTest() {
        Movable testCar = new Biltransport();
        testCar.move();
        testCar.turnLeft();

        assertTrue(true);
    }

    @Test
    public void gasTest() {
        double oldSpeed = transport.getCurrentSpeed();

        transport.startEngine();
        transport.gas(1.0);
        assertTrue(oldSpeed < transport.getCurrentSpeed());
    }

    @Test
    public void brakeTest() {
        double oldSpeed;

        transport.startEngine();
        transport.gas(1);
        oldSpeed = transport.getCurrentSpeed();
        transport.brake(0.5);

        assertTrue(oldSpeed > transport.getCurrentSpeed());
    }

    @Test
    public void speedLimitTest() {
        transport.currentSpeed = 500;
        transport.gas(1);
        assertEquals(500, transport.getCurrentSpeed(), 0.001);
    }
}

