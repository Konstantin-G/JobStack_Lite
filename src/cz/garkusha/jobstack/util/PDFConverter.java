package cz.garkusha.jobstack.util;

import org.allcolor.yahp.converter.CYaHPConverter;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Model class for parsing a html to the pdf.
 *
 * @author Konstantin Garkusha
 */
public class PDFConverter {

    public PDFConverter(String url, String absoluteReferenceToJobPDF) {
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
        File makeDirectory = new File(Path.getAbsoluteProgramPath());
        if (!makeDirectory.exists()) {
            //noinspection ResultOfMethodCallIgnored
            makeDirectory.mkdir();
        }
        // save pdf in outfile
        File filePDF = new File(absoluteReferenceToJobPDF);

        try (FileOutputStream out = new FileOutputStream(filePDF)){
            converter.convertToPdf(new URL(url), IHtmlToPdfTransformer.A4P, headerFooterList, out, properties);
        } catch (IHtmlToPdfTransformer.CConvertException | IOException e) {
            e.printStackTrace();
        }
    }
}
