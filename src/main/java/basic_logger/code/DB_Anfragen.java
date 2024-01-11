package basic_logger.code;

import java.sql.*;

public final class DB_Anfragen extends DB_Verbinder {

    String rueck;

    public boolean getUsername(String username) {
        try {
            sqlAnfrage = "SELECT username FROM logger WHERE username = ?";
            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
            p.setString(1, username);
            ResultSet ergebnis = p.executeQuery(sqlAnfrage);
            ergebnis.next();
            rueck = ergebnis.getString("username");
            ergebnis.close();
            p.close();
        } catch (SQLException ex) {
            DB_Output.myDebug("Fehler in getUsername(): " + ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public boolean getPassword(String password) {
        try {
            sqlAnfrage = "SELECT password FROM logger WHERE password = ?";
            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
            p.setString(1, password);
            ResultSet ergebnis = p.executeQuery(sqlAnfrage);
            ergebnis.next();
            rueck = ergebnis.getString("password");
            ergebnis.close();
            p.close();
        } catch (SQLException ex) {
            DB_Output.myDebug("Fehler in getPassword(): " + ex.getLocalizedMessage());
            return false;
        }
        return true;
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

        } catch (SQLException ex) {
            DB_Output.myDebug("Fehler in insertIntowarenkorb(): " + ex.getLocalizedMessage());
            return false;
        }
        return true;
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

        } catch (SQLException ex) {
            DB_Output.myDebug("Fehler in insertIntowarenkorb(): " + ex.getLocalizedMessage());
            return false;
        }
        return true;
    }





//    //Diese Methode soll insbesondere verwendet werden, wenn der User auf den Knopf "In den warenkorb" drückt
//    public boolean insertIntoWarenkorb(int produktid, String farbe, int speicherplatz, String garantie, int accountid, int menge) {
//        try {
//            PreparedStatement p = getConnectionToActualDB().prepareStatement("INSERT INTO warenkorb (produktid, farbe, speicherplatz, garantie, accountid, menge) VALUES (?,?,?,?,?,?)");
//
//            p.setInt(1, produktid);
//            p.setString(2, farbe);
//            p.setInt(3, speicherplatz);
//            p.setString(4, garantie);
//            p.setInt(5, accountid);
//            p.setInt(6, menge);
//            p.addBatch();
//            getConnectionToActualDB().setAutoCommit(false);
//            p.executeBatch(); // Daten an DB senden
//            getConnectionToActualDB().setAutoCommit(true);
//            p.close();
//
//        } catch (SQLException ex) {
////            Hilfsfunktionen.myDebug("Fehler in insertIntowarenkorb(): " + ex.getLocalizedMessage());
//            return false;
//        }
//        return true;
//    }
//
//    //Die dynamische UI ist für den Designer, sprich Safi/(Michel).
///*
//    public String getProductList(){
//        String allproducts;
//        try{
//            sqlAnfrage = "SELECT count(produktid) FROM warenkorb";
//            Statement p = getConnectionToActualDB().createStatement();
//            ResultSet ergebnis = p.executeQuery(sqlAnfrage);
//
//        } catch (SQLException ex){
//            Hilfsfunktionen.myDebug("Fehler in getProductList(): " + ex.getLocalizedMessage());
//            return "";
//        }
//        return allproducts;
//    }
//*/
//    //Diese Methode wird genutzt, damit der aktuelle warenkorb eines Users angezeigt wird
//    public String getWarenkorb(int accountid) {
//        try {
//            sqlAnfrage = "SELECT accountid FROM account INNER JOIN warenkorb ON account.accountid = warenkorb.accountid WHERE account.accountid = ?";
//            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
//            p.setInt(1, accountid);
//            ResultSet ergebnis = p.executeQuery(sqlAnfrage);
//            ergebnis.next();
//            rueck = ergebnis.getString("accountid");
//            ergebnis.close();
//            p.close();
//        } catch (SQLException ex) {
////            DB_Output.myDebug("Fehler in getwarenkorb(): " + ex.getLocalizedMessage());
//            return "";
//        }
//        return rueck;
//    }
//
//    //Diese Methode soll aufgerufen werden, wenn man sich auf der Login-Page befindet, wo der User die Möglichkeit hat, sich zu registrieren
//    public boolean createAccount(String name, String email, String pw, String adresse, int telefonnummer, int accountid) {
//        try {
//            // PreparedStatement braucht man, wenn man Datensätze einfügen will. Hier soll es explizit SQL-Injections verhindern.
//            PreparedStatement p = getConnectionToActualDB().prepareStatement("INSERT INTO account (name, email, pw, adresse, telefonnummer, accountid) VALUES (?,?,?,?,?,?)");
//
//            p.setString(1, name);
//            p.setString(2, email);
//            p.setString(3, pw);
//            p.setString(4, adresse);
//            p.setInt(5, telefonnummer);
//            p.setInt(6, accountid);
//            p.addBatch();
//            getConnectionToActualDB().setAutoCommit(false);
//            p.executeBatch(); // Daten an DB senden
//            getConnectionToActualDB().setAutoCommit(true);
//            p.close();
//
//        } catch (SQLException ex) {
////            Hilfsfunktionen.myDebug("Fehler in createaccount(): " + ex.getLocalizedMessage());
//            return false;
//        }
//        return true;
//    }
//
//    //Die Methode wird verwendet, um die kategorie(n) eines jeweiligen produkts in der UI anzugeben
//    public String getCategory(int produktid) {
//        try {
//            sqlAnfrage = "SELECT kategorie FROM kategorie WHERE produkt.produktid = ?";
//            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
//            p.setInt(1, produktid);
//            ResultSet ergebnis = p.executeQuery(sqlAnfrage);
//            ergebnis.next();
//            rueck = ergebnis.getString("kategorie");
//            ergebnis.close();
//            p.close();
//        } catch (SQLException ex) {
////            Hilfsfunktionen.myDebug("Fehler in getCategory(): " + ex.getLocalizedMessage());
//            return "";
//        }
//        return rueck;
//    }
//    public String getDescription(int produktid) {
//        try {
//            sqlAnfrage = "SELECT beschreibung FROM produkt WHERE produktid = ?";
//            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
//            p.setInt(1, produktid);
//            ResultSet ergebnis = p.executeQuery(sqlAnfrage);
//            ergebnis.next();
//            rueck = ergebnis.getString("beschreibung");
//            ergebnis.close();
//            p.close();
//        } catch (SQLException ex) {
////            Hilfsfunktionen.myDebug("Fehler in getDescription(): " + ex.getLocalizedMessage());
//            return "";
//        }
//        return rueck;
//    }
//
//    //Die Methode soll aufgerufen werden, wenn der User den Kauf abschließt
//    public void purchase(int accountid) {
//        try {
//            //neue Methode zum Einfügen von Warenkorb Daten mit SELECT * FROM Warenkorb in Bestellungen einfügen
//            if (transfer(accountid)){
//                String sqlAnfrage = "DELETE * FROM warenkorb WHERE accountid = ?";
//                PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
//                p.setInt(1, accountid);
//                getConnectionToActualDB().setAutoCommit(false);
//                p.executeUpdate(); // Daten an DB senden
//                getConnectionToActualDB().setAutoCommit(true);
//                p.close();
//            }
//        } catch (SQLException ex) {
////            Hilfsfunktionen.myDebug("Fehler in purchase(): " + ex.getLocalizedMessage());
//        }
//    }
//
//    //NICHT FÜR DIE UI (AUFRUFEN), NUR EINE HILFSMETHODE FÜR purchase()
//
//
//    public Boolean transfer(int accountid){
//        try{
//            String sqlAnfrage = "INSERT INTO bestellungen (menge, produktid, accountid, datum, bestellungsid) VALUES ((SELECT menge, produktid, accountid FROM warenkorb WHERE warenkorb.accountid = ?), datum = ?, bestellungsid = ?)";
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//            LocalDateTime now = LocalDateTime.now();
//            String Date = dtf.format(now);
//            String uniqueID = UUID.randomUUID().toString();
//            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
//            //Bei 2 unterschiedlichen Statements, die anscheinend nicht gleich, sondern nacheinander laufen, sind die Inserts auf derselben Spalte oder in immer neue? Wie soll die AccountID-Erstellung dann mit SELECT und count() funktionieren?
//            p.setInt(1, accountid);
//            p.setString(2, Date);           //WIE FUNKTIONIERT DIE ITERATION 1,2,3 vor den PARAMETERN?
//            p.setString(3, uniqueID);
//            getConnectionToActualDB().setAutoCommit(false);
//            p.executeUpdate();
//            getConnectionToActualDB().setAutoCommit(true);
//            p.close();
//        } catch (SQLException ex) {
////            Hilfsfunktionen.myDebug("Fehler in transfer(): " + ex.getLocalizedMessage());
//            return false;
//        }
//        return true;
//    }
//
//
//    /*
//    public Boolean transfer(int accountid){
//        try{
//            String sqlAnfrage = "INSERT INTO bestellungen (SELECT menge, produktid, accountid FROM warenkorb WHERE warenkorb.accountid = ?)";
//            String sqlAnfrageNr2 = "UPDATE bestellungen SET datum = date, bestellungsid = uniqueID WHERE accountid = ?";
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//            LocalDateTime now = LocalDateTime.now();
//            String Date = dtf.format(now);
//            String uniqueID = UUID.randomUUID().toString();
//            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
//            PreparedStatement d = getConnectionToActualDB().prepareStatement(sqlAnfrageNr2);        //Bei 2 unterschiedlichen Statements, die anscheinend nicht gleich, sondern nacheinander laufen, sind die Inserts auf derselben Spalte oder in immer neue? Wie soll die AccountID-Erstellung dann mit SELECT und count() funktionieren?
//            p.setInt(1, accountid);
//            d.setString(1, Date);
//            d.setString(2, uniqueID);
//            getConnectionToActualDB().setAutoCommit(false);
//            p.executeUpdate();
//            d.executeUpdate();
//            getConnectionToActualDB().setAutoCommit(true);
//            p.close();
//            d.close();
//        } catch (SQLException ex) {
//            Hilfsfunktionen.myDebug("Fehler in transfer(): " + ex.getLocalizedMessage());
//            return false;
//        }
//        return true;
//    }
//    */
//
//    //Drei Methoden zur Anpassung der Menge eines produkts im warenkorb
//    public void addQuantity(int produktid, int accountid) {
//        try {
//            String sqlAnfrage = "UPDATE menge FROM warenkorb SET menge + 1 WHERE produktid = ? AND accountid = ?";
//            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
//            p.setInt(1, produktid);
//            p.setInt(2, accountid);
//            getConnectionToActualDB().setAutoCommit(false);
//            p.executeUpdate(); // Daten an DB senden
//            getConnectionToActualDB().setAutoCommit(true);
//            p.close();
//        } catch (SQLException ex) {
////            Hilfsfunktionen.myDebug("Fehler in addQuantity(): " + ex.getLocalizedMessage());
//        }
//    }
//    public void removeQuantity(int produktid, int accountid) {
//        try {
//            String sqlAnfrage = "UPDATE menge FROM warenkorb SET menge-1 WHERE produktid = ? AND accountid = ?";
//            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
//            p.setInt(1, produktid);
//            p.setInt(2, accountid);
//            getConnectionToActualDB().setAutoCommit(false);
//            p.executeUpdate(); // Daten an DB senden
//            getConnectionToActualDB().setAutoCommit(true);
//            p.close();
//        } catch (SQLException ex) {
////            Hilfsfunktionen.myDebug("Fehler in addQuantity(): " + ex.getLocalizedMessage());
//        }
//    }
//
//    //Diese Methode wird aufgerufen, wenn man einzelne Produkte aus dem Warenkorb löschen möchte. Da jeder produktid ein
//    //Spalt in dem Warenkorb zugewiesen ist und die UI dynamisch ist, wird getwarenkorb() durch (womöglich ein if-statement) die Rückgabe
//    //eines "(true-)booleans" aufgerufen und somit das Produkt aus der Liste gelöscht bzw. der Warenkorb aktualisiert.
//    //Der Warenkorb wird infolgedessen NICHT gebloated sein, da der DELETE Statement ganze Spalten löscht.
//    public Boolean deleteProductFromShoppingCart(int produktid, int accountid) {
//        try {
//            String sqlDelete = "DELETE produktid FROM warenkorb WHERE produktid = ? AND accountid = ?";
//            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlDelete);
//            p.setInt(1, produktid);
//            p.setInt(2, accountid);
//            getConnectionToActualDB().setAutoCommit(false);
//            p.executeUpdate(); // Daten an DB senden
//            getConnectionToActualDB().setAutoCommit(true);
//            p.close();
//
//        } catch (SQLException ex) {
////            Hilfsfunktionen.myDebug("Fehler in delName(): " + ex.getLocalizedMessage());
//            return false;
//        }
//        return true;
//    }
//
//    //Diese Methode wird benutzt, sobald der User seine vergangenen Bestellungen ansehen möchte
//    public ArrayList<String> getBestellungen(int accountid) {
//        ArrayList<String> liste = new ArrayList();
//        try {
//            sqlAnfrage = "SELECT * FROM bestellungen WHERE accountid = ?";
//            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
//            p.setInt(1, accountid);
//            ResultSet ergebnis = p.executeQuery(sqlAnfrage);
//            while(ergebnis.next()){                                 //An Hr. Bräuer, das so, für dynamische UI, valide? Überhaupt sinnvoll? Weil alle Daten in einem Spalt gespeichert werden???
//                rueck = ergebnis.getString("bestellungsid");
//                liste.add(rueck);
//            }
//            ergebnis.close();
//            p.close();
//        } catch (SQLException ex) {
////            Hilfsfunktionen.myDebug("Fehler in getDescription(): " + ex.getLocalizedMessage());
//        }
//        return liste;
//    }
//
//    public String getAccountDetails(int accountid) {
//        try {
//            sqlAnfrage = "SELECT * FROM account WHERE accountid = ?";
//            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
//            p.setInt(1, accountid);
//            ResultSet ergebnis = p.executeQuery(sqlAnfrage);
//            ergebnis.next();
//            rueck = ergebnis.getString("accountid");
//            ergebnis.close();
//            p.close();
//        } catch (SQLException ex) {
////            Hilfsfunktionen.myDebug("Fehler in getAccountDetails(): " + ex.getLocalizedMessage());
//            return "";
//        }
//        return rueck;
//    }
//
//    public Boolean login(String email, String pw) {
//        try {
//            sqlAnfrage = "SELECT * FROM account WHERE email = ? AND pw = ?";
//            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlAnfrage);
//            p.setString(1, email);
//            p.setString(2, pw);
//            getConnectionToActualDB().setAutoCommit(false);
//            p.execute(); // Daten an DB senden
//            getConnectionToActualDB().setAutoCommit(true);
//            p.close();
//        } catch (SQLException ex) {
////            Hilfsfunktionen.myDebug("Fehler in login(): " + ex.getLocalizedMessage());
//            return false;
//        }
//        return true;
//    }
//
////    BEISPIEL ZUR NUTZUNG VON SQLITE-COMMANDS IN JAVA:
//
//    /*
//     * Prüft, ob Daten vorhanden sind.
//     *
//     * @return true, falls Daten vorhanden sind, sonst false
//     */
//
////    public boolean isFilled() {
////        return !getNamensliste().isEmpty();
////    }
////
////    /**
////     * BEISPIEL für SELECT: Holt alle Namen in eine ArrayList.
////     *
////     * @return ArrayList mit Namen
////     */
////    public ArrayList<String> getNamensliste() {
////        ArrayList<String> liste = new ArrayList<>();
////
////        try {
////            Statement sql = getConnectionToActualDB().createStatement();
////
////            sqlAnfrage = "SELECT name, vorname FROM namen ORDER BY name, vorname";
////            System.out.println(sqlAnfrage);
////            ResultSet ergebnis = sql.executeQuery(sqlAnfrage);
////
////            while (ergebnis.next()) {
////                liste.add(ergebnis.getString("name") + ", " + ergebnis.getString("vorname"));
////            }
////
////            ergebnis.close();
////
////        } catch (SQLException ex) {
////            Hilfsfunktionen.myDebug("Fehler in getNamensliste(): " + ex.getLocalizedMessage());
////        }
////        return liste;
////    }
//
///*
//    public boolean addName(String name, String vorname) {
//
//        try {
//            // PreparedStatement braucht man, wenn man Datensätze einfügen will
//            PreparedStatement p = getConnectionToActualDB().prepareStatement("INSERT INTO namen (name, vorname) VALUES (?,?)");
//
//            p.setString(1, name);
//            p.setString(2, vorname);
//
//            getConnectionToActualDB().setAutoCommit(false);
//            p.executeUpdate(); // Daten an DB senden
//            getConnectionToActualDB().setAutoCommit(true);
//            p.close();
//
//        } catch (SQLException ex) {
//            Hilfsfunktionen.myDebug("Fehler in addName(): " + ex.getLocalizedMessage());
//            return false;
//        }
//        return true;
//    }
//
//    public void delName(String name) {
//        Hilfsfunktionen.myDebug("Lösche: " + name);
//
//        String[] person = name.split(", ");
//
//        try {
//
//            String sqlDelete = "DELETE FROM namen WHERE name=? AND vorname=?";
//
//            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlDelete);
//
//            p.setString(1, person[0]);
//            p.setString(2, person[1]);
//
//            getConnectionToActualDB().setAutoCommit(false);
//            p.executeUpdate(); // Daten an DB senden
//            getConnectionToActualDB().setAutoCommit(true);
//            p.close();
//
//        } catch (SQLException ex) {
//            Hilfsfunktionen.myDebug("Fehler in delName(): " + ex.getLocalizedMessage());
//        }
//
//    }
//
//    public void updateName(String[] person_alt, String[] person) {
//        System.out.println("Aktualisiere: " + person[0] + ", " + person[1]);
//
//        try {
//            Statement sql = getConnectionToActualDB().createStatement();
//
//            String sqlUpdate = "UPDATE namen SET name=?, vorname=? WHERE name=? AND vorname=?";
//
//            PreparedStatement p = getConnectionToActualDB().prepareStatement(sqlUpdate);
//
//            p.setString(1, person[0]);
//            p.setString(2, person[1]);
//            p.setString(3, person_alt[0]);
//            p.setString(4, person_alt[1]);
//
//            getConnectionToActualDB().setAutoCommit(false);
//            p.executeUpdate(); // Daten an DB senden
//            getConnectionToActualDB().setAutoCommit(true);
//            p.close();
//
//        } catch (SQLException ex) {
//            Hilfsfunktionen.myDebug("Fehler in updateName(): " + ex.getLocalizedMessage());
//        }
//    }
//*/
//
}