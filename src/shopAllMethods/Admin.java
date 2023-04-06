package shopAllMethods;
import static bankSystemSQLAllMethods.monoBank.*;

import java.sql.*;
import java.sql.ResultSet;
import java.util.Scanner;

import java.time.LocalDate;
// Timestamp timestamp = new Timestamp(System.currentTimeMillis());

public class Admin{
    private static final Scanner in = new Scanner(System.in);

    public static void mainMenuAdmins(){
        System.out.println("1 - View products in storage\n2 - Replenish storage\n3 - User menu");
        int userAnswer = in.nextByte();
        if (userAnswer == 1){
            viewProductsInStorage();
        } else if (userAnswer == 2) {
            replenishStorage();
        } else if (userAnswer == 3) {
            User.mainMenuUser();
        } else {
            System.out.println("Something is not correct");
            mainMenuAdmins();
        }
    }

    public static void viewProductsInStorage(){
        System.out.println("Apples in storage - " + storageDbSQLForAdmin.getApplesInStorage() +
                "\nBananas in storage - " + storageDbSQLForAdmin.getBananasInStorage());
        mainMenuAdmins();
    }

    public static void replenishStorage(){
        System.out.println("1 - Apples\n2 - Bananas\nYour choose: ");
        int userChoose = in.nextByte();
        if (userChoose == 1){
            System.out.println("How much replenish: ");
            int howMuchReplenish = in.nextInt();
            storageDbSQLForAdmin.replenishStorageOfApples(howMuchReplenish);
        } else if (userChoose == 2) {
            System.out.println("How much replenish: ");
            int howMuchReplenish = in.nextInt();
            storageDbSQLForAdmin.replenishStorageOfBananas(howMuchReplenish);
        }
    }

    static class storageDbSQLForAdmin{
        private final static String jdbcURL = "jdbc:mysql://localhost:3306/shopStorage";
        private final static String userName = "root";
        private final static String password = "root";

        private Connection connection;

        public storageDbSQLForAdmin(){
            try {
                connection = DriverManager.getConnection(jdbcURL, userName, password);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public Connection getConnection() {
            return connection;
        }

        public static int getApplesInStorage(){
            storageDbSQLForAdmin sql = new storageDbSQLForAdmin();
            try{
                Statement statement = sql.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("select howMuchInStorage from applesInStorage");
                if (resultSet.next()){
                    return resultSet.getInt(1);
                }else {
                    System.out.println("Error");
                    return -1;
                }
            }catch (SQLException e){
                e.printStackTrace();
                return -1;
            }
        }

        public static int getBananasInStorage(){
            storageDbSQLForAdmin sql = new storageDbSQLForAdmin();
            try{
                Statement statement = sql.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("select howMuchInStorage from bananasInStorage");
                if (resultSet.next()){
                    return resultSet.getInt(1);
                }else {
                    System.out.println("Error");
                    return -1;
                }
            }catch (SQLException e){
                e.printStackTrace();
                return -1;
            }
        }


        public static void replenishStorageOfApples(int howMuchReplenishStorageOfApples){
            storageDbSQLForAdmin sql = new storageDbSQLForAdmin();

            try{
                Statement statement = sql.getConnection().createStatement();
                LocalDate localDate = LocalDate.now();
                int inStorage = storageDbSQLForAdmin.getApplesInStorage();
                int sum = howMuchReplenishStorageOfApples + inStorage;
                statement.executeUpdate("update applesInStorage set howMuchInStorage = ('"+sum+"'), lastDeliver = ('"+localDate+"'), dateValid = ('"+localDate+"')");
                System.out.println("Replenish successful");
                mainMenuAdmins();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void replenishStorageOfBananas(int howMuchReplenishStorageOfApples){
            storageDbSQLForAdmin sql = new storageDbSQLForAdmin();

            try{
                Statement statement = sql.getConnection().createStatement();
                LocalDate localDate = LocalDate.now();
                int inStorage = storageDbSQLForAdmin.getBananasInStorage();
                int sum = howMuchReplenishStorageOfApples + inStorage;
                statement.executeUpdate("update bananasInStorage set howMuchInStorage = ('"+sum+"'), lastDeliver = ('"+localDate+"'), dateValid = ('"+localDate+"')");
                System.out.println("Replenish successful");
                mainMenuAdmins();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void convertToPriceApples(int howMuchUserWant){
            storageDbSQLForAdmin sql = new storageDbSQLForAdmin();
            Scanner inn = new Scanner(System.in);

            String query = "select * from applesInStorage";
            try{
                Statement statement = sql.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()){
                    int sum = resultSet.getInt(2) * howMuchUserWant;
                    System.out.print("Sum: " + sum + "\n1 - Pay\n2 - No\nYour choose: ");
                    int userAnswer = in.nextInt();
                    if (userAnswer == 1){
                        System.out.print("Enter your number card: ");
                        String userNumberCard = inn.nextLine();

                        System.out.print("Enter PIN-CODE: ");
                        int PinCode = inn.nextInt();

                        monoBankDBForShopping.payByCard(userNumberCard, PinCode, sum, "apple");
                    } else if (userAnswer == 2) {
                        User.mainMenuUser();
                    }else {
                        System.out.println("Something is not correct");
                        User.mainMenuUser();
                    }
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
