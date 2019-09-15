import autoitx4java.AutoItX;
import com.jacob.com.LibraryLoader;

import java.io.File;

import static autoitx4java.AutoItX.SW_MAXIMIZE;

public class Main {

    public static void main(String[] args) {
        File file = new File("jacob-1.19", "jacob-1.19-x64.dll"); //path to the jacob dll
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());

        try {
            notepad();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void notepad() throws InterruptedException {
        AutoItX x = new AutoItX();
        String notepad = "Unbenannt - Editor";
        String testString = "this is a test.";
        x.run("notepad.exe", ".", SW_MAXIMIZE);

        x.winActivate(notepad);
        x.winWaitActive(notepad);
        x.send(testString);
        // Assert.assertTrue(x.winExists(notepad, testString));
        x.winClose(notepad, testString);
        x.winWaitActive("*Unbenannt - Editor");
        x.send("{ALT}n");
        // Assert.assertFalse(x.winExists(notepad, testString));
    }

    public static void calc() throws InterruptedException {
        AutoItX x = new AutoItX();
        x.run("calc.exe");
        x.winActivate("Calculator");
        x.winWaitActive("Calculator");
//Enter 3
        x.controlClick("Calculator", "", "133") ;
        Thread.sleep(1000);
//Enter +
        x.controlClick("Calculator", "", "93") ;
        Thread.sleep(1000);
//Enter 3
        x.controlClick("Calculator", "", "133") ;
        Thread.sleep(1000);
//Enter =
        x.controlClick("Calculator", "", "121") ;
    }
}
