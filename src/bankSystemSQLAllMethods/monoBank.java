package bankSystemSQLAllMethods;

import shopAllMethods.Admin;
import shopAllMethods.User;

import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;
// Timestamp timestamp = new Timestamp(System.currentTimeMillis());

public class monoBank extends Main {
    private static String userNumber;
    private static String userNumberCard;

    public monoBank(){
        Scanner in = new Scanner(System.in);

        System.out.println("1 - Create new card Mono\n2 - View card\n3 - Trade money to another card\n4 - History of operations\n5 - Back\n6 - Main menu");
        int userAnswerMainMenu = in.nextInt();
        if (userAnswerMainMenu == 1)
            createNewCardMono();
        else if (userAnswerMainMenu == 2)
            viewAllInfoAboutCardMono();
        else if (userAnswerMainMenu == 3) {
            checkNumberAndCardUserAndCardToTrade();
        } else if (userAnswerMainMenu == 4) {
            viewHistoryOfOperations();
        } else if (userAnswerMainMenu == 5) {
            Main.onlineCardMonoOrPrivat24();
        } else if (userAnswerMainMenu == 6)
            mainMenu();
        else {
            System.out.println("Error, try again");
            monoBank monoBank = new monoBank();
        }
    }
    static class ATMForMonoBank {
        public static void ATMBankMono() {
            Scanner in = new Scanner(System.in);

            System.out.print("1 - Replenish card\n2 - Withdraw money\n3 - Back\nYour choose: ");
            int userAnswer = in.nextInt();
            if (userAnswer == 1) {
                monoBank.ATMForMonoBank.replenishCard();
            }
            else if (userAnswer == 2) {
                monoBank.ATMForMonoBank.withdrawMoneyFromMono();
            }
            else if (userAnswer == 3){
                ATM();
            }
            else{
                System.out.println("Error, try again");
                ATMBankMono();
            }

        }

        private static void withdrawMoneyFromMono() {
            Scanner in = new Scanner(System.in);

            System.out.print("Enter your phone number: \n+380-");
            String number = in.nextLine();

            System.out.print("Enter your card number(with space): ");
            String numberCard = in.nextLine();

            System.out.println("Enter PIN-CODE: ");
            int PinCode = in.nextInt();

            MonoBankDB.withdrawMoneyFromMono(number, numberCard, PinCode);
        }

        private static void replenishCard(){
            Scanner in = new Scanner(System.in);
            System.out.print("Enter your phone number: \n+380-");
            String answerNumber = in.nextLine();

            System.out.print("Enter your card number(without space): ");
            String answerNumberCard = in.nextLine();

            MonoBankDB.replenishCardDB(answerNumber, answerNumberCard);
        }
    }

    static class MonoBankDB{

        private final static String jdbcURL = "jdbc:mysql://localhost:3306/bankSystem";
        private final static String userName = "root";
        private final static String password = "root";


        private Connection connection;

