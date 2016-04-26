package cz.garkusha.jobstack.model;

import cz.garkusha.jobstack.util.HTMLParser;

/**
 * Model class for new Position.
 *
 * @author Konstantin Garkusha
 */
public class PositionFactory {

    public static Position getNewPosition(int id, String URLToJob, String country){
        HTMLParser htmlParser = new HTMLParser(URLToJob, country);

        // id - have as a parameter
        String result           = "";
        String company          = htmlParser.getCompany();
        String jobTitle         = htmlParser.getJobTitle();
        String html             = htmlParser.getHtml();
        String location         = htmlParser.getLocation();
        // web - have as a parameter
        String person           = null != htmlParser.getPerson() ? htmlParser.getPerson(): "";
        String phone            = null != htmlParser.getPhone()  ? htmlParser.getPhone():  "";
        String email            = null != htmlParser.getEmail()  ? htmlParser.getEmail():  "";
        String requestSentDate  = null;
        String answerDate       = null;
        String conversation     = "";
        // country - have as a parameter

        return new Position(id , result, company, jobTitle, html,location, URLToJob, person, phone, email, requestSentDate, answerDate, conversation, country);
    }
}
