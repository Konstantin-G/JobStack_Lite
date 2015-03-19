package cz.garkusha.jobstack.util;

import cz.garkusha.jobstack.view.Dialogs;

import java.sql.*;

/**
 * Class manager for Apache DerbyDB.
 *
 * @author Konstantin Garkusha
 */

class DerbyDBManager {
    private static Connection con = null ;
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver" ;

    public DerbyDBManager() {
        if(!dbExists()) {
            try {
                Class.forName(DRIVER) ;
                // Connect to DB create new DB
                con = DriverManager.getConnection(Path.getPathToDbFile() + ";create=true");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Dialogs.exceptionDialog(e);
            } catch (SQLException e) {
                e.printStackTrace();
                Dialogs.someError("Another instance of database have already booted");
            }
        }
    }

    private Boolean dbExists()
    {
        Boolean exists = false ;
        try {
            Class.forName(DRIVER) ;
            con = DriverManager.getConnection(Path.getPathToDbFile());
            exists = true ;
        } catch(Exception ignore) {
            System.out.println("Can't connect to database");
            // Do nothing if database isn't exist
        }
        return exists ;
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

