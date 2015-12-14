package cz.garkusha.jobstack_lite.model;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;

import cz.garkusha.jobstack_lite.util.DateUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.metamodel.Type;
import javax.sql.rowset.serial.SerialBlob;

/**
 * Model class for a Position.
 *
 * @author Konstantin Garkusha
 */

@Entity
@Table(name = "STEP_ONE")
public class Position implements Serializable{

    private static final long serialVersionUID = -5527566248002296042L;

    private int id;
    private String result;
    private String company;
    private String jobTitle;
    private Blob html;
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

    public Position(int id, String result, String company, String jobTitle, Object html,
                    String location, String web, String person, String phone, String email, String requestSentDate,
                    String answerDate, String conversation, String country) {

        this.id                 = id;
        this.result             = result;
        this.company            = company;
        this.jobTitle           = jobTitle;

        // Blob if we're creating position from database, and String - from Position factory
        Blob blob = null;
        if (html instanceof Blob) {
            blob = (Blob) html;
        } else if (html instanceof String) {
            try {
                blob = new SerialBlob( ((String)html).getBytes(Charset.forName("UTF-8")));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        this.html               = blob;

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

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 5, scale = 0)
    public int getId() {
        return id;
    }

    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "RESULT", nullable = true, length = 20)
    public String getResult() {
        return result;
    }

    public StringProperty resultProperty() {
        return new SimpleStringProperty(result);
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Column(name = "COMPANY", nullable = false, length = 100)
    public String getCompany() {
        return company;
    }

    public StringProperty companyProperty() {
        return new SimpleStringProperty(company);
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "JOB_TITLE", nullable = false, length = 100)
    public String getJobTitle() {
        return jobTitle;
    }

    public StringProperty jobTitleProperty() {
        return new SimpleStringProperty(jobTitle);
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Lob
    @Column(name = "HTML", nullable = false)
    public Blob getHtml() {
        return this.html;
    }

    public void setHtml(Blob html) {
        this.html = html;
    }
    public String htmlAsString() {
        String s = "";
        try {
            byte[] htmlArray = this.html.getBytes(1, (int) this.html.length());
            s = new String(htmlArray, Charset.forName("UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    public StringProperty htmlProperty() {
        String s = "";
        try {
            byte[] htmlArray = this.html.getBytes(1, (int) this.html.length());
            s = new String(htmlArray, Charset.forName("UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new SimpleStringProperty(s);
    }

    public void Html(String html) {
        Blob blob = null;
        try {
            blob = new SerialBlob( html.getBytes(Charset.forName("UTF-8")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.html = blob;
    }


    @Column(name = "LOCATION", nullable = false, length = 200)
    public String getLocation() {
        return location;
    }

    public StringProperty locationProperty() {
        return new SimpleStringProperty(location);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "WEB", nullable = false, length = 200)
    public String getWeb() {
        return web;
    }

    public StringProperty webProperty() {
        return new SimpleStringProperty(web);
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Column(name = "PERSON", nullable = true, length = 40)
    public String getPerson() {
        return person;
    }

    public StringProperty personProperty() {
        return new SimpleStringProperty(person);
    }

    public void setPerson(String person) {
        this.person = person;
    }

    @Column(name = "PHONE", nullable = true, length = 100)
    public String getPhone() {
        return phone;
    }

    public StringProperty phoneProperty() {
        return new SimpleStringProperty(phone);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "EMAIL", nullable = true, length = 100)
    public String getEmail() {
        return email;
    }

    public StringProperty emailProperty() {
        return new SimpleStringProperty(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "REQUEST_SENT", nullable = true, length = 20)
    public LocalDate getRequestSentDate() {
        return requestSentDate;
    }

    public ObjectProperty<LocalDate> requestSentDateProperty() {
        return new SimpleObjectProperty<>(requestSentDate);
    }

    public void setRequestSentDate(LocalDate requestSentDate) {
        this.requestSentDate = requestSentDate;
    }

    @Column(name = "ANSWER", nullable = true, length = 20)
    public LocalDate getAnswerDate() {
        return answerDate;
    }

    public ObjectProperty<LocalDate> answerDateProperty() {
        return new SimpleObjectProperty<>(answerDate);
    }

    public void setAnswerDate(LocalDate answerDate) {
        this.answerDate = answerDate;
    }

    @Column(name = "CONVERSATION", nullable = true, length = 20000)
    public String getConversation() {
        return conversation;
    }

    public StringProperty conversationProperty() {
        return new SimpleStringProperty(conversation);
    }

    public void setConversation(String conversation) {
        this.conversation = conversation;
    }

    @Column(name = "COUNTRY", nullable = false, length = 30)
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
