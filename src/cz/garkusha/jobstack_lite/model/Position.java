package cz.garkusha.jobstack_lite.model;

import java.time.LocalDate;

import cz.garkusha.jobstack_lite.util.DateUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Position.
 *
 * @author Konstantin Garkusha
 */
public class Position {

    private final IntegerProperty id;
    private final StringProperty result;
    private final StringProperty company;
    private final StringProperty jobTitle;
    private final StringProperty html;
    private final StringProperty location;
    private final StringProperty web;
    private final StringProperty person;
    private final StringProperty phone;
    private final StringProperty email;
    private final ObjectProperty<LocalDate> requestSentDate;
    private final ObjectProperty<LocalDate> answerDate;
    private final StringProperty conversation;
    private final StringProperty country;

    public Position() {
        this(0, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Position(int id, String result, String company, String jobTitle, String html,
                    String location, String web, String person, String phone, String email, String requestSentDate,
                    String answerDate, String conversation, String country) {

        this.id                 = new SimpleIntegerProperty(id);
        this.result             = new SimpleStringProperty(result);
        this.company            = new SimpleStringProperty(company);
        this.jobTitle           = new SimpleStringProperty(jobTitle);
        this.html               = new SimpleStringProperty(html);
        this.location           = new SimpleStringProperty(location);
        this.web                = new SimpleStringProperty(web);
        this.person             = new SimpleStringProperty(person);
        this.phone              = new SimpleStringProperty(phone);
        this.email              = new SimpleStringProperty(email);
        this.requestSentDate    = requestSentDate != null ? new SimpleObjectProperty<>(DateUtil.fromString(requestSentDate))
                                                            : new SimpleObjectProperty<>();
        this.answerDate         = requestSentDate != null ? new SimpleObjectProperty<>(DateUtil.fromString(answerDate))
                                                            : new SimpleObjectProperty<>();
        this.conversation       = new SimpleStringProperty(conversation);
        this.country            = new SimpleStringProperty(country);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getResult() {
        return result.get();
    }

    public StringProperty resultProperty() {
        return result;
    }

    public void setResult(String result) {
        this.result.set(result);
    }

    public String getCompany() {
        return company.get();
    }

    public StringProperty companyProperty() {
        return company;
    }

    public void setCompany(String company) {
        this.company.set(company);
    }

    public String getJobTitle() {
        return jobTitle.get();
    }

    public StringProperty jobTitleProperty() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle.set(jobTitle);
    }

    public String getHtml() {
        return html.get();
    }

    public StringProperty htmlProperty() {
        return html;
    }

    public void setHtml(String html) {
        this.html.set(html);
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getWeb() {
        return web.get();
    }

    public StringProperty webProperty() {
        return web;
    }

    public void setWeb(String web) {
        this.web.set(web);
    }

    public String getPerson() {
        return person.get();
    }

    public StringProperty personProperty() {
        return person;
    }

    public void setPerson(String person) {
        this.person.set(person);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public LocalDate getRequestSentDate() {
        return requestSentDate.get();
    }

    public ObjectProperty<LocalDate> requestSentDateProperty() {
        return requestSentDate;
    }

    public void setRequestSentDate(LocalDate requestSentDate) {
        this.requestSentDate.set(requestSentDate);
    }

    public LocalDate getAnswerDate() {
        return answerDate.get();
    }

    public ObjectProperty<LocalDate> answerDateProperty() {
        return answerDate;
    }

    public void setAnswerDate(LocalDate answerDate) {
        this.answerDate.set(answerDate);
    }

    public String getConversation() {
        return conversation.get();
    }

    public StringProperty conversationProperty() {
        return conversation;
    }

    public void setConversation(String conversation) {
        this.conversation.set(conversation);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }
}
