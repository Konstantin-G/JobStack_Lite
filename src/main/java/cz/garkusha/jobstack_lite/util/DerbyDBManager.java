package cz.garkusha.jobstack_lite.util;

import cz.garkusha.jobstack_lite.controller.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Class manager for Apache DerbyDB.
 *
 * @author Konstantin Garkusha
 */

public class DerbyDBManager {

    private static final Logger LOG = LoggerFactory.getLogger(DerbyDBManager.class);

    private Connection con = null ;
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver" ;

    public DerbyDBManager() {
        LOG.info("Start Derby Database Manager");
        if(!dbExists()) {
            try {
                LOG.debug("Trying to create new database");
                Class.forName(DRIVER) ;
                // Connect to DB create net w DB
                con = DriverManager.getConnection(Path.getPathToDbFile() + ";create=true");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Dialogs.exceptionDialog(e);
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }

    public Connection getCon() {
        return con;
    }

    private Boolean dbExists()
    {
        LOG.debug("Trying to establish connection to database");
        try {
            Class.forName(DRIVER) ;
            con = DriverManager.getConnection(Path.getPathToDbFile());
            LOG.debug("Connection to database was established");
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            Dialogs.exceptionDialog(e);
        } catch(SQLException e) {
            // if error caused by already booted database
            if ("XJ040".equals(e.getSQLState())){
                LOG.debug("Another instance of database have already booted");
                Dialogs.someError("Another instance of database have already booted");
                return true;
            } else // if error caused by missing database
            if ("XJ004".equals(e.getSQLState())){
                LOG.debug("Database isn't exist");
                Dialogs.someError("Your database not found,\n" +
                        "if you have the DB, check your DB in data folder,\n" +
                        "if you haven't the DB, i'll create it.");
                return false;
            }
        }
        return true;
    }

    // Query to update information in database  (INSERT, UPDATE, CREATE TABLE ...)
    public void executeUpdate(String sql) throws SQLException {
        Statement stmt = con.createStatement() ;
        @SuppressWarnings("UnusedAssignment") int count = stmt.executeUpdate(sql) ;
        stmt.close() ;
    }

    // Query to read information from database
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement stmt = con.createStatement() ;
        return stmt.executeQuery(sql);
    }
}

