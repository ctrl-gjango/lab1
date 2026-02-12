public abstract class Truck extends Vehicles {
    protected boolean isRampActive;
    protected double rampAngle;
    protected double maxAng;
    protected double minAng;

    public void gas(double amount) {
        if(isRampActive) {
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

        if(rampAngle <= minAng) {
            rampAngle = minAng;
            isRampActive = false;
        }
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
        isRampActive = true;
        if(rampAngle > maxAng ) {
            rampAngle = maxAng;
        }
    }

    public double getRampAngle() {
        return rampAngle;
    }
}