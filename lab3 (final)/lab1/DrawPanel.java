import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.
public class DrawPanel extends JPanel {
    private BufferedImage volvoImage;
    private BufferedImage saabImage;
    private BufferedImage scaniaImage;
    private BufferedImage biltransportImage;
    private BufferedImage workshopImage;

    private List<Vehicles> cars;

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);

        //läser in alla bilder i BufferedImage variablerna, finns den inte får vi en error.
        try {
            workshopImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg"));
            volvoImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Volvo240.jpg"));
            saabImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Saab95.jpg"));
            scaniaImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Scania.jpg"));
            biltransportImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Biltransport.jpg"));
        } catch (IOException ex) {
            throw new RuntimeException("kunde inte läsa bilder", ex);
        }
    }

    //En metod som tar emot en lista av bilar som den skall måla ut i GUI:t
    public void setCars(List<Vehicles> cars) {
        this.cars = cars;
    }

    //Tar mått på workshop bilden med inbyggda metoder.
    public int getWorkshopWidth() {
        return workshopImage.getWidth();
    }

    public int getWorkshopHeight() {
        return workshopImage.getHeight();
    }

    //Tar emot en bil och retunerar sedan en bild beroende på vilken bil som skickas in. På detta vis behöver vi inte
    //hålla reda på bild-retuneringen själva utan det räcker med att en lista bara skickas in så löser programmet det
    //själv.
    public BufferedImage getVehicleImage(Vehicles car) {
        if (car instanceof Volvo240) {
            return volvoImage;
        }
        if (car instanceof Saab95) {
            return saabImage;
        }
        if (car instanceof Scania) {
            return scaniaImage;
        }
        if (car instanceof Biltransport) {
            return biltransportImage;
        }
        return null;
    }

    //Våran metod som faktiskt lägger ut bilderna där de skall vara.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        /** Om bilen inte existerar så händer ingenting. Existerar bilen så itererar den över listan och använder sig
         *  av metoden ovan för att rita ut rätt bild för rätt bil. (Hantering skapad för om bilden inte existerar)
         *  Vi lagrar bilden i variablen "image", sedan tar vi ut x och y coordinater med våran egna metod.
         *  Ser till att bilen kör "East" aka höger genom en boolean check. Om den kör höger är bilens "framsida" vänd
         *  åt höger, kör den väst, inverterar vi bilden så dens "framsida" är vänster.
         */
        if (cars != null) {
            for (Vehicles car : cars) {
                BufferedImage image = getVehicleImage(car);
                if (image == null) {
                    System.out.println("Bilden existerar inte");
                    continue;
                }

                int imageX = (int) Math.round(car.getXCoord());
                int imageY = (int) Math.round(car.getYCoord());
                boolean facingRight = car.getDirection().equals("East");

                if (facingRight) {
                    g.drawImage(image, imageX, imageY, null);
                } else {
                    g.drawImage(image, imageX + image.getWidth(), imageY,
                            -image.getWidth(), image.getHeight(), null);
                }
            }
        }
        //Sedan hårdkodar vi in workshops så de är på samma ställe alltid.
        if (workshopImage != null) {
            int workshopX = getWidth() - workshopImage.getWidth();
            g.drawImage(workshopImage, workshopX, 0, null);
            g.drawImage(workshopImage, workshopX, 200, null);
        }
    }
}