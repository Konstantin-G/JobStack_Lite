package cz.garkusha.jobstack_lite.util;


import java.io.File;

/**
 *  Class to make some thing with file system
 *
 * @author Konstantin Garkusha
 */
public class FileSystem {

    public static void clearTempDirectory(File file){
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                clearTempDirectory(f);
            }
        }
        file.delete();
        System.out.println("file \"" + file.getName() + "\" was deleted");
    }
}
