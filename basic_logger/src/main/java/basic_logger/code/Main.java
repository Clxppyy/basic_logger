package basic_logger.code;


import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * IMPORTANT NOTE FROM THE AUTHOR Clxppy: Generally speaking: As you use this code you'll probably change it.
 * Nevertheless, if not, then I can encourage you to do so: The code won't work
 * correctly without f.e. changing the path/directory.
 * Still, I want to make the editing for you as comfortable as possible,
 * so I'll just leave some markers behind for the code below it
 * "CHECKPOINT" for NECESSARY changes, "AVOIDABLE" for INDIVIDUAL changes.
 */

public abstract class Main implements ExecutorService {

    static String parameter;

    public Main(String parameter){
        Main.parameter = parameter;
    }

    static DB_Verbinder gateOpener = new DB_Verbinder();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        /*CHECKPOINT, you have to type in your own directory of your own database --> */
        if(gateOpener.db_open("C:\\Users\\Clxppy\\IdeaProjects\\basic_logger\\", "db_logger.db")){
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

    static void organize(Boolean lever) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        System.out.println("Enter a username:");
        AtomicReference<String> username = new AtomicReference<>(scan.nextLine());
        executor.execute(() -> username.set(encryptInput(username.get())));
        System.out.println("Enter a password:");
        AtomicReference<String> password = new AtomicReference<>(scan.nextLine());
        executor.execute(() -> password.set(encryptInput(password.get())));
        executor.shutdown();
        while(executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)){
            if(!lever){
                //lever = false means the user wants to log in
                ToDB(false, username.get(), password.get());
                break;
            }
            else {
                //lever = true means a new profile shall be created
                ToDB(true, username.get(), password.get());
                break;
            }
        }
    }

    static String encryptInput(String input){
        //Origin of code: https://www.javatpoint.com/aes-256-encryption-in-java
        //*AVOIDABLE, you may want to change SECRET_KEY and SALTVALUE*
        final String SECRET_KEY = "your_mom_lol";
        final String SALTVALUE = "123_my_glock_17_is_with_me";
        /* Encryption Method */
        try {
            /* Declare a byte array. */
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            /* Create factory for secret keys. */
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            /* PBEKeySpec class implements KeySpec interface. */
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALTVALUE.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            /* Retruns encrypted value. */
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(input.getBytes(StandardCharsets.UTF_8)));
            }
            catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e)
            {
                System.out.println("Error occured during encryption: " + e.toString());
            }
            return null;
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