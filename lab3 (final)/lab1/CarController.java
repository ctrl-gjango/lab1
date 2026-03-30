import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/*
* This class represents the Controller part in the MVC pattern.
* It's responsibilities is to listen to the View and responds in a appropriate manner by
* modifying the model state and the updating the view.
 */


//Våran main-klass som laddar programmet
public class CarController {
    // member fields:
    // Här har vi laddat in CarModel som model, innehåller våra bilar och logik
    // Även CarView som view, innehåller vårat GUI och ritningar.
    // Timer som skall driva simuleringen
    private final int delay = 50;
    private final CarModel model;
    private final CarView view;
    private final Timer timer;


    //Våran konstruktor som skapar referenser till model, view och timern.
    public CarController(CarModel model, CarView view) {
        this.model = model;
        this.view = view;
        this.timer = new Timer(delay, new TimerListener());
    }

    /** Startpunkten för vårat program
     * Vi har en factory method som skapar bilar och stoppar in de i en lista.
     * Sedan skickar vi bilarna till våran CarModel, och våran CarView får modellen så den kan skapa ett GUI.
     * Därefter skapar vi en controller som tar para model och view, nu är MVC kopplade.
     * Vi kopplar view till controller så att knapptrycken fungerar som det skall.
     * I min designplan pratade vi även om olika designmönster och där var observer något viktigt som behövdes
     * skapas. Jag lägger "View" som en observer, alltså när något händer i modellen notifieras View.
     */
    public static void main(String[] args) {
        VehicleFactory createVehicle = new VehicleFactory();
        List<Vehicles> cars = createVehicle.createCars();
        CarModel model = new CarModel(cars);
        CarView view = new CarView("Ultra Realistic CarSimulator 4.0", model);
        CarController controller = new CarController(model, view);

        view.setController(controller);
        model.addObserver(view);

        controller.start();
    }

    //startar timer
    public void start() {
        timer.start();
    }

    /** Vi har en delay på 50ms, alltså blir actionPerformed triggad varje 50ms, som i sin tur triggar .moveCars(...)
     * Här så sköter moveCars, flyttning av bilar, kollision, byter riktning sedan pingar view. Det fina är nu att
     * moveCars(...) har en notifyObserver() som sedan kallar på update() i view, som använder repaint() för att
     * uppdatera vårat GUI och bilar rör på sig.
     */
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent b) {
            model.moveCars(view.getDrawPanelWidth(),
                    view.getDrawPanelHeight(),
                    view.getWorkshopWidth(),
                    view.getWorkshopHeight());
        }
    }

    //Relevanta metoder för GUI i drawPanel som får knapptrycken att fungera.
    //Skapar en random-bil som läggs till på skrämen beroende på vilken position den förrgående var på.
    void addRandomCarToScreen() {
        Vehicles car = VehicleFactory.createRandomCar(model.getCarCountOnScreen());
        model.addRandomCarToScreen(car);
    }

    void removeLastCarOnScreen() {
        model.removeLastCarOnScreen();
    }

    void startAllCars() {
        model.startAllCars();
    }

    void stopAllCars() {
        model.stopAllCars();
    }

    void gas(int amount) {
        model.gasAll((double)amount/100);
    }

    void brake(int amount) {
        model.brakeAll((double)amount/100);
    }

    void setTurboOn() {
        model.turboOnAllSaabs();
    }

    void setTurboOff() {
        model.turboOffAllSaabs();
    }

    void setLowerRamp() {
        model.lowerAllScaniaRamps();
    }

    void setRaiseRamp() {
        model.raiseAllScaniaRamps();
    }
}
