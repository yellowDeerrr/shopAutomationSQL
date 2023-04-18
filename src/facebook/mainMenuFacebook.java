package facebook;

import java.util.Scanner;

import static facebook.RegisterUserAndLoginUser.*;

public class mainMenuFacebook {
    public static void mainMenuForUserRegisterOrLogin(){
        Scanner in = new Scanner(System.in);

        System.out.print("1 - Register\n2 - Login\nYour choose: ");
        int userAnswer = in.nextInt();
        if (userAnswer == 1){
            registerUser();
        } else if (userAnswer == 2) {
            loginUser();
        }else{
            System.out.println("Error");
            mainMenuForUserRegisterOrLogin();
        }
    }
}
