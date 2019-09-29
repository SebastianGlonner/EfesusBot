package common;

import autoitx4java.AutoItX;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.Buffer;

public class AutoWindow {

    private WindowsBot winBot;

    private String winHandle;

    private Area areaMask;

    private double IMAGE_HIT_TRESHOLD = 0.7;

    public AutoWindow(WindowsBot winBot, String winHandle) {
        this.winBot = winBot;
        this.winHandle = winHandle;
    }

    public void setAreaMask(Area mask) {
        this.areaMask = mask;
    }

    public void resetAreaMask() {
        this.areaMask = null;
    }

    public void winSize(int width, int height) {
        winBot.winSize(this.winHandle, width, height);
    }

    public Point winGetSize() {
        return winBot.winGetSize(this.winHandle);
    }

    public void winMove(int x, int y) {
        winBot.winMove(this.winHandle, x, y);
    }

    public Point winPos() {
        return winBot.winPos(this.winHandle);
    }

    public void winActivate() {
        winBot.winActivate(this.winHandle);
    }

    public BufferedImage takeScreenshot() {
        Point pos = winPos();
        Point size;
        if ( areaMask != null ) {
            pos.x += areaMask.topLeft.x;
            pos.y += areaMask.topLeft.y;
            size = areaMask.bottomRight;
        } else {
            size = winGetSize();
        }

        try {
            return Screenshot.take(
                    pos.x,
                    pos.y,
                    size.x,
                    size.y
            );
        } catch (AWTException e) {
            throw new RuntimeException("Cannot take screenshot of window", e);
        }
    }

    public Point findImage(Mat image, int waitMax) {
        int waited = 0;

        Point pos = findImage(image);
        while ( pos == null ) {
            waited += winBot.sleep();
            if ( waited > waitMax )
                return null;

            pos = findImage(image);
        }

        return pos;
    }

    public Point findImage(Mat image) {
        BufferedImage bufferedScreen = takeScreenshot();
        Debug.writeScreenshot(bufferedScreen);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedScreen, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();

            Mat screen = Imgcodecs.imdecode(new MatOfByte(imageInByte), Imgcodecs.IMREAD_UNCHANGED);

            Mat result = new Mat();
            Imgproc.matchTemplate(screen, image, result, Imgproc.TM_CCOEFF_NORMED);

            Core.MinMaxLocResult resultPos = Core.minMaxLoc(result);

            if ( resultPos.maxVal < IMAGE_HIT_TRESHOLD ) {
                // considered not found
                return null;
            }

            Point pos = new Point((int)resultPos.maxLoc.x, (int)resultPos.maxLoc.y);
            if ( pos.x + pos.y > 0 ) {
                return pos;
            }

            return null;
        } catch (IOException e) {
            throw new RuntimeException("Could not find image", e);
        }
    }

    public void click(Point pos) {
        Point winPos = winPos();

        if ( areaMask != null ) {
            pos.x += areaMask.topLeft.x;
            pos.y += areaMask.topLeft.y;
        }

        winBot.click(new Point(
                winPos.x + pos.x,
                winPos.y + pos.y
        ));
    }

    public boolean clickImage(Mat image, int waitMax) {
        return clickImage(image, waitMax, ClickSuccessCheck.None);
    }

    public boolean clickImage(Mat image, int waitMax, ClickSuccessCheck clickSuccessCheck) {
        boolean result = clickImageInternal(image, waitMax);

        if ( result == true && clickSuccessCheck == ClickSuccessCheck.ImageGone ){
            for ( int i = 0; i < 3; i++ ) {
                winBot.sleep();
                if ( findImage(image, 600) == null )
                    return true;

                clickImageInternal(image, waitMax);
            }

            if ( findImage(image, 600) != null )
                return false;
        }

        return result;
    }

    public boolean clickImageInternal(Mat image, int waitMax) {
        Point pos = findImage(image, waitMax);

        if ( pos == null )
            return false;

        pos.x += (image.cols() / 2);
        pos.y += (image.rows() / 2);
        click(pos);

        return true;
    }

    public void send(String text) {
        winActivate();
        winBot.send(text);
    }

    public void bluestacksReturn() {
        // return button is located bottom left
        Point pos = winPos();
        Point size = winGetSize();

        click(new Point(
                25,
                size.y - 20
        ));

        winBot.sleep();
    }
}
