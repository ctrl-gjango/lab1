import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class CarModel {
    // Våra instansvariabler
    private final List<Vehicles> cars;
    private final List<ModelObserver> observers = new ArrayList<>();
    private final Workshop<Volvo240> volvoWorkshop = new Workshop<>(6);
    private static final int maxCarsOnScreen = 3;

    private final int driveDelay = 200;
    private final double[] individualCollision;

    /** Våran konstruktor. Vi gör en kopia av listan så att CarController och CarModel inte har samma lista.
     *  Då vi inte vill att ändringar utanför modellen skall påverka modellen direkt. Sedan skapar vi en array
     *  med samma längd som antalet bilar. Den håller reda på när kollision sker
     */
    public CarModel(List<Vehicles> cars) {
        this.cars = new ArrayList<>(cars);
        this.individualCollision = new double[cars.size()];
    }

    /** Våran metod som får en bil ifrån VehicleFactory som den sedan lägger till på skärmen
     */
    public boolean addRandomCarToScreen(Vehicles car) {
        if(cars.size() >= maxCarsOnScreen) {
            System.out.println("Det är för många bilar på skärmen just nu!");
            return false;
        }
        cars.add(car);
        notifyObserver();
        return true;
    }

    /** Våran metod som får en ping från CarController och tar sedan bort den sista bilen på skärmen.
     */
    public boolean removeLastCarOnScreen() {
        if(cars.isEmpty()) {
            System.out.println("Det finns inga bilar på skärmen för tillfället!");
            return false;
        }
        cars.remove(cars.size() - 1);
        notifyObserver();
        return true;
    }

    /** En get-metod som används i CarController för att kolla om max antalet bilar på skärmen är uppnåd
     */
    public int getCarCountOnScreen() {
        return cars.size();
    }

    /** Bevarar inkapsling, går ej att lägga till eller tabort bilar ur listan.
     *  Med våran get-metod så får vi endast en konkret lista med vilka bilar
     *  som är i
    **/
    public List<Vehicles> getCars() {
        return Collections.unmodifiableList(cars);
    }


    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

    public void notifyObserver() {
        for(ModelObserver observers : observers) {
            observers.update();
        }
    }

    public void startAllCars() {
        for(Vehicles car : cars) {
            car.startEngine();
        }
        notifyObserver();
    }

    public void stopAllCars() {
        for(Vehicles car : cars) {
            car.stopEngine();
        }
        notifyObserver();
    }

    public void gasAll(double amount) {
        for(Vehicles car : cars) {
            car.gas(amount);
        }
        notifyObserver();
    }

    public void brakeAll(double amount) {
        for(Vehicles car : cars) {
            car.brake(amount);
        }
        notifyObserver();
    }

    public void turboOnAllSaabs() {
        for(Vehicles car : cars) {
            if(car instanceof Saab95 saab) {
                saab.setTurboOn();
            }
        }
        notifyObserver();
    }

    public void turboOffAllSaabs() {
        for(Vehicles car : cars) {
            if(car instanceof Saab95 saab) {
                saab.setTurboOff();
            }
        }
        notifyObserver();
    }

    public void lowerAllScaniaRamps() {
        for(Vehicles car : cars) {
            if(car instanceof Scania scania) {
                scania.lowerRamp(10);
            }
        }
        notifyObserver();
    }

    public void raiseAllScaniaRamps() {
        for(Vehicles car : cars) {
            if(car instanceof Scania scania) {
                scania.raiseRamp(10);
            }
        }
        notifyObserver();
    }


    /** Metoden flyttar på bilar och uppdaterar positioner, de kollar även om kollison har skett med väggar/workshops
     * @param panelWidth
     * @param panelHeight
     * @param workshopWidth
     * @param workshopHeight
     */
    public void moveCars(int panelWidth, int panelHeight,
                         int workshopWidth, int workshopHeight) {
        double currentTime = System.currentTimeMillis();

        for(int i = 0; i < cars.size(); i++) {
            Vehicles car = cars.get(i);

            if(currentTime < individualCollision[i]) {
                continue;
            }

            car.move();

            double xCoord = car.getXCoord();
            double yCoord = car.getYCoord();

            // Skapar rektanglar för collison hitbox med bilar.
            Rectangle workshop1 = new Rectangle(panelWidth - workshopWidth, 0,
                                                workshopWidth, workshopHeight);
            Rectangle workshop2 = new Rectangle(panelWidth - workshopWidth, 200,
                                               workshopWidth, workshopHeight);
            Rectangle carBounds = new Rectangle((int) Math.round(xCoord), (int) Math.round(yCoord),
                                                100, 60);

            if(carBounds.intersects(workshop1) || carBounds.intersects(workshop2)) {
                if(car instanceof Volvo240 volvo) {
                    volvoWorkshop.takeCar(volvo);
                    cars.remove(i);
                    shiftCollisionArrayLeft(i);
                    i--;
                    continue;
                }
            }

            //Sätter våra bounds vart bilarna ska kollidera och vända.
            double maxX = panelWidth - 100;
            double maxY = panelHeight - 60;

            boolean touchX = false;
            boolean touchY = false;


            //Här är kollision "regler" så att xCoord och y inte åker ut ur panelen.
            if(xCoord < 0) {
                xCoord = 0;
                touchX = true;
            } else if(xCoord > maxX) {
                xCoord = maxX;
                touchX = true;
            }

            // inte riktigt relevant då bilar rör sig i sidled endast, men för korrektheten skull har vi med detta.
            if(yCoord < 0) {
                yCoord = 0;
                touchY = true;
            } else if (yCoord > maxY) {
                yCoord = maxY;
                touchY = true;
            }

            //Om xCoord eller yCoord är true, alltså kolliderat så sätter vi deras positon och sedan inverterar axis.
            if(touchX || touchY) {
                car.setPosition(xCoord, yCoord);

                if(touchX) {
                    car.invertX();
                }

                if(touchY) {
                    car.invertY();
                }

                individualCollision[i] = currentTime + driveDelay;
            }

            if(individualCollision[i] != 0 && currentTime >= individualCollision[i]) {
                individualCollision[i] = 0;
            }
        }
        notifyObserver();
    }

    //En metod för när vi tar bort bilar så behöver vi förflytta indexet i arrayen.
    private void shiftCollisionArrayLeft(int removeIndex) {
        for(int i = removeIndex; i < individualCollision.length - 1; i++) {
            individualCollision[i] = individualCollision[i + 1];
        }
        individualCollision[individualCollision.length - 1] = 0;
    }
}
