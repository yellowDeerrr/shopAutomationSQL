package facebook;

import java.sql.*;
import java.util.Scanner;

public class RegisterUserAndLoginUser extends mainMenuFacebook{
    private static String name;
    private static String surname;
    private static int age;

    public static void registerUser(){
        Scanner in = new Scanner(System.in);

        System.out.print("What's your name: ");
        name = in.nextLine();

        System.out.print("Surname: ");
        surname = in.nextLine();

        System.out.print("How old are you: ");
        age = in.nextInt();

        userInputLogin();
    }

    private static void userInputLogin(){
        Scanner in = new Scanner(System.in);

        System.out.print("Think up login: ");
        String userInputLogin = in.nextLine();

        facebookRegisterAndLoginDB.checkUserInputLogin(userInputLogin);
    }
    public static void loginUser(){

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

        public static void checkUserInputLogin(String login){
            Scanner in = new Scanner(System.in);

            facebookRegisterAndLoginDB facebookRegisterAndLoginDB = new facebookRegisterAndLoginDB();

            try {
                Statement statement = facebookRegisterAndLoginDB.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("select * from users where userlogin = ('"+login+"')");
                if (resultSet.next()){
                    System.out.println("Login is already using");
                    userInputLogin();
                }else{
                    while (true){
                        System.out.print("Think up password: ");
                        String password = in.nextLine();

                        System.out.print("Repeat password: ");
                        String repeatPassword = in.nextLine();

                        if (password.equals(repeatPassword)){
                            statement.executeUpdate("insert into users (userLogin, userPassword) values (('"+login+"'), ('"+password+"'))");
                            facebookAllUsersInfo.addUserInDB(name, surname, age);
                            break;
                        }else {
                            System.out.println("Error");
                            continue;
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
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
