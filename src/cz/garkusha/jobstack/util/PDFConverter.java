package cz.garkusha.jobstack.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.allcolor.yahp.converter.CYaHPConverter;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Model class for parsing a html to the pdf.
 *
 * @author Konstantin Garkusha
 */
public class PDFConverter {
    private boolean overwriteFile(String absoluteReferenceToJobPDF) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        String fileName = absoluteReferenceToJobPDF.substring(absoluteReferenceToJobPDF.lastIndexOf(File.separator) + 1
                                                                , absoluteReferenceToJobPDF.length() - 1);
        alert.setContentText("File \"" + fileName + "\"\nalready exists. Do you want to replace it?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else
            return false;
    }
    public PDFConverter(String url, String absoluteReferenceToJobPDF) {
        File makeDirectory = new File(Path.getAbsoluteProgramPath());
        if (!makeDirectory.exists()) {
            //noinspection ResultOfMethodCallIgnored
            makeDirectory.mkdir();
        }
        // save pdf in outfile
        File filePDF = new File(absoluteReferenceToJobPDF);
        if (filePDF.exists()) {
            if(!overwriteFile(absoluteReferenceToJobPDF)){
                return;
            }
        }

        // contains configuration properties
        Map<String, String> properties = new HashMap<>();
        // list containing header/footer
        List<IHtmlToPdfTransformer.CHeaderFooter> headerFooterList = new ArrayList<>();
        // add header/footer
        headerFooterList.add(new IHtmlToPdfTransformer.CHeaderFooter(
                "<table width=\"100%\"><tbody><tr><td align=\"right\">Page <pagenumber>/<pagecount></td></tr></tbody></table>",
                IHtmlToPdfTransformer.CHeaderFooter.HEADER));
        headerFooterList.add(new IHtmlToPdfTransformer.CHeaderFooter("2015 Konstantin Garkusha", IHtmlToPdfTransformer.CHeaderFooter.FOOTER));

        properties.put(IHtmlToPdfTransformer.PDF_RENDERER_CLASS, IHtmlToPdfTransformer.FLYINGSAUCER_PDF_RENDERER);

        //properties to change font
//        properties.put(IHtmlToPdfTransformer.FOP_TTF_FONT_PATH, null);

        // new converter
        CYaHPConverter converter = new CYaHPConverter();
        try (FileOutputStream out = new FileOutputStream(filePDF)){
            converter.convertToPdf(new URL(url), IHtmlToPdfTransformer.A4P, headerFooterList, out, properties);
        } catch (IHtmlToPdfTransformer.CConvertException | IOException e) {
            e.printStackTrace();
        }
    }
}
