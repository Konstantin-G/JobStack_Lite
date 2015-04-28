package cz.garkusha.jobstack.util;

import cz.garkusha.jobstack.view.Dialogs;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Model class for parsing a html file from url.
 *
 * +--------------------------------------------------------------------+
 * |  Working ONLY with:                                                |
 * |                        PRACE.CZ                                    |
 * |                        JOBDNES.CZ                                  |
 * |      mobile version of JOBS.CZ (m.jobs.cz)                         |
 * +--------------------------------------------------------------------+
 * @author Konstantin Garkusha
 */
public class HTMLParser {
    private static final int STATE_CODE = 420;
    private String company;
    private String jobTitle;
    private String location;
    private String person;
    private String phone;
    private String email;

    public HTMLParser(String url, String country){
        readInformationFromWeb(url, country);
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

    private void readInformationFromWeb(String url, String country) {
        /*TODO */
        switch (country){
            case "Ukraine":  System.out.println("Ukraine");
                break;
            case "Russia": System.out.println("Russia");
                break;
            case "Czech": System.out.println("Czech");
                break;
            default: System.out.println("none");
        }
        if (url.contains("m.jobs.cz")) {
            readInformationFrom_M_JODS_CZ(url);
        } else if (url.contains("prace.cz")) {
            readInformationFrom_PRACE_CZ(url);
        }else if (url.contains("jobdnes.cz")) {
            readInformationFrom_JOBDNES_CZ(url);
        }
    }

    private void readInformationFrom_M_JODS_CZ(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
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
            this.phone = phoneFormat(phone);
        } catch (Exception ignore) { }
        try {
            //noinspection ConstantConditions
            this.email = doc.body().select("p.c2-em").first().text().trim();
        } catch (Exception ignore) { }
    }

    private void readInformationFrom_PRACE_CZ(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
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
            String h3_g2dname = doc.body().select("h2#g2d-name").first().text();
            String h3_g2dname_strong = doc.body().select("h2#g2d-name").first().select("strong").text();
            this.jobTitle = h3_g2dname.replace(h3_g2dname_strong, "");
            this.jobTitle = jobTitle.replaceAll("[\\/]", "-").replaceAll("[*?<>|:\"]", "").replaceAll("[ ]{2,}", " ").trim();
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
            this.phone = phoneFormat(phone);
        } catch (Exception ignore) { }
        try {
            //noinspection ConstantConditions
            this.email = doc.body().select("p.c2-em").first().text().trim();
        } catch (Exception ignore) { }
    }

    private void readInformationFrom_JOBDNES_CZ(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            Dialogs.connectionError();
        }
        try {
            //noinspection ConstantConditions
            this.company = doc.body().select("div.ds-left-box").select("h2#employer-name").first().text()
                    .replaceAll("[\\/]", "-").replaceAll("[*?<>|:\"]", "").replaceAll("[ ]{2,}"," ").trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions

            this.jobTitle = doc.body().select("div.ds-left-box").select("h1").first().text()
                    .replaceAll("[\\/]", "-").replaceAll("[*?<>|:\"]", "").replaceAll("[ ]{2,}"," ").trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            /*TODO location for jobdnes.cz*/
            /*this.location = doc.body().select("p.c2-mi").first().text().trim();*/
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            String personInformation = doc.body().select("div#contact.ds-address").select("p").first().text();
            String[] personInformationArray = personInformation.split(",");
            StringBuilder phoneBuilder = new StringBuilder("");
            if (personInformationArray.length > 0) {
                this.person = personInformationArray[0].trim();
            }
            if (personInformationArray.length > 1) {
                this.email = personInformationArray[1].trim();
            }
            for (int i = 2; i < personInformationArray.length; i++) {
                phoneBuilder.append(personInformationArray[i]);
                if (i !=personInformationArray.length - 1) {
                    phoneBuilder.append(", ");
                }
            }
            this.phone = phoneFormat(phoneBuilder.toString());
        } catch (Exception ignore) { }
    }

    private String phoneFormat(String phones){
        if (phones == null || phones.length() == 0) {
            return "";
        }
        String[] phoneArray = phones.split(",");
        StringBuilder phoneBuilder = new StringBuilder("");
        MessageFormat phoneMsgFmt=new java.text.MessageFormat("+{0} {1} {2} {3}");
        for (int i = 0; i < phoneArray.length; i++) {
            phoneArray[i] = phoneArray[i].replaceAll("[\\D]", "");
            if (phoneArray[i].length() == 9) {
                phoneArray[i] = STATE_CODE + phoneArray[i];
            }
            String[] phoneNumArr={phoneArray[i].substring(0, 3), phoneArray[i].substring(3, 6), phoneArray[i].substring(6, 9), phoneArray[i].substring(9)};
            phoneArray[i] = phoneMsgFmt.format(phoneNumArr);
            phoneBuilder.append(phoneArray[i]);
            if (i !=phoneArray.length - 1) {
                phoneBuilder.append(", ");
            }
        }
        return phoneBuilder.toString();
    }
}
