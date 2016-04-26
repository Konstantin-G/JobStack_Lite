package cz.garkusha.jobstack.model;

import cz.garkusha.jobstack.util.DateUtil;
import cz.garkusha.jobstack.util.LocalDateAdapter;
import javafx.beans.property.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Model class for a Position.
 *
 * @author Konstantin Garkusha
 */


public class Position implements Serializable{

    private static final long serialVersionUID = -5527566248002296042L;

    private int id;
    private String result;
    private String company;
    private String jobTitle;
    private String html;
    private String location;
    private String web;
    private String person;
    private String phone;
    private String email;
    private LocalDate requestSentDate;
    private LocalDate answerDate;
    private String conversation;
    private String country;

    public Position() {
        this(0, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Position(int id, String result, String company, String jobTitle, String html,
                    String location, String web, String person, String phone, String email, String requestSentDate,
                    String answerDate, String conversation, String country) {

        this.id                 = id;
        this.result             = result;
        this.company            = company;
        this.jobTitle           = jobTitle;
        this.html               = html;
        this.location           = location;
        this.web                =  web;
        this.person             = person;
        this.phone              = phone;
        this.email              = email;
        this.requestSentDate    = DateUtil.fromString(requestSentDate);
        this.answerDate         = DateUtil.fromString(answerDate);
        this.conversation       = conversation;
        this.country            = country;
    }


    public int getId() {
        return id;
    }

    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public StringProperty resultProperty() {
        return new SimpleStringProperty(result);
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCompany() {
        return company;
    }

    public StringProperty companyProperty() {
        return new SimpleStringProperty(company);
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public StringProperty jobTitleProperty() {
        return new SimpleStringProperty(jobTitle);
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getHtml() {
        return html;
    }

    public StringProperty HTMLProperty() {
        return new SimpleStringProperty(html);
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getLocation() {
        return location;
    }

    public StringProperty locationProperty() {
        return new SimpleStringProperty(location);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeb() {
        return web;
    }

    public StringProperty webProperty() {
        int start = web.indexOf("http://");
        if (start != -1) {
            start += 7;
            start = web.contains("www")? start + 4 : start;
        } else {
            start = web.indexOf("https://");
            if (start != -1) {
                start += 8;
                start = web.contains("www")? start + 4 : start;
            } else start = 0;
        }

        int end = web.indexOf("/", 14);
        return new SimpleStringProperty(web.substring(start, end));
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPerson() {
        return person;
    }

    public StringProperty personProperty() {
        return new SimpleStringProperty(person);
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPhone() {
        return phone;
    }

    public StringProperty phoneProperty() {
        return new SimpleStringProperty(phone);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public StringProperty emailProperty() {
        return new SimpleStringProperty(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getRequestSentDate() {
        return requestSentDate;
    }

    public ObjectProperty<LocalDate> requestSentDateProperty() {
        return new SimpleObjectProperty<>(requestSentDate);
    }

    public void setRequestSentDate(LocalDate requestSentDate) {
        this.requestSentDate = requestSentDate;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getAnswerDate() {
        return answerDate;
    }

    public ObjectProperty<LocalDate> answerDateProperty() {
        return new SimpleObjectProperty<>(answerDate);
    }

    public void setAnswerDate(LocalDate answerDate) {
        this.answerDate = answerDate;
    }

    public String getConversation() {
        return conversation;
    }

    public StringProperty conversationProperty() {
        return new SimpleStringProperty(conversation);
    }

    public void setConversation(String conversation) {
        this.conversation = conversation;
    }

    public String getCountry() {
        return country;
    }

    public StringProperty countryProperty() {
        return new SimpleStringProperty(country);
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
