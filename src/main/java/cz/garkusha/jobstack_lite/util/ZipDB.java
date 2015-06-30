package cz.garkusha.jobstack_lite.util;

/**
 * Class for zipping and unzipping database
 *
 * @author Konstantin Garkusha
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipDB {

    private static final Logger LOG = LoggerFactory.getLogger(ZipDB.class);

    private List<String> fileList;
    private static final String ZIP_FILE = Path.getAbsoluteDataPath() + Path.getDbName() + ".dat";
    private static final String TMP_FOLDER = Path.getProgramTempFolder() + "tmp_" + Path.getDbName();

    public static void compression(){
        ZipDB appZip = new ZipDB();
        appZip.fileList = new ArrayList<>();
        appZip.generateFileList(new File(TMP_FOLDER));
        appZip.zipIt();
    }

    public static void unCompression(){
        ZipDB unZip = new ZipDB();
        unZip.unZipIt();
    }

    /**
     * Zip it
     */
    public void zipIt(){
        LOG.info("Start compression database to zip");
        byte[] buffer = new byte[1024];

        //if dataDirectory is not exist
        File dataPath = new File(Path.getAbsoluteDataPath());
        if (!dataPath.exists()){
            if (dataPath.mkdir()){
                System.out.println("directory was successfully created: " + dataPath.toString());
            } else System.out.println("error creating directory: " + dataPath.toString());
        }

        try(FileOutputStream fos = new FileOutputStream(ZIP_FILE);
            ZipOutputStream zos = new ZipOutputStream(fos, Charset.forName("UTF-8"))
            ){
            for(String file : this.fileList){

                ZipEntry ze= new ZipEntry(file);
                zos.putNextEntry(ze);

                try (FileInputStream in = new FileInputStream(TMP_FOLDER + File.separator + file)){
                    int readBytes;
                    while ((readBytes = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, readBytes);
                    }
                } catch (IOException e) {
                    LOG.error("Error Database wasn't compressed successfully");
                    e.printStackTrace();
                }
            }
            LOG.debug("Database was compressed");
        }catch(IOException ex){
            LOG.error("Error Database wasn't compressed successfully");
            ex.printStackTrace(); }
    }

    /**
     * Traverse a directory and get all files,
     * and add the file into fileList
     * @param node file or directory
     */
    public void generateFileList(File node){

        //add file only
        if(node.isFile()){
            fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
        }

        if(node.isDirectory()){
            String[] subNote = node.list();
            for(String filename : subNote){
                generateFileList(new File(node, filename));
            }
        }

    }

    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file){
        return file.substring(TMP_FOLDER.length(), file.length());
    }

    /**
     * Unzip it
     */
    public void unZipIt(){
        LOG.info("Start extraction database from zip");
        byte[] buffer = new byte[1024];

        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(ZIP_FILE))
        ){

            //create output directory is not exists
            File folder = new File(TMP_FOLDER);
            if(!folder.exists()){
                folder.mkdir();
            }

            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while(ze!= null){

                String fileName = ze.getName();
                //linux
                if (TMP_FOLDER.startsWith("/var")){
                    fileName = fileName.substring(1, fileName.length());
                    fileName = fileName.replace("\\", "/");
                }
                File newFile = new File(TMP_FOLDER + File.separator + fileName);
                //create all non exists folders
                //else we will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                try (FileOutputStream fos = new FileOutputStream(newFile)){
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                } catch (IOException e) {
                    LOG.error("Error Database wasn't extracted successfully");
                    e.printStackTrace();
                }
                ze = zis.getNextEntry();
            }
            LOG.debug("Database was extracted");
        }catch(IOException ex){
            LOG.error("Error Database wasn't extracted successfully");
            ex.printStackTrace(); }
    }
}
