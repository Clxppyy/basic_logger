package basic_logger.code;

import java.sql.*;

public final class DB_Anfragen extends DB_Verbinder {
    public boolean getData(String keyUsername, String keyPassword){
        try {
            sqlAnfrage = "SELECT username FROM logger WHERE username = ? AND password = ?";
            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
            p.setString(1, keyUsername);
            p.setString(1, keyPassword);
            ResultSet ergebnis = p.executeQuery(sqlAnfrage);
            ergebnis.next();
            ergebnis.close();
            p.close();
            return true;
        } catch (SQLException ex) {
            DB_Output.myDebug("Error in getPassword(): " + ex.getLocalizedMessage());
            return false;
        }
    }

    public boolean insertData(String keyUsername, String keyPassword) {
        try {
            PreparedStatement p = getConnectionToActualDB().prepareStatement("INSERT INTO logger (username, password) VALUES (?,?)");
            p.setString(1, keyUsername);
            p.setString(2, keyPassword);
            p.addBatch();
            getConnectionToActualDB().setAutoCommit(false);
            p.executeBatch(); // send data to DB
            getConnectionToActualDB().setAutoCommit(true);
            p.close();
            return true;
        } catch (SQLException ex) {
            DB_Output.myDebug("Error in insertPassword(): " + ex.getLocalizedMessage());
            return false;
        }
    }
}