import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VehicleFactory {
    private static final Random rand = new Random();

    //Skapar en lista av bilar som får tre bilar i sig.
    public List<Vehicles> createCars () {
        List<Vehicles> cars = new ArrayList<>();

        Volvo240 volvo = new Volvo240();
        volvo.setPosition(0, 0);
        cars.add(volvo);

        Saab95 saab = new Saab95();
        saab.setPosition(0, 200);
        cars.add(saab);

        Scania scania = new Scania();
        scania.setPosition(0, 400);
        cars.add(scania);

        return cars;
    }

    //Skapar en random bil beroende på numret som genereras och sedan skapas bilden på rätt plats på GUI:t beroende
    //på hur många bilar som redan är på skärmen.
    public static Vehicles createRandomCar(int screenPosition) {
        int positionOnScreen = screenPosition % 4;
        int positionY = positionOnScreen * 200;

        int randomCar = rand.nextInt(4);
        Vehicles randCar;

        if(randomCar == 0) {
            randCar = new Volvo240();
        } else if(randomCar == 1) {
            randCar = new Saab95();
        } else if(randomCar == 2) {
            randCar = new Scania();
        } else {
            randCar = new Biltransport();
        }
        randCar.setPosition(0, positionY);
        return randCar;
    }
}
