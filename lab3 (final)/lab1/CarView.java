import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the full view of the MVC pattern of your car simulator.
 * It initializes with being center on the screen and attaching it's controller in it's state.
 * It communicates with the Controller by calling methods of it when an action fires of in
 * each of it's components.
 * TODO: Write more actionListeners and wire the rest of the buttons
 **/

public class CarView extends JFrame implements ModelObserver {
    private static final int X = 700;
    private static final int Y = 700;

    // The controller member
    CarController carC;
    private CarModel model;

    DrawPanel drawPanel = new DrawPanel(X, Y-200);

    JPanel controlPanel = new JPanel();
    JPanel carControllerPanel = new JPanel();

    JPanel gasPanel = new JPanel();
    JSpinner gasSpinner = new JSpinner();
    int gasAmount = 0;
    JLabel gasLabel = new JLabel("Amount of gas");

    JButton gasButton = new JButton("Gas");
    JButton brakeButton = new JButton("Brake");
    JButton turboOnButton = new JButton("Saab Turbo on");
    JButton turboOffButton = new JButton("Saab Turbo off");
    JButton liftBedButton = new JButton("Scania Lift Bed");
    JButton lowerBedButton = new JButton("Lower Lift Bed");

    JButton startButton = new JButton("Start all cars");
    JButton stopButton = new JButton("Stop all cars");

    JButton addRandomCarButton = new JButton("Add a random car");
    JButton removeLastCarButton = new JButton("Remove last car");

    // Konstruktor, vi ger CarView en referens till modellen.
    public CarView(String framename, CarModel model){
        this.model = model;
        initComponents(framename);
    }

    // Sparar controller i carC, sedan kopplar vi ihopp controller med knapparna genom denna metod.
    public void setController (CarController controller) {
        this.carC = controller;
        buttonListener();
    }

    public int getGasAmount() {
        return gasAmount;
    }

    public int getDrawPanelWidth() {
        return drawPanel.getWidth();
    }

    public int getDrawPanelHeight() {
        return drawPanel.getHeight();
    }

    public int getWorkshopWidth() {
        return drawPanel.getWorkshopWidth();
    }

    public int getWorkshopHeight() {
        return drawPanel.getWorkshopHeight();
    }

    // Sets everything in place and fits everything
    // TODO: Take a good look and make sure you understand how these methods and components work
    private void initComponents(String title) {

        this.setTitle(title);
        this.setPreferredSize(new Dimension(X, Y));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        this.add(drawPanel);

        SpinnerModel spinnerModel =
                new SpinnerNumberModel(0, //initial value
                        0, //min
                        100, //max
                        1);//step
        gasSpinner = new JSpinner(spinnerModel);
        gasSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                gasAmount = (int) ((JSpinner) e.getSource()).getValue();
            }
        });

        gasPanel.setLayout(new BorderLayout());
        gasPanel.add(gasLabel, BorderLayout.PAGE_START);
        gasPanel.add(gasSpinner, BorderLayout.PAGE_END);

        this.add(gasPanel);

        controlPanel.setLayout(new GridLayout(2, 4));

        controlPanel.add(gasButton, 0);
        controlPanel.add(turboOnButton, 1);
        controlPanel.add(liftBedButton, 2);
        controlPanel.add(brakeButton, 3);
        controlPanel.add(turboOffButton, 4);
        controlPanel.add(lowerBedButton, 5);
        controlPanel.setPreferredSize(new Dimension((X / 2) + 4, 150));
        controlPanel.setBackground(Color.CYAN);
        this.add(controlPanel);

        carControllerPanel.setLayout(new GridLayout(2, 1));
        carControllerPanel.add(addRandomCarButton, 0);
        carControllerPanel.add(removeLastCarButton, 1);
        carControllerPanel.setPreferredSize(new Dimension((X / 10), 150));
        this.add(carControllerPanel);


        startButton.setBackground(Color.blue);
        startButton.setForeground(Color.green);
        startButton.setPreferredSize(new Dimension(X / 8, 150));
        this.add(startButton);


        stopButton.setBackground(Color.red);
        stopButton.setForeground(Color.black);
        stopButton.setPreferredSize(new Dimension(X / 8, 150));
        this.add(stopButton);

        // Make the frame pack all it's components by respecting the sizes if possible.
        this.pack();

        // Get the computer screen resolution
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // Center the frame
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        // Make the frame visible
        this.setVisible(true);
        // Make sure the frame exits when "x" is pressed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // TODO: Create more for each component as necessary

    // samma som innan men nu använder jag mig av lambda-uttryck för att "compress" allting till mindre kod.
    // När en knapp trycks, anropas carC.metod(...), sedan in i carcontroller som sedan anropar model, view blir
    // notified av modellen och view uppdateras.
    private void buttonListener() {
        startButton.addActionListener(b -> carC.startAllCars());
        stopButton.addActionListener(b -> carC.stopAllCars());
        gasButton.addActionListener(b -> carC.gas(getGasAmount()));
        brakeButton.addActionListener(b -> carC.brake(getGasAmount()));
        turboOnButton.addActionListener(b -> carC.setTurboOn());
        turboOffButton.addActionListener(b -> carC.setTurboOff());
        lowerBedButton.addActionListener(b -> carC.setLowerRamp());
        liftBedButton.addActionListener(b -> carC.setRaiseRamp());
        addRandomCarButton.addActionListener(b -> carC.addRandomCarToScreen());
        removeLastCarButton.addActionListener(b -> carC.removeLastCarOnScreen());
    }

    //Vad som körs varjegång notifyObserver() anropas, enkelt sätt att uppdatera vårat GUI genom en observer-pattern.
    //Får data från modellen (alltså lista med bilar), ger den drawPanel (view) sedan ritas GUI:t om med den nya
    //datan.
    @Override
    public void update() {
        drawPanel.setCars(model.getCars());
        drawPanel.repaint();
    }
}