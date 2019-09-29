import autoxit.AutoXItExample;
import integration.MsPaintExample;
import opencv.OpenCvBasic;

import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {

            // AutoXItExample.doEample();

            OpenCvBasic.doEample();

            // TakeScreenshot.doExample();

            // MsPaintExample.doExample();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
