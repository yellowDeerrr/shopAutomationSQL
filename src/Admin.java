import com.sun.security.jgss.GSSUtil;

import java.util.Scanner;

public class Admin extends Main{
    private static final Scanner in = new Scanner(System.in);
    public static void mainMenuAdmins(){
        System.out.println("1 - View products in storage\n2 - replenish storage");
        int userAnswer = in.nextByte();
        if (userAnswer == 1){
            viewProductsInStorage();
        } else if (userAnswer == 2) {
            replenishStorage();
        }
        else {
            System.out.println("Something is not correct");
            mainMenuAdmins();
        }
    }

    public static void viewProductsInStorage(){
        System.out.println("Apples in storage - " + storageDbSQL.getApplesInStorage() +
                "Bananas in storage - " + storageDbSQL.getBananasInStorage());
        mainMenuAdmins();
    }

    public static void replenishStorage(){
        System.out.println("1 - Apples\n2 - Bananas\nYour choose: ");
        int userChoose = in.nextByte();
        if (userChoose == 1){
        } else if (userChoose == 2) {
        }
    }

    static class storageDbSQL{
        public static int getApplesInStorage(){
            return 0;
        }

        public static int getBananasInStorage(){
            return 0;
        }
    }
}
