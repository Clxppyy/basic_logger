package basic_logger.code;

import java.sql.*;

public final class DB_Anfragen extends DB_Verbinder {

    String giveBack;

    public boolean getUsername(String username) {
        try {
            sqlAnfrage = "SELECT username FROM logger WHERE username = ?";
            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
            p.setString(1, username);
            ResultSet ergebnis = p.executeQuery(sqlAnfrage);
            ergebnis.next();
            giveBack = ergebnis.getString("username");
            ergebnis.close();
            p.close();
            return true;
        } catch (SQLException ex) {
            DB_Output.myDebug("Error in getUsername(): " + ex.getLocalizedMessage());
            return false;
        }
    }

    public boolean getPassword(String password) {
        try {
            sqlAnfrage = "SELECT password FROM logger WHERE password = ?";
            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
            p.setString(1, password);
            ResultSet ergebnis = p.executeQuery(sqlAnfrage);
            ergebnis.next();
            giveBack = ergebnis.getString("password");
            ergebnis.close();
            p.close();
            return true;
        } catch (SQLException ex) {
            DB_Output.myDebug("Error in getPassword(): " + ex.getLocalizedMessage());
            return false;
        }
    }

    public boolean insertUsername(String username) {
        try {
            PreparedStatement p = getConnectionToActualDB().prepareStatement("INSERT INTO logger (username) VALUES (?)");

            p.setString(1, username);
            p.addBatch();
            getConnectionToActualDB().setAutoCommit(false);
            p.executeBatch(); // Daten an DB senden
            getConnectionToActualDB().setAutoCommit(true);
            p.close();
            return true;
        } catch (SQLException ex) {
            DB_Output.myDebug("Error in insertUsername(): " + ex.getLocalizedMessage());
            return false;
        }
    }

    public boolean insertPassword(String password) {
        try {
            PreparedStatement p = getConnectionToActualDB().prepareStatement("INSERT INTO logger (password) VALUES (?)");

            p.setString(1, password);
            p.addBatch();
            getConnectionToActualDB().setAutoCommit(false);
            p.executeBatch(); // Daten an DB senden
            getConnectionToActualDB().setAutoCommit(true);
            p.close();
            return true;
        } catch (SQLException ex) {
            DB_Output.myDebug("Error in insertPassword(): " + ex.getLocalizedMessage());
            return false;
        }
    }
}