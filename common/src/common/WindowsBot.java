package common;

import autoitx4java.AutoItX;

public class WindowsBot {

    static public final String WINDOW_NAME_BLUESTACKS = "BlueStacks";

    static public final String MOUSE_LEFT = "left";

    static private final int SLEEP_MSSECONDS = 400;

    static public WindowsBot get() {
        Libs.loadAutoItX();
        Libs.loadOpenCv();
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

    public AutoWindow openBlueStacksWindow()
    {
        if ( !autoItX.winExists(WINDOW_NAME_BLUESTACKS) )
            throw new RuntimeException("BlueStacks window does not exist!");

        return new AutoWindow(this, WINDOW_NAME_BLUESTACKS);
    }

    public void winMove(String handle, int x, int y) {
        autoItX.winMove(handle, null, x, y);
    }

    public void winSize(String handle, int width, int height) {
        Point pos = winPos(handle);
        autoItX.winMove(handle, null, pos.x, pos.y, width, height);
    }

    public Point winGetSize(String handle) {
        int height = autoItX.winGetClientSizeHeight(handle);
        int width = autoItX.winGetClientSizeWidth(handle);

        return new Point(
                width,
                height
        );
    }

    public Point winPos(String handle) {
        return new Point(
                autoItX.winGetPosX(handle),
                autoItX.winGetPosY(handle)
        );
    }

    public void winActivate(String handle) {
        autoItX.winActivate(handle);
    }

    public void click(Point pos) {
        // autoItX.mouseClick(MOUSE_LEFT, pos.x, pos.y, 1, 0);

        autoItX.mouseMove(pos.x, pos.y);

        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int x = autoItX.mouseGetPosX();
        if ( Math.abs(pos.x - x) > 2 ) {
            throw new RuntimeException("exit due to mouse movement?");
        }

        autoItX.mouseDown(MOUSE_LEFT);
        sleep(300);
        autoItX.mouseUp(MOUSE_LEFT);

        // move the mouse away to prevent problems with changed buttons due to hover
        sleep(100);
        autoItX.mouseMove(10, 10);
    }

    public int sleep() {
        try {
            Thread.sleep(SLEEP_MSSECONDS);
            return SLEEP_MSSECONDS;
        } catch (InterruptedException e) {
            throw new RuntimeException("Could not Thread.sleep!", e);
        }
    }

    public int sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
            return milliseconds;
        } catch (InterruptedException e) {
            throw new RuntimeException("Could not Thread.sleep!", e);
        }
    }

    public void send(String text) {
        autoItX.send(text, false);
    }

}
