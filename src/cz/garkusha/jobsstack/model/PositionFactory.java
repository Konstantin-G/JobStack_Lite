package cz.garkusha.jobsstack.model;

import cz.garkusha.jobsstack.MainApp;
import cz.garkusha.jobsstack.util.HTMLParser;
import cz.garkusha.jobsstack.util.PDFConverter;

/**
 * Model class for new Position.
 *
 * @author Konstantin Garkusha
 */
public class PositionFactory {

    private static int getPositionsSize(){
        return MainApp.getListSize();
    }

    public static Position getNewPosition(String URLToJob){
        HTMLParser htmlParser = new HTMLParser(URLToJob);
        PDFConverter pdfConverter = new PDFConverter(URLToJob, htmlParser.getTitle());
        int id = getPositionsSize() + 1;
        return new Position(id , null, htmlParser.getCompany(), htmlParser.getJobTitle(), pdfConverter.getRelativeReferenceToJobPDF(),
                                htmlParser.getLocation(), URLToJob, htmlParser.getPerson(), htmlParser.getPhone(), htmlParser.getEmail(), null, null, null);
    }

}
