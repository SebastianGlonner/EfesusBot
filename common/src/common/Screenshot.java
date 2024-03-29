package common;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Screenshot {

    static private Robot robot;

    static private Robot getRobot() throws AWTException {
        if ( robot == null )
            robot = new Robot();

        return robot;
    }

    static public BufferedImage take() throws AWTException {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        return getRobot().createScreenCapture(screenRect);
    }

    static public BufferedImage take(int x, int y, int width, int height) throws AWTException {
        Rectangle screenRect = new Rectangle(x, y, width, height);
        return getRobot().createScreenCapture(screenRect);
    }
}
