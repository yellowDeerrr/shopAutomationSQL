package facebook;

import java.sql.*;
import java.util.Scanner;

public class RegisterUserAndLoginUser extends mainMenuFacebook{
    public static void registerUser(){
        Scanner in = new Scanner(System.in);

        System.out.print("What's your name: ");
        String name = in.nextLine();

        System.out.print("Surname: ");
        String surname = in.nextLine();

        System.out.print("How old are you: ");
        int age = in.nextInt();
        while (true){
            System.out.print("Think up login: ");
            String userInputLogin = in.nextLine();

            if (facebookRegisterAndLoginDB.checkUserInputLoginRegister(userInputLogin)){
                System.out.println("Login is already using");
            }else {
                facebookAllUsersInfo.addUserInDB(name, surname, age);
            }
        }
    }

    public static void loginUser(){
        Scanner in = new Scanner(System.in);

        System.out.print("Enter login: ");
        String userInputLogin = in.nextLine();

        System.out.print("Enter password: ");
        String userInputPassword = in.nextLine();

        if (facebookRegisterAndLoginDB.checkLoginAndPasswordInDBForLoginInAccount(userInputLogin, userInputPassword)){
            System.out.println("Welcome");
        }else{
            System.out.println("Something is not correct");
            loginUser();
        }
    }

    private static class facebookRegisterAndLoginDB{
        private final static String jdbcURL = "jdbc:mysql://localhost:3306/facebookRegisterAndLoginDB";
        private final static String userName = "root";
        private final static String password = "root";

        private Connection connection;

        private facebookRegisterAndLoginDB() {
            try {
                connection = DriverManager.getConnection(jdbcURL, userName, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Connection getConnection() {
            return connection;
        }

        public static boolean checkUserInputLoginRegister(String login){
            Scanner in = new Scanner(System.in);

            facebookRegisterAndLoginDB facebookRegisterAndLoginDB = new facebookRegisterAndLoginDB();

            try {
                Statement statement = facebookRegisterAndLoginDB.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("select * from users where userlogin = ('"+login+"')");
                return resultSet.next();
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }

        public static boolean checkLoginAndPasswordInDBForLoginInAccount(String login, String password){
            facebookRegisterAndLoginDB facebookRegisterAndLoginDB = new facebookRegisterAndLoginDB();

            try {
                Statement statement = facebookRegisterAndLoginDB.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("select * from users where userLogin = ('"+login+"') and userPassword = ('"+password+"')");
                return resultSet.next();
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }

    private static class facebookAllUsersInfo{
        private final static String jdbcURL = "jdbc:mysql://localhost:3306/facebookAllUsersDB";
        private final static String userName = "root";
        private final static String password = "root";

        private Connection connection;

        private facebookAllUsersInfo() {
            try {
                connection = DriverManager.getConnection(jdbcURL, userName, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Connection getConnection() {
            return connection;
        }

        public static void addUserInDB(String name, String surname, int age){
            facebookAllUsersInfo facebookAllUsersInfo = new facebookAllUsersInfo();
            try {
                Statement statement = facebookAllUsersInfo.getConnection().createStatement();
                statement.executeUpdate("insert into users (name, surname, age) values (('"+name+"'), ('"+surname+"'), ('"+age+"'))");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
