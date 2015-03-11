package cz.garkusha.jobstack.util;

import java.io.File;
import java.sql.*;

/**
 * Class manager for Apache DerbyDB.
 *
 * @author Konstantin Garkusha
 */

class DerbyDBManager {
    private static Connection con = null ;
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver" ;
    private static final String URL = "jdbc:derby:" + System.getProperty("user.dir") + File.separator + "data" + File.separator; // jdbc:derby:/home/user/dreby/mydb
    private String dbName;

    public DerbyDBManager(String dbName) {
        this.dbName=dbName;
        if(!dbExists()) {
            try {
                Class.forName(DRIVER) ;
                // Connect to DB create new DB
                con = DriverManager.getConnection(URL + dbName + ";create=true");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Boolean dbExists()
    {
        Boolean exists = false ;
        try {
            Class.forName(DRIVER) ;
            con = DriverManager.getConnection(URL + dbName);
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

