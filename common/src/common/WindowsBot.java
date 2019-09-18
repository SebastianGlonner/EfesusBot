package common;

import autoitx4java.AutoItX;
import com.jacob.com.LibraryLoader;

import java.io.File;

public class WindowsBot {

    static public WindowsBot get() {
        Libs.loadAutoItX();
        return new WindowsBot();
    }

    AutoItX autoItX;

    private WindowsBot() {
        autoItX = new AutoItX();
    }

    public void run() {
    }

    public String[][] listWindwos() {
        return autoItX.winList(null);
    }

    public String findBluestacks() {
        return autoItX.winGetHandle("BlueStacks");
    }

    public void winMove(String handle, int x, int y) {
        autoItX.winMove(handle, null, x, y);
    }

    public Point winPos(String handle) {
        return new Point(
                autoItX.winGetPosX(handle),
                autoItX.winGetPosY(handle)
        );
    }
}
