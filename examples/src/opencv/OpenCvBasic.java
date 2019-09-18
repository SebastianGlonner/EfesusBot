package opencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCvBasic {

    public static void doEample() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String path = "C:/MyGameDev/AndroidBot/EfesusBot/examples/src/opencv/";

        Mat mario = Imgcodecs.imread(path + "Mario.png");
        Mat marioWorld = Imgcodecs.imread(path +"MarioWorld.png");

        Mat result = new Mat();
        Imgproc.matchTemplate(marioWorld, mario, result, Imgproc.TM_SQDIFF);

        System.out.println(marioWorld);
        System.out.println(mario);
        System.out.println(result);

        Core.MinMaxLocResult posResult = Core.minMaxLoc(result);

        System.out.println("x: " + posResult.minLoc.x);
        System.out.println("y: " + posResult.minLoc.y);
    }

}
