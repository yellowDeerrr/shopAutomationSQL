package shopAllMethods;
import static bankSystemSQLAllMethods.monoBank.*;

import java.util.Scanner;

public class User extends Admin{
    public static void mainMenuUser(){
        Scanner in = new Scanner(System.in);
        System.out.println("What to buy\n1 - Apples (price 5 for one)\n2 - Bananas (price 8 for one)");
        int userAnswer = in.nextInt();

        if (userAnswer == 1){
            System.out.println("How much you want to buy apples: ");
            int howMuchUserWantToBuyApples = in.nextInt();
            if (Admin.storageDbSQLForAdmin.getApplesInStorage() > howMuchUserWantToBuyApples){
                Admin.storageDbSQLForAdmin.convertToPriceApples(howMuchUserWantToBuyApples);
            }else {
                System.out.println("We don't have a lot of apples");
            }
        }


        else if (userAnswer == 2) {
            System.out.println("How much you want to buy bananas: ");
            int howMuchUserWantToBuyBananas = in.nextInt();
        } else {
            System.out.println("Something is not correct");
            mainMenuUser();
        }
    }
}
