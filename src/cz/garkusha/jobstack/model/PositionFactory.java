package cz.garkusha.jobstack.model;

import cz.garkusha.jobstack.util.HTMLParser;
import cz.garkusha.jobstack.util.Path;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Model class for new Position.
 *
 * @author Konstantin Garkusha
 */
public class PositionFactory {

    public static Position getNewPosition(int id, String URLToJob){
        HTMLParser htmlParser = new HTMLParser(URLToJob);

        // id - have as parameter
        String result           = "";
        String company          = htmlParser.getCompany();
        String jobTitle         = htmlParser.getJobTitle();
        String jobTitlePDF      = Path.getRelativeJobDescriptionPath() + getPDFFileName(company, jobTitle);
        String location         = htmlParser.getLocation();
        // web - have as parameter
        String person           = null != htmlParser.getPerson() ? htmlParser.getPerson(): "";
        String phone            = null != htmlParser.getPhone()  ? htmlParser.getPhone():  "";
        String email            = null != htmlParser.getEmail()  ? htmlParser.getEmail():  "";
        String requestSentDate  = null;
        String answerDate       = null;
        String conversation     = "";

        return new Position(id , result, company, jobTitle, jobTitlePDF,location, URLToJob, person, phone, email, requestSentDate, answerDate, conversation);
    }

    public static String getPDFFileName( String company, String jobTitle) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
/*        if (null == company)
            company = "companyName";
        if (null == jobTitle)
            company = "jobTitle";*/
        return simpleDateFormat.format(new Date()) + "_" + company + "_" + jobTitle + ".pdf";
    }

}
