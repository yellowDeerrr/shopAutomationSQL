import java.util.Scanner;

public class Main{
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args){
        mainMenu();
    }

    public static void mainMenu(){
        System.out.println("Internation bank system\n1 - ATM\n2 - Online card\n3 - Exit");
        int userChoose = in.nextInt();
        if (userChoose == 1)
            ATM();
        else if (userChoose == 2){
            onlineCardMonoOrPrivat24();
        } else if (userChoose == 3) {
            System.out.println(" ");
        }else {
            System.out.println("Something is not correct");
            mainMenu();
        }
    }

    public static void onlineCardMonoOrPrivat24(){
        System.out.print("1 - Mono\n2 - Privat24\nYour choose: ");
        int userBankChoose = in.nextInt();

        if (userBankChoose == 1) {
            monoBank monoBank = new monoBank();
        }
        else if (userBankChoose == 2)
            System.out.println("1 - Create new card Privat24\n2 - View card\n3 - Back\n4 - Main menu");

        else
            System.out.println("Error, try again");
    }

        public static void ATM(){
        System.out.print("1 - Mono\n2 - Privat24\n3 - Online card\nYour choose: ");
        int userChooseMonoOrPrivat24 = in.nextInt();
        if (userChooseMonoOrPrivat24 == 1){
            monoBank.ATMForMonoBank.ATMBankMono();
        } else if (userChooseMonoOrPrivat24 == 3) {
            onlineCardMonoOrPrivat24();
        } else{
            System.out.println("Error");
            ATM();
        }

    }


}