package integration;

import autoitx4java.AutoItX;
import com.jacob.com.LibraryLoader;
import common.Libs;
import common.Screenshot;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class MsPaintExample {

    static public void doExample() throws InterruptedException, AWTException, IOException {
        Libs.loadAutoItX();

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String paintWindowTitle = "Unbenannt - Paint";

        AutoItX x = new AutoItX();
        int paintHandle = x.run("mspaint.exe", "", AutoItX.SW_HIDE);

        tryShowWindowUntil(x, paintWindowTitle);

        x.winActivate(paintWindowTitle);
        x.winMove(
                paintWindowTitle,
                "",
                100,
                100,
                1000,
                600
        );

        selectColorRed(x);

        x.mouseMove(300, 300);
        x.mouseDown("left");
        x.mouseMove(400, 400);
        x.mouseUp("left");

        Thread.sleep(300);
        x.winClose(paintWindowTitle);


    }

    static private void selectColorRed(AutoItX x) throws AWTException, IOException {
        String path = "C:/MyGameDev/AndroidBot/EfesusBot/examples/src/integration/";
        Mat redImage = Imgcodecs.imread(path + "mspaint-red.png");

        BufferedImage image = Screenshot.take();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( image, "jpg", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        Mat screen = Imgcodecs.imdecode(new MatOfByte(imageInByte), Imgcodecs.IMREAD_UNCHANGED);

        Mat result = new Mat();
        Imgproc.matchTemplate(screen, redImage, result, Imgproc.TM_SQDIFF);

        Core.MinMaxLocResult resultPos = Core.minMaxLoc(result);

        x.mouseMove((int) (resultPos.minLoc.x + 2), (int) (resultPos.minLoc.y + 2));
        x.mouseDown("left");
        x.mouseUp("left");
    }

    static private void tryShowWindowUntil(AutoItX x, String title) throws InterruptedException {
        tryShowWindowUntil(x, title, 2000);
    }

    static private void tryShowWindowUntil(AutoItX x, String title, int until) throws InterruptedException {
        int waited = 0;
        int waitPeriod = 300;
        int state = x.winGetState(title);

        int STATE_VISIBLE = 2;

        while ( (state & STATE_VISIBLE) != STATE_VISIBLE ) {
            Thread.sleep(waitPeriod);
            x.winSetState(title, "", AutoItX.SW_SHOW);
            state = x.winGetState(title);
            waited += waitPeriod;

            if ( waited > until )
                throw new InterruptedException("Could set state after seconds: " + until);
        }
    }

}
