import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
* This class represents the Controller part in the MVC pattern.
* It's responsibilities is to listen to the View and responds in a appropriate manner by
* modifying the model state and the updating the view.
 */

public class CarController {
    // member fields:
    private final int driveDelay = 200;
    private double[] individualColli;

    private final int delay = 50;
    private Timer timer = new Timer(delay, new TimerListener());

    CarView frame;
    ArrayList<Vehicles> cars = new ArrayList<>();

    //methods:
    public static void main(String[] args) {
        CarController cc = new CarController();


        cc.cars.add(new Volvo240());
        cc.cars.add(new Saab95());
        cc.cars.add(new Scania());

        cc.individualColli = new double[cc.cars.size()];

        cc.frame = new CarView("CarSim 1.0", cc);

        cc.cars.get(0).setPosition(0, 0);
        cc.cars.get(1).setPosition(0, 200);
        cc.cars.get(2).setPosition(0, 400);

        cc.frame.drawPanel.addCar("pics/Volvo240.jpg", 0, 0, true);
        cc.frame.drawPanel.addCar("pics/Saab95.jpg", 0, 0, true);
        cc.frame.drawPanel.addCar("pics/Scania.jpg", 0, 0, true);

/**
        cc.cars.get(0).setPosition(0, 0);
        cc.cars.get(1).setPosition(0, 200);
        cc.cars.get(2).setPosition(0, 400);
**/

        cc.timer.start();
    }

    /* Each step the TimerListener moves all the cars in the list and tells the
    * view to update its images. Change this method to your needs.
    * */
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double currentTimer = System.currentTimeMillis();

            int panelWidth = frame.drawPanel.getWidth();
            int panelHeight = frame.drawPanel.getHeight();

            for (int i = 0; i < cars.size(); i++) {
                Vehicles car = cars.get(i);

                if(currentTimer < individualColli[i]) {
                    frame.drawPanel.moveCar(i, (int)Math.round(car.getXCoord()), (int)Math.round(car.getYCoord()));
                    continue;
                }

                car.move();

                double x = car.getXCoord();
                double y = car.getYCoord();

                int imgWidth = frame.drawPanel.getCarImageWidth(i);
                int imgHeight = frame.drawPanel.getCarImageHeight(i);

                Rectangle carBorder = new Rectangle((int)x, (int)y, imgWidth, imgHeight);
                Rectangle workshop1 = frame.drawPanel.getWorkshop1();
                Rectangle workshop2 = frame.drawPanel.getWorkshop2();
                Workshop<Volvo240> volvoshop = new Workshop<Volvo240>(6);

                if(carBorder.intersects(workshop1) || carBorder.intersects(workshop2)) {
                    if(car instanceof Volvo240 volvo) {
                        volvoshop.takeCar(volvo);
                        cars.remove(i);
                        frame.drawPanel.removeCar(i);

                        i--;
                        continue;
                    }
                }

                double maxX = panelWidth - imgWidth;
                double maxY = panelHeight - imgHeight;

                boolean touchMaxX = false;
                boolean touchMaxY = false;

                if(x < 0) {
                    x = 0;
                    touchMaxX = true;
                } else if(x > maxX) {
                    x = maxX;
                    touchMaxX = true;
                }

                if(y < 0) {
                    y = 0; touchMaxY = true;
                } else if(y > maxY) {
                    y = maxY; touchMaxY = true;
                }

                if(touchMaxX || touchMaxY) {
                    // snap back inside
                    car.setPosition(x, y);

                    if(touchMaxX) {
                        car.invertX();
                    }

                    if(touchMaxY) {
                        car.invertY();
                    }

                    individualColli[i] = currentTimer + driveDelay;
                }

                if(individualColli[i] != 0 && currentTimer >= individualColli[i]) {
                    individualColli[i] = 0;
                }

                // Update sprite direction + position
                boolean facingRight = car.getDirection().equals("East");
                frame.drawPanel.setDrivingRight(i, facingRight);
                frame.drawPanel.moveCar(i, (int) Math.round(x), (int) Math.round(y));
            }

            frame.drawPanel.repaint();
        }
    }

    void startAllCars() {
        for(Vehicles car : cars) {
            car.startEngine();
        }
    }

    void stopAllCars() {
        for(Vehicles car : cars) {
            car.stopEngine();
        }
    }

    void gas(int amount) {
        double gas = ((double) amount) / 100;
        for(Vehicles car : cars) {
            car.gas(gas);
        }
    }

    void brake(int amount) {
        double brake = ((double) amount) / 100;
        for(Vehicles car : cars) {
            car.brake(brake);
        }
    }

    void setTurboOn() {
        for(Vehicles car : cars) {
            if(car instanceof Saab95 saab ) {
                saab.setTurboOn();
            }
        }
    }

    void setTurboOff() {
        for(Vehicles car : cars) {
            if(car instanceof Saab95 saab) {
                saab.setTurboOff();
            }
        }
    }

    void setLowerRamp() {
        for(Vehicles car : cars) {
            if(car instanceof Scania scania) {
                scania.lowerRamp(10);
                System.out.println(scania.getRampAngle());

            }
        }
    }

    void setRaiseRamp() {
        for(Vehicles car : cars) {
            if(car instanceof Scania scania) {
                scania.raiseRamp(10);
                System.out.println(scania.getRampAngle());
            }
        }
    }
}
