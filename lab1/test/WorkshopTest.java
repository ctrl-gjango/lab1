import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class WorkshopTest {
    Workshop<Vehicles> generalWork = new Workshop<>(5);
    Workshop<Volvo240> volvoWork = new Workshop<>(5);
    Workshop<Saab95> saabWork = new Workshop<>(5);

    Vehicles volvo = new Volvo240();
    Vehicles saab = new Saab95();
    Truck scania = new Scania();
    Truck transport = new Biltransport();

    @Test
    public void generalWorkshopTakeAndReturn() {
        generalWork.takeCar(volvo);
        generalWork.takeCar(saab);
        generalWork.takeCar(scania);
        generalWork.takeCar(transport);

        assertEquals(4, generalWork.cars.size());
        assertEquals(volvo, generalWork.returnCar());
        assertEquals(saab, generalWork.returnCar());
    }

    @Test
    public void staticWorkshop() {
        Volvo240 volvo2401 = new Volvo240();
        Volvo240 volvo2402 = new Volvo240();
        volvoWork.takeCar(volvo2401);
        volvoWork.takeCar(volvo2402);
        assertEquals(volvo2401, volvoWork.returnCar());
    }
}
