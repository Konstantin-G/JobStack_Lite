package cz.garkusha.jobsstack.util;

import cz.garkusha.jobsstack.model.Position;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for communication with database.
 *
 * @author Konstantin Garkusha
 */
public class DBCommunication {
    private static final String DBName = "seekingJob2015";
    public static String getDBName() {
        return DBName;
    }

    private DerbyDBManager db;
    private ObservableList<Position> positions;

    public DBCommunication(ObservableList<Position> positions) {
        this.db = new DerbyDBManager(DBName);
        this.positions = positions;
        try {
            try {
                fillPositionsFromDB();
            } catch (SQLException ignore) {
                // exception when we haven't data base, or can't find her
               db.executeUpdate("CREATE TABLE stepOne (" +
                               " id INTEGER NOT NULL PRIMARY KEY," +
                               " result VARCHAR(20) DEFAULT NULL," +
                               " company VARCHAR(100) NOT NULL," +
                               " jobTitle VARCHAR(100) NOT NULL," +
                               " jobTitlePDF VARCHAR(500) NOT NULL," +
                               " location VARCHAR(200) NOT NULL," +
                               " web VARCHAR(200) NOT NULL," +
                               " person VARCHAR(40) DEFAULT NULL," +
                               " requestSent VARCHAR(20) DEFAULT NULL," +
                               " answer VARCHAR(20) DEFAULT NULL," +
                               " conversation LONG VARCHAR DEFAULT NULL)"
               );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillPositionsFromDB() throws SQLException {
        db = new DerbyDBManager(DBName);
        ResultSet rs = db.executeQuery("SELECT * FROM stepOne");
        while (rs.next()) {
            positions.add(new Position(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),  rs.getString(5),
                    rs.getString(6), rs.getString(7),  rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)));
        }
    }

    public void writePositionsToDB(){
        try {
            db.executeUpdate("DELETE FROM stepOne");
            for (Position p : positions){
                String id               = String.valueOf(p.getId());
                String result           = p.getResult() != null ? p.getResult() : "";
                String company          = p.getCompany();
                String jobTitle         = p.getJobTitle();
                String jobTitlePDF      = p.getJobTitlePDF();
                String location         = p.getLocation();
                String web              = p.getWeb();
                String person           = p.getPerson() != null ? p.getPerson() : "";
                String requestSentDate  = DateUtil.toString(p.getRequestSentDate()) != null ? DateUtil.toString(p.getRequestSentDate()) : "";
                String answerDate       = DateUtil.toString(p.getAnswerDate()) != null ? DateUtil.toString(p.getAnswerDate()) : "";
                String conversation     = p.getConversation() != null ? p.getConversation() : "";

                String newRowQuery = String.format("INSERT INTO stepOne VALUES (%s, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                                                    id, result, company, jobTitle, jobTitlePDF, location, web, person, requestSentDate, answerDate, conversation);
                db.executeUpdate(newRowQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
