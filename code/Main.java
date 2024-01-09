package basic_logger.code;

import java.util.Scanner;

/**
 * IMPORTANT NOTE FROM THE AUTHOR Clxppy: Generally speaking: As you use this code you'll probably change it.
 * Nevertheless, if not, then I can encourage you to do so: The code won't work
 * correctly without f.e. changing the directories.
 * I want to make the editing for you as comfortable as possible,
 * so I'll just leave some markers behind for the code below it
 * "CHECKPOINT" for MUST changes, "AVOIDABLE" for INDIVIDUAL changes.
 * You can find those in class:
 * --> Main
 * -->
 */

public class Main {

    static DB_Verbinder gateOpener = new DB_Verbinder();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        /*CHECKPOINT, you have to type in your own directory of your own database --> */
        gateOpener.db_open("C:\\Users\\Clxppy\\IdeaProjects\\basic_logger\\src\\basic_logger\\db_logger.db", "db_logger.sqlite");
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

    static void loginUser(){
        System.out.println("Enter a username:");
        hashToDB(scan.nextLine());
    }

    static void createUser(){

    }

    static void exitProgram(){
        gateOpener.db_close();
        System.exit(1);
    }

    static boolean hashToDB(String key){
        key.hashCode();
        return true;
    }

}

