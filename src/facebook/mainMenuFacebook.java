package facebook;

import java.util.Scanner;

import static facebook.RegisterUserAndLoginUser.*;

public class mainMenuFacebook {
    public static void mainMenuForUserRegisterOrLogin(){
        Scanner in = new Scanner(System.in);

        System.out.print("1 - Register\n2 - Login\nYour choose: ");
        if (in.nextInt() == 1){
            registerUser();
        } else if (in.nextInt() == 2) {
            loginUser();
        }else{
            System.out.println("Error");
            mainMenuForUserRegisterOrLogin();
        }
    }
}
