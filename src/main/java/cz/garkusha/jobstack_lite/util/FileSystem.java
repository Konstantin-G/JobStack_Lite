package cz.garkusha.jobstack_lite.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 *  Class to make some thing with file system
 *
 * @author Konstantin Garkusha
 */
public class FileSystem {

    private static final Logger LOG = LoggerFactory.getLogger(FileSystem.class);

    public static void clearTempDirectory(){
        File tempDirectory = new File(Path.getProgramTempFolder());
        LOG.debug("Clearing temporary directory");
        clearDirectory(tempDirectory);
        LOG.debug("Temporary directory was cleared");
    }

    private static void clearDirectory(File file) {
        if (!file.exists() && file.listFiles() == null)
            return;
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                clearDirectory(f);
            }
        }
        if (!file.delete()){
            LOG.debug("Can't delete file: " + file.getName());
        }
    }

}
