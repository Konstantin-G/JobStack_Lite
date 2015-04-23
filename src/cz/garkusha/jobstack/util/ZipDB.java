package cz.garkusha.jobstack.util;

/**
 * Class for zipping and unzipping database
 *
 * @author Konstantin Garkusha
 */
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

public class ZipDB
{
    private List<String> fileList;
    private static final String ZIP_FILE = Path.getAbsoluteDataPath() + Path.getDbName() + ".dat";
    private static final String TMP_FOLDER = Path.getProgramTempFolder() + "tmp_" + Path.getDbName();

    public static void compression(){
        ZipDB appZip = new ZipDB();
        appZip.fileList = new ArrayList<>();
        appZip.generateFileList(new File(TMP_FOLDER));
        appZip.zipIt(ZIP_FILE);
    }

    public static void unCompression(){
        ZipDB unZip = new ZipDB();
            unZip.unZipIt(ZIP_FILE, TMP_FOLDER);
    }

    /**
     * Zip it
     * @param zipFile output ZIP file location
     */
    public void zipIt(String zipFile){

        byte[] buffer = new byte[1024];

        try(FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos, Charset.forName("UTF-8"))
            ){
            for(String file : this.fileList){

                System.out.println("File Added : " + file);
                ZipEntry ze= new ZipEntry(file);
                zos.putNextEntry(ze);

                try (FileInputStream in = new FileInputStream(TMP_FOLDER + File.separator + file)){
                    int readBytes;
                    while ((readBytes = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, readBytes);
                    }
                } catch (IOException e) { e.printStackTrace(); }
            }
        }catch(IOException ex){ ex.printStackTrace(); }
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
     * @param zipFile input zip file
     * @param outputFolder zip file output folder
     */
    public void unZipIt(String zipFile, String outputFolder){

        byte[] buffer = new byte[1024];

        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))
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
                File newFile = new File(outputFolder + File.separator + fileName);

                System.out.println("file unzip : "+ newFile.getAbsoluteFile());

                //create all non exists folders
                //else we will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                try (FileOutputStream fos = new FileOutputStream(newFile)){
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ze = zis.getNextEntry();
            }
        }catch(IOException ex){ ex.printStackTrace(); }
    }
}
