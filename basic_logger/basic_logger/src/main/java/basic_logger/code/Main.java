package basic_logger.code;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * IMPORTANT NOTE FROM THE AUTHOR Clxppy: Generally speaking: As you use this code you'll probably change it.
 * Nevertheless, if not, then I can encourage you to do so: The code won't work
 * correctly without f.e. changing the path/directory.
 * Still, I want to make the editing for you as comfortable as possible,
 * so I'll just leave some markers behind for the code below it
 * "CHECKPOINT" for MUST changes, "AVOIDABLE" for INDIVIDUAL changes.
 */

public abstract class Main implements ExecutorService {

    static String parameter;

    public Main(String parameter){
        Main.parameter = parameter;
    }

    static DB_Verbinder gateOpener = new DB_Verbinder();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        /*CHECKPOINT, you have to type in your own directory of your own database --> */
        if(gateOpener.db_open("/home/AD/emanuel.bachir/IdeaProjects/basic_logger/basic_logger/", "db_logger.db")){
            /*AVOIDABLE,  from here on now the logging process is of course your thing to do.*/
            System.out.println("'L' for login / 'C' for creating new profile / 'E' for exit");
            boolean lever = true;
            while(lever){
                switch (scan.nextLine()) {
                    case "L" -> {
                        //lever = false means the user wants to log in
                        organize(lever = false);
                    }
                    case "C" -> {
                        //lever = true means a new profile shall be created
                        organize(true);
                    }
                    case "E" -> {
                        exitProgram();
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

    static void organize(Boolean lever){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        System.out.println("Enter a username:");
        String username = scan.nextLine();
        executor.execute(() -> encryptInput(username));
        System.out.println("Enter a password:");
        String password = scan.nextLine();
        executor.execute(() -> encryptInput(password));
        executor.shutdown();
        while(executor.isTerminated()){
            if(!lever){
                //lever = false means the user wants to log in
                ToDB(lever, username, password);
                break;
            }
            else {
                //lever = true means a new profile shall be created
                ToDB(lever, username, password);
                break;
            }
        }
    }

    static String encryptInput(String input){
        //Copyright (c) 2006 Damien Miller <djm@mindrot.org>
        String hashed = BCrypt.hashpw(input, BCrypt.gensalt(15));
        System.out.println(hashed);
        return hashed;
    }

    static void ToDB(boolean lever, String key_username, String key_password) {
        DB_Anfragen opener = new DB_Anfragen();
        if(!lever){
            //lever = false means the user wants to log in
            if(opener.getData(key_username, key_password)){
                System.out.println("Login successful!");
            }
            else {
                System.out.println("### Your credentials are not valid. ###");
                exitProgram();
            }
        }
        else {
            //lever = true means a new profile shall be created
            if(opener.insertData(key_username, key_password)){
                System.out.println("Profile successfully created!");
            }
            else {
                System.out.println("### This username is unfortunately already used. Retry with a different one. ###");
                exitProgram();
            }
        }
    }
}