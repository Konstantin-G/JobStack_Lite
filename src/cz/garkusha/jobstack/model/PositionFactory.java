package cz.garkusha.jobstack.model;

import cz.garkusha.jobstack.util.HTMLParser;
import cz.garkusha.jobstack.util.PDFConverter;

/**
 * Model class for new Position.
 *
 * @author Konstantin Garkusha
 */
public class PositionFactory {

    public static Position getNewPosition(int id, String URLToJob){
        HTMLParser htmlParser = new HTMLParser(URLToJob);
        PDFConverter pdfConverter = new PDFConverter(URLToJob, htmlParser.getCompany(), htmlParser.getJobTitle());
        return new Position(id , null, htmlParser.getCompany(), htmlParser.getJobTitle(), pdfConverter.getRelativeReferenceToJobPDF(),
                                htmlParser.getLocation(), URLToJob, htmlParser.getPerson(), htmlParser.getPhone(), htmlParser.getEmail(), null, null, null);
    }

}
