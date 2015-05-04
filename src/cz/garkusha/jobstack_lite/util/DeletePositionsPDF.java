package cz.garkusha.jobstack_lite.util;


import java.io.File;

/**
 *  The class deletes all pdf file in positions , were deleted or unsaved in database.
 *
 * @author Konstantin Garkusha
 */
public class DeletePositionsPDF {

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
