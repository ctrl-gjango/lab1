import java.util.ArrayList;
import java.util.List;

public class Workshop<V extends Vehicles> {
    // Använder generella List<> då vi inte vill vara låsta till en Linked/ArrayList
    protected List<V> vehicles;
    private final int maxVehicles;

    public Workshop(int maxCars) {
        this.maxVehicles = maxCars;
        this.vehicles = new ArrayList<>();
    }

    public void takeCar(V car) {
        if(vehicles.size() >= maxVehicles) {
            System.out.println("Verkstaden är full!");
            return;
        }
        // Om verkstaden inte är full lägger vi till parametern i listan. För car är utav typen Vehicles.
        vehicles.add(car);
        System.out.println("Bilen: " + car.getModelName() + ", är nu i verkstaden.");
    }

    public V returnCar() {
        if(vehicles.isEmpty()) {
            return null;
        }
        return (vehicles.removeFirst());
    }

    public static void main(String[] args) {
        Workshop<Volvo240> volvoshop = new Workshop<Volvo240>(4);
    }
}
