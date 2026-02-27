import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.
public class DrawPanel extends JPanel {

    private final List<BufferedImage> carImages = new ArrayList<>();
    private final List<Point> carPoints = new ArrayList<>();
    private final List<Boolean> carDriveRight = new ArrayList<>();

    private BufferedImage workshopImage;
    private final Point pointWorkshop1 = new Point();
    private final Point pointWorkshop2 = new Point();

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);

        try {
            workshopImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int addCar(String resourcePath, int x, int y, boolean driveRight) {
        try {
            BufferedImage img = ImageIO.read(DrawPanel.class.getResourceAsStream(resourcePath));
            carImages.add(img);
            carPoints.add(new Point(x, y));
            carDriveRight.add(driveRight);
            return carImages.size() - 1;
        } catch (IOException ex) {
            throw new RuntimeException("Kunde inte hittabild: " + resourcePath, ex);
        }
    }

    public void moveCar(int index, int x, int y) {
        carPoints.get(index).x = x;
        carPoints.get(index).y = y;
    }

    public void removeCar(int index) {
        carImages.remove(index);
        carPoints.remove(index);
        carDriveRight.remove(index);
    }

    public void setDrivingRight(int index, boolean driveRight) {
        carDriveRight.set(index, driveRight);
    }

    public int getCarImageWidth(int index) {
        return carImages.get(index).getWidth();
    }

    public int getCarImageHeight(int index) {
        return carImages.get(index).getHeight();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < carImages.size(); i++) {
            BufferedImage img = carImages.get(i);
            Point p = carPoints.get(i);
            boolean right = carDriveRight.get(i);

            if (right) {
                g.drawImage(img, p.x, p.y, null);
            } else {
                g.drawImage(img, p.x + img.getWidth(), p.y, -img.getWidth(), img.getHeight(), null);
            }
        }

        if (workshopImage != null) {
            int x = getWidth() - workshopImage.getWidth();

            pointWorkshop1.setLocation(x, 0);
            pointWorkshop2.setLocation(x, 200);

            g.drawImage(workshopImage, pointWorkshop1.x, pointWorkshop1.y, null);
            g.drawImage(workshopImage, pointWorkshop2.x, pointWorkshop2.y, null);
        }
    }

    public Rectangle getWorkshop1() {
        return new Rectangle(pointWorkshop1.x,
                pointWorkshop1.y,
                workshopImage.getWidth(),
                workshopImage.getHeight());
    }

    public Rectangle getWorkshop2() {
        return new Rectangle(pointWorkshop2.x,
                pointWorkshop2.y,
                workshopImage.getWidth(),
                workshopImage.getHeight());
    }
}