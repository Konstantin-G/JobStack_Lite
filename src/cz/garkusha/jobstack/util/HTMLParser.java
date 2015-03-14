package cz.garkusha.jobstack.util;

import cz.garkusha.jobstack.view.Dialogs;
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
        readInformationFrom_M_JODS_CZ(url);
    }

    private void readInformationFrom_M_JODS_CZ(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            this.title = doc.title();
        } catch (IOException e) {
            e.printStackTrace();
            Dialogs.connectionError();
        }
        try {
            //noinspection ConstantConditions
            this.company = doc.body().select("p.c2-za").first().text()
                    .replaceAll("[\\/]", "-").replaceAll("[*?<>|:\"]", "").replaceAll("[ ]{2,}"," ").trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.jobTitle = doc.body().select("h1.job-title").first().text()
                    .replaceAll("[\\/]", "-").replaceAll("[*?<>|:\"]", "").replaceAll("[ ]{2,}", " ").trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.location = doc.body().select("p.c2-mi").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.person = doc.body().select("p.c2-ko").first().text().trim();
        } catch (Exception ignore) { }
        try {
            //noinspection ConstantConditions
            this.phone = doc.body().select("p.c2-te").first().text().trim();
        } catch (Exception ignore) { }
        try {
            //noinspection ConstantConditions
            this.email = doc.body().select("p.c2-em").first().text().trim();
        } catch (Exception ignore) { }
    }
}
