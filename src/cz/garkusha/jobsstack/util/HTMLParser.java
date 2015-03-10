package cz.garkusha.jobsstack.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Model class for parsing a html file from url.
 *
 * +--------------------------------------------------------------------+
 * |  For while working ONLY with mobile version of JOBS.CZ (m.jobs.cz) |
 * +--------------------------------------------------------------------+
 * @author Konstantin Garkusha
 */
public class HTMLParser {
    private String company;
    private String jobTitle;
    private String location;
    private String person;
    private String title;
    private String phone;
    private String email;

    public HTMLParser(String url){
        readInformationFromWeb(url);
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public String getPerson() {
        return person;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    private void readInformationFromWeb(String url) {
        readInformationFromM_JODS_CZ(url);
    }

    private void readInformationFromM_JODS_CZ(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            this.title = doc.title();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //noinspection ConstantConditions
            this.company = doc.body().select("p.c2-za").first().text();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.jobTitle = doc.body().select("h1.job-title").first().text();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.location = doc.body().select("p.c2-mi").first().text();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.person = doc.body().select("p.c2-ko").first().text();
        } catch (Exception ignore) { }
        try {
            //noinspection ConstantConditions
            this.phone = doc.body().select("p.c2-te").first().text();
        } catch (Exception ignore) { }
        try {
            //noinspection ConstantConditions
            this.email = doc.body().select("p.c2-em").first().text();
        } catch (Exception ignore) { }
    }
}
