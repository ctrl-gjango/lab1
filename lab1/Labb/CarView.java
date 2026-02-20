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

public class CarView extends JFrame{
    private static final int X = 800;
    private static final int Y = 700;

    // The controller member
    CarController carC;

    DrawPanel drawPanel = new DrawPanel(X, Y-240);

    JPanel controlPanel = new JPanel();
    JPanel controlGridPanel = new JPanel();
    JPanel startstopPanel = new JPanel();

    JPanel gasPanel = new JPanel();


    int gasAmount = 0;
    int brakeAmount = 0;
    JLabel gasLabel = new JLabel("Amount of gas");

    JButton gasButton = new JButton("Gas");
    JButton brakeButton = new JButton("Brake");
    JButton turboOnButton = new JButton("Saab Turbo on");
    JButton turboOffButton = new JButton("Saab Turbo off");
    JButton liftBedButton = new JButton("Raise Lift Bed");
    JButton lowerBedButton = new JButton("Lower Lift Bed");

    JButton startButton = new JButton("Start all cars");
    JButton stopButton = new JButton("Stop all cars");

    // Constructor
    public CarView(String framename, CarController cc)
    {
        this.carC = cc;
        initComponents(framename);
    }

    // Sets everything in place and fits everything
    // TODO: Take a good look and make sure you understand how these methods and components work
    private void initComponents(String title) {

        this.setTitle(title);
        this.setPreferredSize(new Dimension(X,Y));
        this.setLayout(new BorderLayout());

        this.add(drawPanel, BorderLayout.CENTER);




        SpinnerModel spinnerModel =
                new SpinnerNumberModel(0, //initial value
                        0, //min
                        100, //max
                        1);//step
        JSpinner gasSpinner = new JSpinner(spinnerModel);
        JSpinner brakeSpinner = new JSpinner(spinnerModel);

        gasSpinner.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                 gasAmount = (int) ((JSpinner)e.getSource()).getValue();
                 brakeAmount = (int) ((JSpinner)e.getSource()).getValue();
            }
        });

        JPanel gasGroup = new JPanel(new BorderLayout());

        gasGroup.add(gasButton, BorderLayout.CENTER);
        gasGroup.add(gasSpinner, BorderLayout.SOUTH);

        JPanel brakeGroup = new JPanel(new BorderLayout());

        brakeGroup.add(brakeButton, BorderLayout.CENTER);
        brakeGroup.add(brakeSpinner, BorderLayout.SOUTH);

        startButton.setBackground(new Color(0,51,0));
        startButton.setForeground(Color.black);
        startButton.setPreferredSize(new Dimension(X/5-15,200));



        stopButton.setBackground(new Color(111,0,0));
        stopButton.setForeground(Color.black);
        stopButton.setPreferredSize(new Dimension(X/5-15,200));


        controlGridPanel.setLayout(new GridLayout(2,4));

        controlGridPanel.add(gasGroup, 0);
        controlGridPanel.add(turboOnButton, 1);
        controlGridPanel.add(liftBedButton, 2);
        controlGridPanel.add(brakeGroup, 3);
        controlGridPanel.add(turboOffButton, 4);
        controlGridPanel.add(lowerBedButton, 5);
        controlGridPanel.setPreferredSize(new Dimension((X/2)+4, 200));

        startstopPanel.setLayout(new GridLayout(1,2));
        startstopPanel.add(startButton);
        startstopPanel.add(stopButton);

        controlPanel.add(controlGridPanel);
        controlPanel.add(startstopPanel);

        this.add(controlPanel, BorderLayout.SOUTH);
        controlPanel.setBackground(Color.white);




        // This actionListener is for the gas button only
        // TODO: Create more for each component as necessary
        gasButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                carC.gas(gasAmount);
            }
        });
        brakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                carC.gas(brakeAmount);
            }
        });
        turboOnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carC.turboOn();
            }
        });
        turboOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carC.turboOff();
            }
        });
        liftBedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carC.raiseLiftBed();
            }
        });
        lowerBedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carC.lowerLiftBed();
            }
        });

        // Make the frame pack all it's components by respecting the sizes if possible.
        this.pack();

        // Get the computer screen resolution
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // Center the frame
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        // Make the frame visible
        this.setVisible(true);
        // Make sure the frame exits when "x" is pressed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}