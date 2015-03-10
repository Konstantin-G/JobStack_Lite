package cz.garkusha.jobsstack.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Model class for parsing a html file from url.
 *
 * @author Konstantin Garkusha
 */
public class HTMLParser {
    private String company;
    private String jobTitle;
    private String location;
    private String person;
    private String title;

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

    private void readInformationFromWeb(String url) {
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
        } catch (NullPointerException ignore) {
            System.out.println("input company name manually");
        }
        try {
            //noinspection ConstantConditions
            this.jobTitle = doc.body().select("h1.job-title").first().text();
        } catch (NullPointerException ignore) {
            System.out.println("input job title manually");
        }
        try {
            //noinspection ConstantConditions
            this.location = doc.body().select("p.c2-mi").first().text();
        } catch (NullPointerException ignore) {
            System.out.println("input location manually");
        }
        try {
            //noinspection ConstantConditions
            this.person = doc.body().select("p.c2-ko").first().text();
        } catch (Exception ignore) {
            System.out.println("input person");
        }
    }
}
