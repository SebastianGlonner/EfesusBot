package common;

import com.jacob.com.LibraryLoader;
import org.opencv.core.Core;

import java.io.File;

public class Libs {

    static private boolean autoItXLoaded = false;

    static public void loadAutoItX() {
        if ( autoItXLoaded )
            return;

        autoItXLoaded = true;
        File file = new File(Settings.LIB_JACOB); //path to the jacob dll
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());

    }

    static public void loadOpenCv() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}
