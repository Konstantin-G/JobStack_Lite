package cz.garkusha.jobstack.util;

import cz.garkusha.jobstack.model.Position;
import cz.garkusha.jobstack.view.Dialogs;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for communication with database.
 *
 * @author Konstantin Garkusha
 */
public class DBCommunication {
    private final DerbyDBManager db;
    private final ObservableList<Position> positions;

    public DBCommunication(ObservableList<Position> positions) {

        // clear temp directory
        DeletePositionsPDF.clearTempDirectory(new File(Path.getProgramTempFolder()));

        // unZip database from JobStack *.dat file
        ZipDB.unCompression();

        this.db = new DerbyDBManager();
        this.positions = positions;
        try {
            try {
                fillPositionsFromDB();
            } catch (SQLException ignore) {
                // exception when we haven't the database, or can't find her
               db.executeUpdate("CREATE TABLE stepOne (" +
                  /*( 1)*/     " id INTEGER NOT NULL PRIMARY KEY," +
                  /*( 2)*/     " result VARCHAR(20) DEFAULT NULL," +
                  /*( 3)*/     " company VARCHAR(100) NOT NULL," +
                  /*( 4)*/     " jobTitle VARCHAR(100) NOT NULL," +
                  /*( 5)*/     " jobTitlePDF VARCHAR(500) NOT NULL," +
                  /*( 6)*/     " location VARCHAR(200) NOT NULL," +
                  /*( 7)*/     " web VARCHAR(200) NOT NULL," +
                  /*( 8)*/     " person VARCHAR(40) DEFAULT NULL," +
                  /*( 9)*/     " phone VARCHAR(40) DEFAULT NULL," +
                  /*(10)*/     " email VARCHAR(100) DEFAULT NULL," +
                  /*(11)*/     " requestSent VARCHAR(20) DEFAULT NULL," +
                  /*(12)*/     " answer VARCHAR(20) DEFAULT NULL," +
                  /*(13)*/     " conversation LONG VARCHAR DEFAULT NULL)" +
                  /*(14)*/     " country VARCHAR(30) NOT NULL)"
               );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Dialogs.exceptionDialog(e);
        }
    }

    private void fillPositionsFromDB() throws SQLException {
        ResultSet rs = db.executeQuery("SELECT * FROM stepOne");
        while (rs.next()) {
            positions.add(new Position(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),  rs.getString(5),
                    rs.getString(6), rs.getString(7),  rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13).replaceAll("''","'"), rs.getString(14)));
        }
    }

    public void writePositionsToDB(){
        try {
            db.executeUpdate("DELETE FROM stepOne");
            System.out.println("DB was cleared");
            for (Position p : positions){
                String id               = String.valueOf(p.getId());
                String result           = p.getResult() != null ? p.getResult() : "";
                String company          = p.getCompany();
                String jobTitle         = p.getJobTitle();
                String jobTitlePDF      = p.getJobTitlePDF();
                String location         = p.getLocation();
                String web              = p.getWeb();
                String person           = p.getPerson() != null ? p.getPerson() : "";
                String phone            = p.getPhone() != null ? p.getPhone() : "";
                String email            = p.getEmail() != null ? p.getEmail() : "";
                String requestSentDate  = DateUtil.toString(p.getRequestSentDate()) != null ? DateUtil.toString(p.getRequestSentDate()) : "";
                String answerDate       = DateUtil.toString(p.getAnswerDate()) != null ? DateUtil.toString(p.getAnswerDate()) : "";
                String conversation     = p.getConversation() != null ? p.getConversation().replaceAll("'", "''") : ""; // replaceAll("'", "''") need to escapes ' in sql query
                String country          = p.getCountry() != null ? p.getCountry() : "";

                String newRowQuery = String.format("INSERT INTO stepOne VALUES (%s, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                        id, result, company, jobTitle, jobTitlePDF, location, web, person, phone, email, requestSentDate, answerDate, conversation, country);
                db.executeUpdate(newRowQuery);
            }
            System.out.println("Information was saved to DB");

        } catch (SQLException e) {
            e.printStackTrace();
            Dialogs.exceptionDialog(e);
        }
    }
}
