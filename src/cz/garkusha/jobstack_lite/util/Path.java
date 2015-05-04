package cz.garkusha.jobstack_lite.util;

import java.io.File;

/**
 *  The class stores all program path .
 *
 * @author Konstantin Garkusha
 */

/*
-|              ABSOLUTE_PROGRAM_PATH
 |---|          DATA_FOLDER
 |   |---|      DB_NAME
 |   |   |---   JOBS_DESCRIPTION_FOLDER

*/

public class Path {
    private static final String ABSOLUTE_PROGRAM_PATH = System.getProperty("user.dir");
    private static final String TEMP_FOLDER = System.getProperty("java.io.tmpdir");
    private static final String DATA_FOLDER = "data";
    private static final String DB_NAME = "JavaDeveloper2015";
    private static final String JOBS_DESCRIPTION_FOLDER = "jobsDescription";

    private static final String PATH_TO_DB_FILE = "jdbc:derby:" + getProgramTempFolder() + "tmp_" + DB_NAME;

    private static final String RELATIVE_JOB_DESCRIPTION_PATH = DATA_FOLDER + File.separator + DB_NAME + File.separator + JOBS_DESCRIPTION_FOLDER + File.separator ;
    private static final String ABSOLUTE_JOB_DESCRIPTION_PATH = ABSOLUTE_PROGRAM_PATH + File.separator + RELATIVE_JOB_DESCRIPTION_PATH;

    private static final String ABSOLUTE_DATA_PATH = ABSOLUTE_PROGRAM_PATH + File.separator + DATA_FOLDER  + File.separator;


    public static String getProgramTempFolder() {
        return TEMP_FOLDER + "JobStack" + File.separator;
    }

    public static String getDbName() {
        return DB_NAME;
    }

    public static String getAbsoluteDataPath() {
        return ABSOLUTE_DATA_PATH;
    }

    public static String getAbsoluteProgramPath() {
        return ABSOLUTE_PROGRAM_PATH;
    }

    public static String getAbsoluteJobDescriptionPath() {
        return ABSOLUTE_JOB_DESCRIPTION_PATH;
    }

    public static String getPathToDbFile() {
        return PATH_TO_DB_FILE;
    }
}
