package lodbot;

import common.Debug;
import common.Point;
import common.Screenshot;
import common.WindowsBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class Main {
    static private Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws AWTException {
        long now = System.currentTimeMillis();
        try {
            WindowsBot winBot = WindowsBot.get();
            LodBot lodBot = new LodBot(winBot);
            lodBot.mainLoop();

        } catch (Exception e) {
            e.printStackTrace();
            Debug.writeScreenshot(Screenshot.take());
        }

        System.out.println("@@@@@@@@@@@@@@@@@@@ bot run: " + (System.currentTimeMillis() - now));
    }
}
