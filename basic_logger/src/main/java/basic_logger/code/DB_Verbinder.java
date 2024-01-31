package basic_logger.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Verbinder {

    static private Connection conn = null;
    protected String sqlAnfrage = "";
    protected String dbname;

    /**
     * Returns the connection of the current database
     *
     * @return Connection
     */
    public Connection getConnectionToActualDB() {
        return conn;
    }

    /**
     *
     * @param directory - Directory in which the database must be stored
     * @param dbname - Name of database
     * @return - true or false
     *
     */
    public synchronized boolean db_open(String directory, String dbname) {
        DB_Output.myDebug("Opening database in directory: " + directory + " called: " + dbname);
        try {
            // Loading driver
            Class.forName("org.sqlite.JDBC");
            // Connecting
            conn = DriverManager.getConnection("jdbc:sqlite:" + directory + dbname);
            DB_Output.myDebug("Database successfully loaded!");
            return true;
        } catch (ClassNotFoundException | SQLException | SecurityException | IllegalArgumentException e) {
            DB_Output.myDebug("Database error in db_open(): " + e.getLocalizedMessage());
            return false;
        }
    }
    
    public void db_close() {
        try {
            if (conn != null || !conn.isClosed()) {
                conn.close();
            }
            DB_Output.myDebug("Database closed!");
        } catch (SQLException ex) {
            DB_Output.myDebug("DB-connection failed to close! " + ex.getLocalizedMessage());
        }
    }
}
