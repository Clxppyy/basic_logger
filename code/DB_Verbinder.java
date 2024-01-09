package basic_logger.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DB_Verbinder {

    /*Verzeichnis, wo die Datenbank gespeichert ist/werden soll.
    * Wichtig: Datenbank IM HOME-Verzeichnis des Benutzers sein (speziell für Linux)!
    * SQLite-Datenbanken müssen im Benutzer-Hauptverzeichnis gespeichert werden,
    * _nicht_ unter Files oder Groups! Ansonsten kann nicht gespeichert werden.
    * Die Dateinen selbst können dann aber in Files oder Groups gesichert werden.
     System.getProperty("user.home") + System.getProperty("file.separator");
     */
    public static final String SQLITEVZ = System.getProperty("user.home") + System.getProperty("file.separator");
    // Name der Datenbankdatei
    public static final String SQLITEDB = "db_logger.sqlite";
/*
    private final JFrame parent;

    public DB_Verbinder(JFrame parent) {
        this.parent = parent;
    }
*/
    private Connection conn = null;
    protected String sqlAnfrage = "";
    protected String dbname;

    public static String getSQLITEVZ() {
        return DB_Verbinder.SQLITEVZ;
    }

    public static String getSQLITEDB() {
        return DB_Verbinder.SQLITEDB;
    }

    public String getActualDBName() {
        return dbname;
    }

    /**
     * Gibt die aktuelle Verbindung zur DB zurück.
     *
     * @return Connection
     */
    public Connection getConnectionToActualDB() {
        return conn;
    }

    /**
     *
     * @param verzeichnis - Verzeichnis, in dem die DB-Datei gespeichert ist.
     * @param dbname - Name der Datenbank
     * @return - wahr oder falsch
     *
     */
    public synchronized boolean db_open(String verzeichnis, String dbname) {
        DB_Output.myDebug("Öffne Datenbank: " + verzeichnis + dbname);
        this.dbname = dbname;

        boolean dbtest;
        try {
            // Treiber laden
            Class.forName("org.sqlite.JDBC");
            // Verbindung aufbauen
            conn = DriverManager.getConnection("jdbc:sqlite:" + verzeichnis + dbname);

            dbtest = true;

        } catch (ClassNotFoundException | SQLException | SecurityException | IllegalArgumentException e) {
            DB_Output.myDebug("Datenbankfehler in db_open(): " + e.getLocalizedMessage());
            dbtest = false;
        }
        return dbtest;
    }
    
    public void db_close() {
        try {
            if (conn != null || !conn.isClosed()) {
                conn.close();
            }
            DB_Output.myDebug("Datenbank geschlossen!");
        } catch (SQLException ex) {
            DB_Output.myDebug("######## DB-Verbindung konnte NICHT geschlossen werden! ########\n" + ex.getLocalizedMessage());
        }
    }


//    public boolean dbIsFilled() {
//        try {
//            Statement sql = getConnectionToActualDB().createStatement();
//
//            sqlAnfrage = "SELECT name FROM namen";
//
//            // falls die Abfrage fehlschlägt, wird eine SQLException ausgelöst
//            ResultSet ergebnis = sql.executeQuery(sqlAnfrage);
//
//            ergebnis.close();
//            return true;
//        } catch (SQLException ex) {
//            DB_Output.myDebug("Datenbank ist leer!");
//            return false;
//        }
//
//    }


/**
        // Prüft, ob die Datenbankdatei existiert.
        if (!dbIsFilled()) {

            Hilfsfunktionen.myDebug("Datenbank nicht vorhanden, lege neu an:");
            Hilfsfunktionen.myDebug("Ort: " + getSQLITEVZ() + getSQLITEDB());
            // Erstellt die Datenbank neu, wenn sie nicht existiert.
            if (createDB()) {
                if (insertExampleData()) {
                    JOptionPane.showMessageDialog(parent, "Die Datenbank wurde erfolgreich angelegt und mit Beispieldaten gefüllt.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                    Hilfsfunktionen.myDebug("Datenbank erfolgreich angelegt und gefüllt.");
                } else {
                    //JOptionPane.showMessageDialog(parent, "Die Datenbank konnte nicht angelegt werden.", "Fehler", JOptionPane.WARNING_MESSAGE);
                    Hilfsfunktionen.myDebug("Datenbank NICHT erolgreich gefüllt.");
                }
            } else {
                //JOptionPane.showMessageDialog(parent, "Die Datenbanktabellen konnten nicht angelegt werden.", "Fehler", JOptionPane.WARNING_MESSAGE);
                Hilfsfunktionen.myDebug("Datenbank NICHT erfolgreich angelegt.");
                System.exit(1);
            }
        } else {
            // DB existiert schon, verwende vorhandene Datei
            Hilfsfunktionen.myDebug("Datenbank existiert bereits.");
        }

        return dbtest;
    }
    
/**
 * 
 * Beispieldaten einfügen:
 * 
    public boolean insertExampleData() {
        Hilfsfunktionen.myDebug("Füge Beispieldaten ein...");
        conn = getConnectionToActualDB();
        try {
            // PreparedStatement braucht man, wenn man Datensätze einfügen will
            PreparedStatement p = conn.prepareStatement("INSERT INTO namen (name, vorname) VALUES (?,?);");

            p.setString(1, "Hans");
            p.setString(2, "Meiser");
            p.addBatch();

            p.setString(1, "Müller");
            p.setString(2, "Heinz");
            p.addBatch();

            p.setString(1, "Meier");
            p.setString(2, "Horst");
            p.addBatch();

            p.setString(1, "Schmitt");
            p.setString(2, "Harald");
            p.addBatch();

            p.setString(1, "Berger");
            p.setString(2, "Martin");
            p.addBatch();

            conn.setAutoCommit(false);
            p.executeBatch(); // Daten an DB senden
            conn.setAutoCommit(true);
            p.close();

        } catch (SQLException ex) {
            Hilfsfunktionen.myDebug("Fehler in insertExampleData(): " + ex.getLocalizedMessage());
            return false;
        }
        Hilfsfunktionen.myDebug("Füge Beispieldaten ein... fertig!");
        return true;
    }
    
**/
    
/**
 * SQLITE-Datenbank in Java kann man auch erstellen:
 * 
    public boolean createDB() {
        Hilfsfunktionen.myDebug("Erstelle Datenbank...");

        if (conn != null) {

            try {
                Statement sql = getConnectionToActualDB().createStatement();
                String sqlCreate = "CREATE TABLE \"namen\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , \"name\" VARCHAR NOT NULL , \"vorname\" VARCHAR NOT NULL )";
                try {
                    sql.execute(sqlCreate);
                    Hilfsfunktionen.myDebug("Datenbank wurde erzeugt.");
                } catch (SQLException ex) {
                    Hilfsfunktionen.myDebug("Fehler in createDB() 1: " + ex.getLocalizedMessage());
                    return false;
                }

                //conn.close();
            } catch (SQLException ex) {
                Hilfsfunktionen.myDebug("Fehler in createDB() 2: " + ex.getLocalizedMessage());
                return false;
            }
            Hilfsfunktionen.myDebug("Erstelle Datenbank... fertig!");
        } else {
            Hilfsfunktionen.myDebug("Fehler in createDB() 3: ");
            Hilfsfunktionen.myDebug("CONN = null");
            Hilfsfunktionen.myDebug("Erstelle Datenbank... fehlgeschlagen!");
            return false;
        }

        return true;
    }
    * 
**/
}
