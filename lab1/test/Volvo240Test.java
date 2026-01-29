import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Volvo240Test {
    Vehicles volvo = new Volvo240();

    @Test
    public void startingValues() {
        assertEquals(4, volvo.getNrDoors());
        assertEquals(Color.BLACK, volvo.getColor());
        assertEquals(100.00, volvo.getEnginePower(), 0.10);
        assertEquals("Volvo240", volvo.getModelName());
    }

    @Test
    public void engineTest() {
        volvo.startEngine();
        assertTrue(volvo.getCurrentSpeed() > 0);

        volvo.stopEngine();
        assertEquals(0, volvo.getCurrentSpeed(), 0.1);
    }

    @Test
    public void moveTest() {
        double xBefore = volvo.getXCoord();
        double yBefore = volvo.getYCoord();
        volvo.startEngine();
        volvo.move();
        assertTrue(yBefore < volvo.getYCoord());
        volvo.turnRight();
        volvo.move();
        assertTrue(xBefore < volvo.getXCoord());
    }

    @Test
    public void turnLeftTest() {
        int[] posList = new int[4];
        String oldPos = volvo.getPosition(volvo.positions);

        volvo.startEngine();
        for(int i = 0; i < posList.length; i++ ) {
            assertEquals(oldPos, volvo.getPosition(volvo.positions));
            volvo.turnLeft();
            oldPos = volvo.getPosition(volvo.positions);
        }
    }

    @Test
    public void turnRightTest() {
        int[] posList = new int[4];
        String oldPos = volvo.getPosition(volvo.positions);

        volvo.startEngine();
        for(int i = 0; i < posList.length; i++ ) {
            assertEquals(oldPos, volvo.getPosition(volvo.positions));
            volvo.turnRight();
            oldPos = volvo.getPosition(volvo.positions);
        }
    }

    @Test
    public void speedTest() {
        double oldSpeed = volvo.getCurrentSpeed();

        volvo.incrementSpeed(50);
        assertTrue(oldSpeed < volvo.getCurrentSpeed());

        volvo.decrementSpeed(1000);
        assertEquals(0, volvo.getCurrentSpeed(), 0.1);
    }

    @Test
    public void interFaceTest() {
        Movable testCar = new Saab95();
        testCar.move();
        testCar.turnLeft();

        assertTrue(true);
    }

    @Test
    public void gasBrake() {
        double oldSpeed = volvo.getCurrentSpeed();

        volvo.startEngine();
        volvo.gas(1.0);
        assertTrue(oldSpeed < volvo.getCurrentSpeed());
        oldSpeed = volvo.getCurrentSpeed();

        volvo.brake(0.5);
        assertTrue(oldSpeed > volvo.getCurrentSpeed());
    }

    @Test
    public void speedLimitTest() {
        volvo.currentSpeed = 2000;
        volvo.gas(1);
        assertEquals(100, volvo.currentSpeed, 0.001);
    }


}