        public static void checkNumberInDB(String number){
            MonoBankDB BankDB = new MonoBankDB();

            try{
                Statement statement = BankDB.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("select * from users where exists (select userNumber from users where userNumber = ('"+number+"'))");
                if (resultSet.next()){
                    System.out.println("Number is already using");
                    createNewCardMono();
                }
                else{
                    userNumber = number;
                    randomNumberCard();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        public static void tradeMoneyFromMonoToMono(String number, String numberCard, int PinCode, String numberCardToTrade){
            Scanner in = new Scanner(System.in);
            MonoBankDB BankDB = new MonoBankDB();
            MonoBankDBUsersHistory monoBankDBUsersHistory = new MonoBankDBUsersHistory();

            try{
                Statement statement = BankDB.getConnection().createStatement();
                Statement sts = monoBankDBUsersHistory.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("select * from users where userNumber = ('"+number+"') and userNumberCard = ('"+numberCard+"') and PinCode = ('"+PinCode+"')");
                System.out.println();
                if (resultSet.next()){
                    int colum1 = resultSet.getInt(5);
                    resultSet = statement.executeQuery("select * from users where userNumberCard = ('"+numberCardToTrade+"')");
                    if (resultSet.next()){
                        int colum2 = resultSet.getInt(5);
                        System.out.print("Your balance: " + colum1 + "\nHow much you want trade: ");
                        int howMuch = in.nextInt();
                        if (colum1 >= howMuch){
                            int sum = colum1 - howMuch;
                            int sumCardToTrade = colum2 + howMuch;
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            System.out.println(timestamp);
                            statement.executeUpdate("update users set userMoney = ('"+sum+"') where userNumber = ('"+number+"') and userNumberCard = ('"+numberCard+"')");
                            statement.executeUpdate("update users set userMoney = ('"+sumCardToTrade+"') where userNumberCard = ('"+numberCardToTrade+"')");
                            sts.executeUpdate("insert into cardHistory_" + numberCard + " (sum, whom, time) values (('"+(-howMuch)+"'), ('"+numberCardToTrade+"'), ('"+timestamp+"'))");
                            sts.executeUpdate("insert into cardHistory_" + numberCardToTrade + " (sum, whom, time) values (('"+howMuch+"'), ('"+numberCardToTrade+"'), ('"+timestamp+"'))");
                            System.out.println("Operation successful");
                            viewAllInfoAboutUserCard(number, numberCard, PinCode);
                        }
                        else{
                            System.out.println("You don't have a lot of money, try again");
                            tradeMoneyFromMonoToMono(number, numberCard, PinCode, numberCardToTrade);
                        }
                    }
                }
                else{
                    System.out.println("Something is not correct, try again");
                    checkNumberAndCardUserAndCardToTrade();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        public static void randomNumberCard(){
            int max = 9999;
            int min = 1000;
            int range = max - min + 1;

            String random1 = Integer.toString((int)(Math.random() * range) + min);
            String random2 = Integer.toString((int)(Math.random() * range) + min);
            String random3 = Integer.toString((int)(Math.random() * range) + min);
            String random4 = Integer.toString((int)(Math.random() * range) + min);

            userNumberCard = random1 + random2 + random3 + random4;
            String userNumberCardForUser = random1 + " " + random2 + " " + random3 + " " + random4;

            System.out.println(userNumberCard);

            MonoBankDB BankDB = new MonoBankDB();

            String query = "select * from users where exists (select userNumber from users where userNumberCard = ('"+userNumberCard+"'))";
            try{
                Statement statement = BankDB.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()){
                    randomNumberCard();
                }
                else {
                    System.out.println("Your number card is " + userNumberCardForUser + " (раджу записати)");
                    addUserInDB();
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        public static void addUserInDB(){
            MonoBankDB BankDB = new MonoBankDB();
            MonoBankDBUsersHistory monoBankDBUsersHistory = new MonoBankDBUsersHistory();
            Scanner in = new Scanner(System.in);

            String userNumberCardStr = String.valueOf(userNumberCard);

            String sql = "CREATE TABLE cardHistory_" + userNumberCardStr +
                    " (id INT NOT NULL AUTO_INCREMENT, " +
                    "PRIMARY KEY (id), " +
                    "sum INT NOT NULL, " +
                    "whom VARCHAR(50) NOT NULL, " +
                    "time TIMESTAMP)";
            try {
                Statement sts = monoBankDBUsersHistory.getConnection().createStatement();
                Statement statement = BankDB.getConnection().createStatement();

                while (true){
                    System.out.print("Think up PIN-CODE: ");
                    int PinCode = in.nextInt();
                    if (PinCode > 9999 || PinCode < 1000){
                        System.out.println("Enter four numbers");
                    }

                    else {
                        statement.executeUpdate("insert into users (userNumber, userNumberCard, PinCode) values (('"+userNumber+"'), ('"+userNumberCard+"'), ('"+PinCode+"'))");
                        System.out.println("User added");

                        ResultSet resultSet = statement.executeQuery("select * from users where userNumber = ('"+userNumber+"') and userNumberCard = ('"+userNumberCard+"')");

                        if (resultSet.next()){
                            sts.executeUpdate(sql);

                            System.out.println("Your number: " + resultSet.getString(2)+ "\nYour number card: " + resultSet.getString(3) +
                                    "\nYour PIN-CODE: " + resultSet.getInt(4) + "\nYour balance: " + resultSet.getInt(5));
                            System.out.println("Main menu - 1\nATM - 2\nYour choose: ");
                            int answer = in.nextInt();
                            if (answer == 1){
                                mainMenu();
                                break;
                            } else if (answer == 2) {
                                ATM();
                                break;
                            }
                        }
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public static void replenishCardDB(String numberCheck, String numberCardCheck){
            MonoBankDB BankDB = new MonoBankDB();
            MonoBankDBUsersHistory monoBankDBUsersHistory = new MonoBankDBUsersHistory();
            Scanner in = new Scanner(System.in);

            String query = "select userNumber, userNumberCard, userMoney from users where exists (select userNumber, userNumberCard from users where userNumberCard = ('"+numberCardCheck+"')) and userNumber = ('"+numberCheck+"')";
            try{
                Statement statement = BankDB.getConnection().createStatement();
                Statement sts = monoBankDBUsersHistory.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()){
                    int userMoney = Integer.parseInt(resultSet.getString(3));
                    System.out.println("Your balance: " + userMoney);
                    System.out.print("How much replenish: ");
                    int userWantReplenish = in.nextInt();
                    int sum = userWantReplenish + userMoney;
                    statement.executeUpdate("update users set userMoney = ('"+sum+"') where userNumber = ('"+numberCheck+"') and userNumberCard = ('"+numberCardCheck+"')");
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String ATM = "ATM";
                    sts.executeUpdate("insert into cardHistory_" + numberCardCheck + " (sum, whom, time) values (('"+(userWantReplenish)+"'), ('"+ATM+"'), ('"+timestamp+"'))");
                    System.out.println("Operation successful");
                    mainMenu();
                }
                else {
                    System.out.println("Something is not correct");
                    ATMForMonoBank.replenishCard();
                }

            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void withdrawMoneyFromMono(String numberCheck, String numberCardCheck, int checkPinCode){
            MonoBankDB BankDB = new MonoBankDB();
            MonoBankDBUsersHistory monoBankDBUsersHistory = new MonoBankDBUsersHistory();
            Scanner in = new Scanner(System.in);

            String query = "select userNumber, userNumberCard, userMoney from users where exists (select userNumber, userNumberCard from users where userNumberCard = ('"+numberCardCheck+"')) and userNumber = ('"+numberCheck+"') and PinCode = ('"+checkPinCode+"')";
            try{
                Statement statement = BankDB.getConnection().createStatement();
                Statement sts = monoBankDBUsersHistory.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()){
                    int userMoney = resultSet.getInt(3);
                    System.out.println("Your balance: " + userMoney);
                    System.out.print("How much withdraw: ");
                    int userWantReplenish = in.nextInt();
                    int sum = userMoney - userWantReplenish;
                    if (sum < 0){
                        System.out.println("You don't have a lot of money");
                        withdrawMoneyFromMono(numberCheck, numberCardCheck, checkPinCode);
                    }else {
                        statement.executeUpdate("update users set userMoney = ('"+sum + "') where userNumber = ('" + numberCheck + "') and userNumberCard = ('" + numberCardCheck + "')");
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        String ATM = "ATM";
                        sts.executeUpdate("insert into cardHistory_" + numberCardCheck + " (sum, whom, time) values (('"+(-userWantReplenish)+"'), ('"+ATM+"'), ('"+timestamp+"'))");
                        System.out.println("Operation successful");
                        mainMenu();
                    }
                }
                else {
                    System.out.println("Something is not correct");
                    ATMForMonoBank.withdrawMoneyFromMono();
                }

            }catch (SQLException e) {
                e.printStackTrace();
            }
        }


        public static void viewAllInfoAboutUserCard(String number, String numberCard, int PinCode){
            MonoBankDB BankDB = new MonoBankDB();
            Scanner in = new Scanner(System.in);

            String query = "select userNumber, userNumberCard, userMoney from users where exists (select * from users where userNumberCard = ('"+numberCard+"')) and userNumber = ('"+number+"') and PinCode = ('"+PinCode+"')";
            try{
                Statement statement = BankDB.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()){
                    System.out.println("Your number: " + resultSet.getString(1) + "\nYour number card: " + resultSet.getString(2) + "\nYour balance: " + resultSet.getString(3));
                    TimeUnit.SECONDS.sleep(3);
                    while (true){
                        System.out.println("Main menu - 1\nATM - 2\nYour choose: ");
                        int answer = in.nextInt();
                        if (answer == 1){
                            mainMenu();
                            break;
                        } else if (answer == 2) {
                            ATM();
                            break;
                        }
                        else
                            System.out.println("Error");
                    }
                }
                else {
                    System.out.println("Something is not correct");
                    monoBank.viewAllInfoAboutCardMono();
                }

            }catch (SQLException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public MonoBankDB(){
            try {
                connection = DriverManager.getConnection(jdbcURL, userName, password);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public Connection getConnection() {
            return connection;
        }
    }

    static class MonoBankDBUsersHistory {
        private final static String jdbcURL = "jdbc:mysql://localhost:3306/bankUsersHistory";
        private final static String userName = "root";
        private final static String password = "root";

        private Connection connection;

        public MonoBankDBUsersHistory() {
            try {
                connection = DriverManager.getConnection(jdbcURL, userName, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Connection getConnection() {
            return connection;
        }

        public static void viewHistoryOfCard(String number, String numberCard, int PinCode){
            MonoBankDBUsersHistory monoBankDBUsersHistory = new MonoBankDBUsersHistory();
            MonoBankDB monoBankDB = new MonoBankDB();
            Scanner in = new Scanner(System.in);

            String query = "select * from cardHistory_" + numberCard ;
            try {
                Statement statement = monoBankDBUsersHistory.getConnection().createStatement();
                Statement sts = monoBankDB.getConnection().createStatement();
                ResultSet resultSet = sts.executeQuery("select * from users where userNumberCard = ('"+numberCard+"') and userNumber = ('"+number+"') and PinCode = ('"+PinCode+"')");
                if (resultSet.next()) {
                    resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        while(resultSet.next()) {
                            int id = resultSet.getInt(1);
                            int sum = resultSet.getInt(2);
                            String whom = resultSet.getString(3);
                            String time = resultSet.getString(4);
                            TimeUnit.SECONDS.sleep((long) 2.5);
                            System.out.println("id: " + id + "\nSum: " + sum + "\nWhom: " + whom + "\ntime: " + time + "\n\n");
                        }
                        System.out.println("first operation doesn't out(");
                        mainMenu();
                    }
                    else
                        System.out.println("No");
                } else {
                    System.out.println("Nothing info about trade or number card, number is not correct");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class monoBankDBForShopping{
        public static void payByCard(String userNumberCard, int PinCode, int sumHowMuchUserWantToBuy, String product){
            MonoBankDB monoBankDB = new MonoBankDB();
            MonoBankDBUsersHistory monoBankDBUsersHistory = new MonoBankDBUsersHistory();

            String query = "select * from users where userNumberCard = ('"+userNumberCard+"') and PinCode = ('"+PinCode+"')";
            try {
                Statement statement = monoBankDB.getConnection().createStatement();
                Statement sts = monoBankDBUsersHistory.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()){

                    int result = resultSet.getInt(5);
                    if (result > sumHowMuchUserWantToBuy){
                        int sum = result - sumHowMuchUserWantToBuy;
                        statement.executeUpdate("update users set userMoney = ('"+sum+"') where userNumberCard = ('"+userNumberCard+"') and PinCode = ('"+PinCode+"')");
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        String Shop = "Shop, " + product;
                        sts.executeUpdate("insert into cardHistory_" + userNumberCard + " (sum, whom, time) values (('"+(-sumHowMuchUserWantToBuy)+"'), ('"+Shop+"'), ('"+timestamp+"'))");
                        System.out.println("Operation successful");
                        User.mainMenuUser();
                    }
                    else {
                        System.out.println("You don't have a lot of money");
                        User.mainMenuUser();
                    }
                }
                else {
                    System.out.println("Something is not correct");
                    User.mainMenuUser();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void createNewCardMono(){
        Scanner in = new Scanner(System.in);

        System.out.print("Enter your phone number +380-");
        userNumber = in.nextLine();
        if (userNumber.length() == 9){
            MonoBankDB.checkNumberInDB(userNumber);
        }
        else {
            System.out.println("Not correct phone number, try again");
            createNewCardMono();
        }
    }


    public static void viewAllInfoAboutCardMono() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter your phone number +380-");
        String viewAllInfoUserNumber = in.nextLine();

        System.out.print("Enter your card number(without space): ");
        String viewAllInfoUserNumberCard = in.nextLine();

        System.out.print("Enter PIN-CODE: ");
        int PinCode = in.nextInt();

        System.out.println(viewAllInfoUserNumber);
        System.out.println(viewAllInfoUserNumberCard);
        MonoBankDB.viewAllInfoAboutUserCard(viewAllInfoUserNumber, viewAllInfoUserNumberCard, PinCode);
    }

    public static void viewHistoryOfOperations(){
        Scanner in = new Scanner(System.in);

        System.out.print("Enter your phone number +380-");
        String userNumber = in.nextLine();

        System.out.print("Enter your card number(without space): ");
        String userNumberCard = in.nextLine();

        System.out.print("Enter PIN-CODE: ");
        int PinCode = in.nextInt();

        MonoBankDBUsersHistory.viewHistoryOfCard(userNumber, userNumberCard, PinCode);
    }

    public static void checkNumberAndCardUserAndCardToTrade() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter your phone number +380-");
        String userNumber = in.nextLine();

        System.out.print("Enter your card number(without space): ");
        String userNumberCard = in.nextLine();

        System.out.print("Enter PIN-CODE: ");
        int PinCode = in.nextInt();

        System.out.print("Enter card number to trade(without space): ");
        String userNumberCardToTrade = in.nextLine();

        MonoBankDB.tradeMoneyFromMonoToMono(userNumber, userNumberCard, PinCode, userNumberCardToTrade);
    }
}
