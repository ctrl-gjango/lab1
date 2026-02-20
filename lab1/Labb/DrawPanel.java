import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.

public class DrawPanel extends JPanel
{

    final static int imgsize = 90;
    String[] carNames = {"Volvo240.jpg", "Saab95.jpg", "Scania.jpg"};
    String[] workshopNames = {"VolvoBrand.jpg"};

    List<Point> initialOffsets = new ArrayList<>();
    List<BufferedImage> carImage = new ArrayList<>();
    List<BufferedImage> workshopImages = new ArrayList<>();
    List<Point> carPosition = new ArrayList<>();
    List<Point> workshopPosition = new ArrayList<>();
    List<Boolean> inWorkshop = new ArrayList<>();

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y)
    {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.darkGray);
        // Print an error message in case file is not found with a try/catch block
        //
        for (int i = 0; i < carNames.length; i++) {
            String name = carNames[i];
            try {
                BufferedImage img = ImageIO.read(DrawPanel.class.getResourceAsStream("/pics/" + name));
                if (img != null) {
                    carImage.add(img);
                    carPosition.add(new Point(0, 20));        // start at 0, will be updated by moveit
                    initialOffsets.add(new Point(i * (imgsize + 20), 20));
                    inWorkshop.add(false);  // <-- IMPORTANT: initialize here
                }
            } catch (IOException | IllegalArgumentException e) {
                System.out.println("Could not load car image: " + name);
            }
        }
        for (String name : workshopNames) {
            try {
                BufferedImage img = ImageIO.read(DrawPanel.class.getResourceAsStream("/pics/" + name));
                if (img != null)
                {
                    workshopImages.add(img);
                    workshopPosition.add(new Point(0,220));
                }

            } catch (IOException | IllegalArgumentException e)
            {
                System.out.println("Could not load image: " + name);
            }
        }

    }


    void moveit(int index, int x, int y)
    {
        if(index >= 0 && index < carPosition.size())
        {
            int panelHeight = getHeight();
            int panelwidth = getWidth();
            Point invpos = carPosition.get(index);

            invpos.x -= x;
            invpos.y -= y;

            if (x<0)
            {
                x = 0;
                //x = invpos.x;
            }
            if (x>panelwidth - imgsize)
            {
                x = panelwidth - imgsize;
            }

            if (y<0)
            {
                y = 0;
            }
            if (y>panelHeight - imgsize)
            {
                y = panelHeight - imgsize;
            }

            Point offset = initialOffsets.get(index);
            carPosition.get(index).x = x + offset.x;
            carPosition.get(index).y = y + offset.y;

            repaint();
        }

    }

    void checkWorkshopCollisions() {
        for (int i = 0; i < carImage.size(); i++) {
            String carName = carNames[i].replace(".jpg", "");      // Volvo240
            String carBrand = carName.replaceAll("[0-9]", "");     // Volvo

            Point carPos = carPosition.get(i);

            for (int j = 0; j < workshopImages.size(); j++) {
                String workshopBrand = workshopNames[j].replace("Brand.jpg", ""); // Volvo
                Point workshopPos = workshopPosition.get(j);

                Rectangle carRect = new Rectangle(carPos.x, carPos.y, imgsize, imgsize);
                Rectangle workshopRect = new Rectangle(workshopPos.x, workshopPos.y, imgsize, imgsize);

                if (carBrand.equals(workshopBrand) && carRect.intersects(workshopRect)) {
                    // Snap car to workshop
                    carPos.x = workshopPos.x;
                    carPos.y = workshopPos.y;

                    // Mark the car as in workshop
                    inWorkshop.set(i, true);

                    // Optional: replace workshop image with car image
                    workshopImages.set(j, carImage.get(i));
                }
            }
        }
    }



    // This method is called each time the panel updates/refreshes/repaints itself
    // TODO: Change to suit your needs.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = 0;

        for (int i = 0; i < carImage.size(); i++) {
            if (!inWorkshop.get(i)) {
                BufferedImage img = carImage.get(i);
                Point p = carPosition.get(i);
                g.drawImage(img, p.x, p.y, imgsize, imgsize, null);
            }
        }

        for (int j = 0; j < workshopImages.size(); j++) {
            BufferedImage img = workshopImages.get(j);
            Point p = workshopPosition.get(j);
            g.drawImage(img, p.x, p.y, imgsize, imgsize, null);
        }

    }
}
