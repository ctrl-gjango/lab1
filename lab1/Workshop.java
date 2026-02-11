import java.util.ArrayList;
import java.util.List;

public class Workshop<V extends Vehicles> {
    // Använder generella List<> då vi inte vill vara låsta till en Linked/ArrayList
    private List<V> cars;
    private int maxCars;

    public Workshop(int maxCars) {
        this.maxCars = maxCars;
        this.cars = new ArrayList<>();
    }

    public void takeCar(V car) {
        if(cars.size() > maxCars) {
            System.out.println("Verkstaden är full!");
            return;
        }
        // Om verkstaden inte är full lägger vi till parametern i listan. För car är utav typen Vehicles.
        cars.add(car);
        System.out.println("Bilen: " + car.getModelName() + ", är nu i verkstaden.");
    }

    public V returnCar() {
        if(cars.isEmpty()) {
            return null;
        }
        return (cars.removeLast());
    }
}
