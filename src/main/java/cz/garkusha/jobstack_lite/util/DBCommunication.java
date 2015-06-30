package cz.garkusha.jobstack_lite.util;

import cz.garkusha.jobstack_lite.model.Position;
import cz.garkusha.jobstack_lite.controller.Dialogs;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.sql.*;

/**
 * Class for communication with database.
 *
 * @author Konstantin Garkusha
 */
public class DBCommunication {

    private static final Logger LOG = LoggerFactory.getLogger(DBCommunication.class);

    private final DerbyDBManager db;
    private final ObservableList<Position> positions;

    public DBCommunication(ObservableList<Position> positions) {

        // clear temp directory
        FileSystem.clearTempDirectory(new File(Path.getProgramTempFolder()));

        // unZip database from JobStack *.dat file
        ZipDB.unCompression();

        this.db = new DerbyDBManager();
        this.positions = positions;
        try {
            try {
                LOG.info("Read positions from database");
                fillPositionsFromDB();
                LOG.debug("Positions from database was read");
            } catch (SQLException ignore) {
                // exception when we haven't the database, or can't find her
                // Script for Apache Derby DB
               db.executeUpdate("CREATE TABLE stepOne (" +
                  /*( 1)*/     " id INTEGER NOT NULL PRIMARY KEY," +
                  /*( 2)*/     " result VARCHAR(20) DEFAULT NULL," +
                  /*( 3)*/     " company VARCHAR(100) NOT NULL," +
                  /*( 4)*/     " jobTitle VARCHAR(100) NOT NULL," +
                  /*( 5)*/     " location VARCHAR(200) NOT NULL," +
                  /*( 6)*/     " web VARCHAR(200) NOT NULL," +
                  /*( 7)*/     " person VARCHAR(40) DEFAULT NULL," +
                  /*( 8)*/     " phone VARCHAR(40) DEFAULT NULL," +
                  /*( 9)*/     " email VARCHAR(100) DEFAULT NULL," +
                  /*(10)*/     " requestSent VARCHAR(20) DEFAULT NULL," +
                  /*(11)*/     " answer VARCHAR(20) DEFAULT NULL," +
                  /*(12)*/     " conversation LONG VARCHAR DEFAULT NULL," +
                  /*(13)*/     " country VARCHAR(30) NOT NULL," +
                  /*(14)*/     " html BLOB(128 K) NOT NULL)"
               );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Dialogs.exceptionDialog(e);
        }
    }

    private void fillPositionsFromDB() throws SQLException {
        ResultSet rs = db.executeQuery("SELECT * FROM stepOne");
        Blob c;
        String html;
        while (rs.next()) {

            c = rs.getBlob("html");
            html = new String(c.getBytes(1, (int)c.length()), Charset.forName("UTF-8"));

            positions.add(new Position(rs.getInt("id"), rs.getString("result"), rs.getString("company"), rs.getString("jobTitle"),  html,
                    rs.getString("location"), rs.getString("web"),  rs.getString("person"), rs.getString("phone"), rs.getString("email"),
                    rs.getString("requestSent"), rs.getString("answer"), rs.getString("conversation"), rs.getString("country")));
        }
    }

    public void writePositionsToDB(){
        try {
            db.executeUpdate("DELETE FROM stepOne");
            LOG.debug("Database was cleared");
            for (Position p : positions){
                PreparedStatement ps = db.getCon().prepareStatement("INSERT INTO stepOne VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                ps.setInt(1, p.getId());
                ps.setString(2, p.getResult() != null ? p.getResult() : "");
                ps.setString(3, p.getCompany());
                ps.setString(4, p.getJobTitle());

                ps.setString(5, p.getLocation());
                ps.setString(6, p.getWeb());
                ps.setString(7, p.getPerson() != null ? p.getPerson() : "");
                ps.setString(8, p.getPhone() != null ? p.getPhone() : "");
                ps.setString(9, p.getEmail() != null ? p.getEmail() : "");
                ps.setString(10, DateUtil.toString(p.getRequestSentDate()) != null ? DateUtil.toString(p.getRequestSentDate()) : "");
                ps.setString(11, DateUtil.toString(p.getAnswerDate()) != null ? DateUtil.toString(p.getAnswerDate()) : "");
                ps.setString(12, p.getConversation() != null ? p.getConversation() : "");
                ps.setString(13, p.getCountry());

                ps.setBlob(14, new ByteArrayInputStream(p.getHtml().getBytes(Charset.forName("UTF-8"))));

                ps.execute();
                db.getCon().commit();
            }
            LOG.debug("Information was saved to database");

        } catch (SQLException e) {
            e.printStackTrace();
            Dialogs.exceptionDialog(e);
        }
    }
}
