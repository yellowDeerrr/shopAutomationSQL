package shopAllMethods;

import bankSystemSQLAllMethods.monoBank;
import static bankSystemSQLAllMethods.monoBank.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        System.out.println("1 - Admin\n2 - User");
        int userAnswer = in.nextByte();
        if (userAnswer == 1){
            Admin.mainMenuAdmins();
        } else if (userAnswer == 2) {
            System.out.println("You enter in shop");
            User.mainMenuUser();
        }else {
            System.out.println("Something is not correct");;
        }
    }
}