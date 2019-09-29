package common;

import java.io.File;

public class Settings {

    static final public String PROJECT_PATH = "C:/MyGameDev/AndroidBot/EfesusBot/";

    static final public String LIB_JACOB = PROJECT_PATH + "lib/jacob-1.19/jacob-1.19-x64.dll";

    static private String projectPath;

    static public String getProjectPath() {
        if ( projectPath == null ) {
            File f = new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            f = f.getAbsoluteFile();

            String currentName = f.getName();
            while (currentName != null && !currentName.equals("EfesusBot")) {
                f = f.getParentFile();
                currentName = f.getName();
            }
            projectPath = f.getPath() + "/";
        }

        return projectPath;
    }
}
