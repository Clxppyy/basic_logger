package basic_logger.code;

import java.util.Scanner;

/**
 * IMPORTANT NOTE FROM THE AUTHOR Clxppy: Generally speaking: As you use this code you'll probably change it.
 * Nevertheless, if not, then I can encourage you to do so: The code won't work
 * correctly without f.e. changing the directories.
 * I want to make the editing for you as comfortable as possible,
 * so I'll just leave some markers behind for the code below it and called "CHECKPOINT".
 * You can find those in class:
 * --> Main
 * -->
 */



public class Main {
    public static void main(String[] args) {
        DB_Verbinder gateOpener = new DB_Verbinder();
        /*CHECKPOINT, you have to type in your own directory of your own database --> */
        gateOpener.db_open("C:\\Users\\Clxppy\\IdeaProjects\\basic_logger\\src\\basic_logger\\db_logger.db", "db_logger.sqlite");
        /*CHECKPOINT,  the logging process is of course your thing to do.*/
        Scanner scan = new Scanner(System.in);
        System.out.println("'L' for login | 'C' for creating new profile");
        boolean lever = true;
        while(lever){
            switch (scan.nextLine()){
                case "L": loginUser();
                        lever = false;
                        break;

                case "C": createUser();
                        lever = false;
                        break;

                default: System.out.println("Das ist keine valide Eingabe.");
            }
        }
    }

    static void loginUser(){

    }

    static void createUser(){

    }

}

