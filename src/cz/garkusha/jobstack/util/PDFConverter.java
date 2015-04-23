package cz.garkusha.jobstack.util;

import cz.garkusha.jobstack.view.Dialogs;
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

    public PDFConverter(String url, String relativeReferenceToJobPDF) {
        File makeDirectory = new File(Path.getAbsoluteJobDescriptionPath());
        if (!makeDirectory.exists()) {
            //noinspection ResultOfMethodCallIgnored
            makeDirectory.mkdir();
        }
        // save pdf in outfile
        File filePDF = new File(Path.getProgramTempFolder() + "tmp_" + Path.getDbName() + File.separator
                + "jobsDescription" + File.separator + relativeReferenceToJobPDF);
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
        properties.put(IHtmlToPdfTransformer.FOP_TTF_FONT_PATH, Path.getAbsoluteProgramPath() + "src/cz/garkusha/jobstack/resources");

        // new converter
        CYaHPConverter converter = new CYaHPConverter();
        try (FileOutputStream out = new FileOutputStream(filePDF)){
            converter.convertToPdf(new URL(url), IHtmlToPdfTransformer.A4P, headerFooterList, out, properties);
        } catch (IHtmlToPdfTransformer.CConvertException | IOException e) {
            e.printStackTrace();
            boolean isFileDeleted = filePDF.delete();
            if (isFileDeleted){
                Dialogs.someError("Some error in the PDF Converter,\npdf file wasn't created");
            }
        }
        // I had a "Exception in thread "AWT-Windows" java.lang.IllegalStateException: Shutdown in progress" when I made 1 pdf file in program,
        // So i fixed this by create 2 instance of CYaHPConverter class.
        new CYaHPConverter();
    }
}
