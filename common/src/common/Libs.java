package common;

import com.jacob.com.LibraryLoader;

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
}
