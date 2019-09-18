import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TakeScreenshot {
    static public void doExample() throws AWTException, IOException {
        partscreen();
    }
    static public void partscreen() throws AWTException, IOException {
        String basePath = "C:\\Users\\Sebastian Glonner\\Desktop/".replaceAll("\\\\", "/");

        Robot robot = new Robot();
        String format = "jpg";
        String fileName = "PartScreenshot." + format;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle captureRect = new Rectangle(0, 0, screenSize.width / 2, screenSize.height / 2);
        BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
        ImageIO.write(screenFullImage, format, new File(basePath + fileName));

        System.out.println("A partly screenshot saved!");
    }

    static public void fullscreen() throws AWTException, IOException {
        String basePath = "C:\\Users\\Sebastian Glonner\\Desktop/".replaceAll("\\\\", "/");

        Robot robot = new Robot();
        String format = "jpg";
        String fileName = "FullScreenshot." + format;

        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
        ImageIO.write(screenFullImage, format, new File(basePath + fileName));

        System.out.println("A full screenshot saved!");
    }
}
