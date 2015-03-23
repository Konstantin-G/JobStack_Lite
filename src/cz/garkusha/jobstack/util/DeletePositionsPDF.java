package cz.garkusha.jobstack.util;


import cz.garkusha.jobstack.view.Dialogs;

import java.io.File;
import java.util.ArrayList;

/**
 *  The class deletes all pdf file od positions , were deleted or unsaved in database.
 *
 * @author Konstantin Garkusha
 */
public class DeletePositionsPDF {
    private static final ArrayList<String> deletedList = new ArrayList<>();
    private static final ArrayList<String> unsavedList = new ArrayList<>();

    public static ArrayList<String> getDeletedList() {
        return deletedList;
    }
    public static ArrayList<String> getUnsavedList() {
        return unsavedList;
    }

    public static void deletedPDFDelete(){
        for (String s : deletedList) {
            File fileToDelete = new File(Path.getAbsoluteProgramPath() + File.separator + s);
            if (fileToDelete.exists()){
                boolean isDeleted = fileToDelete.delete();
                if (!isDeleted){
                    Dialogs.someError("Some file aren't deleted");
                }
            }
        }
    }

    public static void unsavedPDFDelete(){
        for (String s : unsavedList) {
            File fileToDelete = new File(Path.getAbsoluteProgramPath() + File.separator + s);
            if (fileToDelete.exists()){
                boolean isDeleted = fileToDelete.delete();
                if (!isDeleted){
                    Dialogs.someError("Some file aren't deleted");
                }
            }
        }
    }
}
