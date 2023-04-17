package shopAllMethods;

import bankSystemSQLAllMethods.monoBank;

import java.util.Scanner;

public class mainMenu {
    public static void mainMenu(){
        Scanner in = new Scanner(System.in);

        System.out.println("1 - Admin\n2 - User\n3 - Bank");
        int userAnswer = in.nextByte();
        if (userAnswer == 1){
            Admin.mainMenuAdmins();
        } else if (userAnswer == 2) {
            System.out.println("You enter in shop");
            User.mainMenuUser();
        } else if (userAnswer == 3) {
            monoBank.mainMenu();
        } else {
            System.out.println("Something is not correct");;
        }
    }
}