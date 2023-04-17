import java.util.Scanner;

import bankSystemSQLAllMethods.monoBank;
import shopAllMethods.mainMenu;
import facebook.mainMenuFacebook;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        System.out.println("1 - Bank\n2 - Shop\n3 - Facebook\nYour choose: ");
        int userAnswer = in.nextInt();
        if (userAnswer == 1){
            monoBank.mainMenu();
        } else if (userAnswer == 2) {
            mainMenu.mainMenu();
        } else if (userAnswer == 3) {
            mainMenuFacebook.mainMenuForUserRegisterOrLogin();
        }else{
            System.out.println("Error");
            main(args);
        }
    }
}
