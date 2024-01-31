package basic_logger.code;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;

/**
 * IMPORTANT NOTE FROM THE AUTHOR Clxppy: Generally speaking: As you use this code you'll probably change it.
 * Nevertheless, if not, then I can encourage you to do so: The code won't work
 * correctly without f.e. changing the path/directory.
 * Still, I want to make the editing for you as comfortable as possible,
 * so I'll just leave some markers behind for the code below it
 * "CHECKPOINT" for MUST changes, "AVOIDABLE" for INDIVIDUAL changes.
 */

public class Main {

    static DB_Verbinder gateOpener = new DB_Verbinder();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        /*CHECKPOINT, you have to type in your own directory of your own database --> */
        if(gateOpener.db_open("C:\\Users\\Clxppy\\IdeaProjects\\basic_logger", "db_logger.db")){
            /*AVOIDABLE,  from here on now the logging process is of course your thing to do.*/
            System.out.println("'L' for login / 'C' for creating new profile / 'E' for exit");
            boolean lever = true;
            while(lever){
                switch (scan.nextLine()) {
                    case "L" -> {
                        loginUser();
                        lever = false;
                    }
                    case "C" -> {
                        createUser();
                        lever = false;
                    }
                    case "E" -> {
                        exitProgram();
                        lever = false;
                    }
                    default -> System.out.println("That isn't a valid input. Please retry:");
                }
            }
        }
        else{
            System.exit(1);
        }
    }

    static void exitProgram(){
        gateOpener.db_close();
        System.exit(1);
    }

    static void loginUser(){
        System.out.println("Enter a username:");
        String username = scan.nextLine();
        System.out.println("Enter the belonging password:");
        String password = scan.nextLine();
        hashToDB(true , username, password);
    }

    static void createUser(){
        System.out.println("Enter a username:");
        String username = scan.nextLine();
        System.out.println("Enter a password:");
        String password = scan.nextLine();
        hashToDB(false , username, password);
    }

    static void hashToDB(boolean lever, String key_username, String key_password) {
        //Copyright (c) 2006 Damien Miller <djm@mindrot.org>
        String hashed_username = BCrypt.hashpw(key_username, BCrypt.gensalt(15));
        String hashed_password = BCrypt.hashpw(key_password, BCrypt.gensalt(15));
        DB_Anfragen opener = new DB_Anfragen();
        if(lever){
            if(opener.getUsername(hashed_username) && opener.getPassword(hashed_password)){
                System.out.println("Login successful!");
            }
            else {
                System.out.println("Your credentials are not valid.");
            }
        }
        else {
            if(opener.insertUsername(hashed_username) && opener.insertPassword(hashed_password)){
                System.out.println("Profile successfully created!");
            }
            else {
                System.out.println("This username is unfortunately already used. Retry with a different one.");
            }
        }
    }
}