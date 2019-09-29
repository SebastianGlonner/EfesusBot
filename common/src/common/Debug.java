package common;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Debug {

    static public String getDebugPath() {
        return Settings.getProjectPath() + "debug/";
    }

    static public void writeScreenshot(BufferedImage image) {
        File outputfile = new File(getDebugPath() + "last-screenshot.png");
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException("could not write screenshot", e);
        }
    }
}
