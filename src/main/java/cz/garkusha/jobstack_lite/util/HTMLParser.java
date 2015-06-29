package cz.garkusha.jobstack_lite.util;

import cz.garkusha.jobstack_lite.controller.Dialogs;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Model class for parsing a html file from url.
 * problem with monster.cz, cant parse this web
 * +--------------------------------------------------------------------+
 * |  Working ONLY with:                                                |
 * |      Czech Republic->                                              |
 * |                        PRACE.CZ                                    |
 * |                        JOBDNES.CZ                                  |
 * |                        STARTUPJOBS.CZ                              |
 * |      mobile version of JOBS.CZ (m.jobs.cz)                         |
 * |                                                                    |
 * |        Russia->        SUPERJOB.RU                                 |
 * |                        RABOTA.RU                                   |
 * |                        CAREERIST.RU                                |
 * |                        BRAINSTORAGE.ME                             |
 * |                                                                    |
 * |        Ukraine->       HH_UA                                       |
 * |      mobile version of RABOTA.UA (RABOTA.UA/mobile/)               |
 * |                        JOBS.DOU.UA                                 |
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
    private String html;

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

    public String getHtml() {
        return html;
    }

    private void readInformationFromWeb(String url, String country) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            this.html = doc.html();
        } catch (IOException e) {
            e.printStackTrace();
            Dialogs.connectionError();
        }

        switch (country) {
            case "Czech":
                if (url.contains("m.jobs.cz")) {
                    readInformationFrom_M_JODS_CZ(doc);
                } else if (url.contains("prace.cz")) {
                    readInformationFrom_PRACE_CZ(doc);
                } else if (url.contains("jobdnes.cz")) {
                    readInformationFrom_JOBDNES_CZ(doc);
                }else if (url.contains("startupjobs.cz")) {
                    readInformationFrom_STARTUPJOBS_CZ(doc);
                }
                break;
            case "Russia":
                if (url.contains("superjob.ru")) {
                    readInformationFrom_SUPERJOB_RU(doc);
                } else if (url.contains("rabota.ru")) {
                    readInformationFrom_RABOTA_RU(doc);
                } else if (url.contains("careerist.ru")) {
                    readInformationFrom_CAREERIST_RU(doc);
                } else if (url.contains("brainstorage.me")) {
                    readInformationFrom_BRAINSTORAGE_ME(doc);
                }
                break;
            case "Ukraine":
                if (url.contains("jobs.dou.ua")) {
                    readInformationFrom_JOBS_DOU_UA(doc);
                } else if (url.contains("hh.ua")) {
                    readInformationFrom_HH_UA(doc);
                } else if (url.contains("rabota.ua/mobile")) {
                    readInformationFrom_RABOTA_UA(doc);
                }
                break;
            case "USA":
                if (url.contains("dice.com")) {
                    readInformationFrom_DICE_COM(doc);
                } else if (url.contains("careerbuilder.com")) {
                    readInformationFrom_CAREERBUILDER_COM(doc);
                } else if (url.contains("monster.com")) {
                    readInformationFrom_MONSTER_COM(doc);
                } else if (url.contains("indeed.com")) {
                    readInformationFrom_INDEED_COM(doc);
                }
                break;
            default:
                System.out.println("none");
        }

    }

    /** Czech Republic*/
    private void readInformationFrom_M_JODS_CZ(Document doc) {
        try {
            //noinspection ConstantConditions
            this.company = doc.body().select("p.c2-za").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.jobTitle = doc.body().select("h1.job-title").first().text().trim();
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

    private void readInformationFrom_PRACE_CZ(Document doc) {
        try {
            //noinspection ConstantConditions
            this.company = doc.body().select("p.c2-za").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            String h3_g2dname = doc.body().select("h2#g2d-name").first().text();
            String h3_g2dname_strong = doc.body().select("h2#g2d-name").first().select("strong").text();
            this.jobTitle = h3_g2dname.replace(h3_g2dname_strong, "").trim();
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

    private void readInformationFrom_JOBDNES_CZ(Document doc) {
        try {
            //noinspection ConstantConditions
            this.company = doc.body().select("div.ds-left-box").select("h2#employer-name").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.jobTitle = doc.body().select("div.ds-left-box").select("h1").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            Elements elements = doc.body().select("div.w2-a.text").first().getElementsByTag("tr");

            Map<String, String> mapOfData = new HashMap<>();
            for (Element e : elements){
                mapOfData.put(e.getElementsByTag("th").first().text(), e.getElementsByTag("td").first().text());
            }

            for (Map.Entry<String, String> pair : mapOfData.entrySet()) {
                if (pair.getKey().contains("Místo pracoviště") || pair.getKey().contains("Region")) {
                    this.location = pair.getValue().trim();
                }
            }
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

    private void readInformationFrom_STARTUPJOBS_CZ(Document doc) {
        try {
            //noinspection ConstantConditions
            this.company = doc.body().select("span.startupName.mobile-only").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            String positionCol = doc.body().select("td.positionCol").first().select("h1").text();
            String positionCol_span = doc.body().select("td.positionCol").first().tagName("h1").select("span.desktop-only.new-sticker").text();
            this.jobTitle = positionCol.replace(positionCol_span, "").trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.location = doc.body().select("div.details.center > div").first().text().trim();
        } catch (NullPointerException ignore) { }
    }


    /**Russia*/
    private void readInformationFrom_SUPERJOB_RU(Document doc) {
        try {
            //noinspection ConstantConditions
            this.company = doc.body().select("a.h_color_gray_dk").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.jobTitle = doc.body().select("h1.VacancyView_title.h_color_gray_dk").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.location = doc.body().select("span.h_color_gray.VacancyView_town").first().text().trim();
        } catch (NullPointerException ignore) { }
    }

    private void readInformationFrom_RABOTA_RU(Document doc) {
        try {
            //noinspection ConstantConditions
            this.company = doc.body().select("a.bold.text_16.no_decore").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.jobTitle = doc.body().select("p.title_30.vac-title").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.location = doc.body().select("div.list_kval.pd_h").first().select("ul").last().text();
            this.location = location.substring(0, location.indexOf(32));
        } catch (NullPointerException ignore) { }
    }

    private void readInformationFrom_BRAINSTORAGE_ME(Document doc) {
        try {
            //noinspection ConstantConditions
            this.company = doc.body().select("div.company_name").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.jobTitle = doc.body().select("div.title").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.location = doc.body().select("div.location").first().text();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.person = doc.body().select("div.comContact").first().getElementsByTag("span").get(1).text();
        } catch (Exception ignore) { }
        try {
            //noinspection ConstantConditions
            this.phone = doc.body().select("div.contact").first().text();
//            this.phone = phoneFormat(phone);
        } catch (Exception ignore) { }
        try {
            //noinspection ConstantConditions
            this.email = doc.body().select("p.c2-em").first().text().trim();
        } catch (Exception ignore) { }
    }

    private void readInformationFrom_CAREERIST_RU(Document doc) {
        try {
            //noinspection ConstantConditions
            this.company = doc.body().select("div.comContact").first().getElementsByTag("span").get(0).text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.jobTitle = doc.body().select("div.vacancyPageHeaderNew").first().getElementsByTag("h1").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.location = doc.body().select("div.vacancyPageHeaderNewRight").first().select("span.searchingtext").first().text();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.person = doc.body().select("div.comContact").first().getElementsByTag("span").get(1).text();
        } catch (Exception ignore) { }
        try {
            //noinspection ConstantConditions
            this.phone  = doc.body().select("div.comContact").first().getElementsByTag("span").get(2).text();
//            this.phone = phoneFormat(phone);
        } catch (Exception ignore) { }
        try {
            //noinspection ConstantConditions
            this.email = doc.body().select("p.c2-em").first().text().trim();
        } catch (Exception ignore) { }
    }

    /**Ukraine*/
    private void readInformationFrom_RABOTA_UA(Document doc) {
        try {
            //noinspection ConstantConditions
            this.company = doc.body().select("p.loud").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.jobTitle = doc.body().select("h1.title").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.location = doc.body().select("span[itemprop = addressLocality]").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            Elements elements = doc.body().select("ul.unstyled-list").first().select("li.col-50.mob-100");
            Map<String, String> mapOfData = new HashMap<>();
            for (Element e : elements){
                mapOfData.put(e.select("b.col-100.mob-50.muted").first().text(), e.getElementsByTag("span").first().text());
            }

            for (Map.Entry<String, String> pair : mapOfData.entrySet()) {
                if (pair.getKey().contains("Контактное лицо")) {
                    if (pair.getValue().contains("@")) {
                        this.email = pair.getValue().trim();
                    } else
                        this.person = pair.getValue().trim();
                }
            }
        } catch (Exception ignore) { }
    }

    private void readInformationFrom_HH_UA(Document doc) {
        try {
            //noinspection ConstantConditions
            String cut = doc.body().select("div.navigate__hint").first().text().trim();
            this.company = doc.body().select("div.navigate.navigate_nopadding").first().text().replace(cut, "").trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.jobTitle = doc.body().select("h1.vacancy__name").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.location = doc.body().select("div.vacancy__address").first().getElementsByTag("span").first().text().trim();
        } catch (NullPointerException ignore) { }
    }

    private void readInformationFrom_JOBS_DOU_UA(Document doc) {
        try {
            //noinspection ConstantConditions
            this.company = doc.body().select("div.l-n").first().getElementsByTag("a").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.jobTitle = doc.body().select("h1.g-h2").first().text().trim();
        } catch (NullPointerException ignore) { }
        try {
            //noinspection ConstantConditions
            this.location = doc.body().select("span.place").first().text().trim();
        } catch (NullPointerException ignore) { }
    }

    /**USA*/
    private void readInformationFrom_DICE_COM(Document doc){}
    private void readInformationFrom_CAREERBUILDER_COM(Document doc){}
    private void readInformationFrom_MONSTER_COM(Document doc){}
    private void readInformationFrom_INDEED_COM(Document doc){}

    private String phoneFormat(String phones){
        if (phones == null || phones.length() == 0) {
            return "";
        }
        String[] phoneArray = phones.split(",");
        StringBuilder phoneBuilder = new StringBuilder("");
        MessageFormat phoneMsgFmt = new MessageFormat("+{0} {1} {2} {3}");
        for (int i = 0; i < phoneArray.length; i++) {
            phoneArray[i] = phoneArray[i].replaceAll("[\\D]", "");
            // if phone without state code, like this 123 456 789
            if (phoneArray[i].length() == 9) {
                phoneArray[i] = STATE_CODE + phoneArray[i];
            }
            // if phone with state code and 00 instead + , like this 00420 123 456 789
            if (phoneArray[i].length() == 14) {
                phoneArray[i] = phoneArray[i].substring(2);
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
