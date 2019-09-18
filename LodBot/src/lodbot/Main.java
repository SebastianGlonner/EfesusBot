package lodbot;

import common.Point;
import common.WindowsBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    static public Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            WindowsBot winBot = WindowsBot.get();

            String blueStacksHandle = "BlueStacks";
            log.info(blueStacksHandle);

            Point bsPoint = winBot.winPos(blueStacksHandle);

            log.info(bsPoint);

            winBot.winMove(blueStacksHandle, 10, 10);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
