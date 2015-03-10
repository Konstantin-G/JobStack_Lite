package cz.garkusha.jobsstack.util;

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
    private static final String RELATIVE_JOB_DESCRIPTION_PATH = "data" + File.separator + DBCommunication.getDBName() + File.separator + "jobsDescription" + File.separator ;
    private static final String ABSOLUTE_JOB_DESCRIPTION_PATH = System.getProperty("user.dir") + File.separator + RELATIVE_JOB_DESCRIPTION_PATH;
    private String relativeReferenceToJobPDF;
    private String absoluteReferenceToJobPDF;
    private String title;

    public PDFConverter(String url, String title) {
        this.title = title;
        this.relativeReferenceToJobPDF = RELATIVE_JOB_DESCRIPTION_PATH + getPDFFileName();
        this.absoluteReferenceToJobPDF = ABSOLUTE_JOB_DESCRIPTION_PATH + getPDFFileName();
        savePDF(url);
    }

    public String getRelativeReferenceToJobPDF() {
        return relativeReferenceToJobPDF;
    }

    private String getPDFFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return simpleDateFormat.format(new Date()) + "_" + this.title.replaceAll("[\\/:\"]", "-")
                                                            .replaceAll("[:*?<>|]", "").replaceAll("[ ]{2,}"," ") + ".pdf";
    }



    void savePDF(String url) {
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
//        properties.put(IHtmlToPdfTransformer.FOP_TTF_FONT_PATH, null);
        // new converter
        CYaHPConverter converter = new CYaHPConverter();
        File makeDirectory = new File(ABSOLUTE_JOB_DESCRIPTION_PATH);
        if (!makeDirectory.exists()) {
            //noinspection ResultOfMethodCallIgnored
            makeDirectory.mkdir();
        }
        // save pdf in outfile
        File filePDF = new File(this.absoluteReferenceToJobPDF);

        try (FileOutputStream out = new FileOutputStream(filePDF)){
            converter.convertToPdf(new URL(url), IHtmlToPdfTransformer.A4P, headerFooterList, out, properties);
        } catch (IHtmlToPdfTransformer.CConvertException | IOException e) {
            e.printStackTrace();
        }
    }
}
