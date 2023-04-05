import java.util.Scanner;

public class User extends Admin{
    private static final Scanner in = new Scanner(System.in);
    public static void mainMenuUser(){
        System.out.println("What to buy\n1 - Apples\n2 - Bananas");
        int userAnswer = in.nextInt();

        if (userAnswer == 1){
            System.out.println("How much you want to buy apples: ");
            int howMuchUserWantToBuyApples = in.nextInt();
            if(storageDbSQLForUser.checkHowMuchUserWantToBuyApples(howMuchUserWantToBuyApples)){
                System.out.println("Operation successful");
                mainMenuUser();
            }else {
                System.out.println("We don't have a lot of apples in storage");
                mainMenuUser();
            }
        }


        else if (userAnswer == 2) {
            System.out.println("How much you want to buy bananas: ");
            int howMuchUserWantToBuyBananas = in.nextInt();
            if(storageDbSQLForUser.checkHowMuchUserWantToBuyBananas(howMuchUserWantToBuyBananas)){
                System.out.println("Operation successful");
                mainMenuUser();
            }else {
                System.out.println("We don't have a lot of bananas in storage");
                mainMenuUser();
            }
        }else {
            System.out.println("Something is not correct");
            mainMenuUser();
        }
    }
}
