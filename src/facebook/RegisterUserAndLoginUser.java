package facebook;

import java.sql.*;
import java.util.Scanner;

import static facebook.RegisterUserAndLoginUser.facebookRegisterAndLoginDB.checkUserInputLoginRegister;

public class RegisterUserAndLoginUser extends mainMenuFacebook{

    private static String name;
    private static String surname;
    private static int age;
    private static String sex;
    private static int avatar;

    public static void registerUser(){
        Scanner in = new Scanner(System.in);

        System.out.print("What's your name: ");
        name = in.nextLine();

        System.out.print("Surname: ");
        surname = in.nextLine();

        System.out.print("How old are you: ");
        age = in.nextInt();

        System.out.print("1 - Man\n2 - Women\nYour choose: ");
        int userInputSex = in.nextInt();
        if (userInputSex == 1) sex = "Man";
        else if (userInputSex == 2) sex = "Women";
        else {
            System.out.println("Error");
            registerUser();
        };

        System.out.println("Choose avatar\n1 - https://imgur.com/a/R6kTcrM\nYour choose: ");
        avatar = in.nextInt();

        inputLogin();
    }

    public static void inputLogin(){
        Scanner in = new Scanner(System.in);
        System.out.print("Think up login: ");
        String userInputLogin = in.nextLine();
        if (checkUserInputLoginRegister(userInputLogin)){
            while (true){
                System.out.print("Think up password: ");
                String userInputPassword = in.nextLine();

                System.out.print("Repeat password: ");
                String repeatPassword = in.nextLine();

                if (userInputPassword.equals(repeatPassword)) {
                    facebookAddUserDeleteUserOrChangeProfile.addUserInDB(name, surname, age, sex, avatar, userInputLogin, userInputPassword);
                    facebookAddUserDeleteUserOrChangeProfile.setLogin(userInputLogin);
                    mainMenuAfterRegister.mainMenu();
                    break;
                }else{
                    System.out.println("Repeat password is not correct");
                }

            }
        }else {
            inputLogin();
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
            mainMenuAfterRegister.mainMenu();
        }else{
            System.out.println("Something is not correct");
            loginUser();
        }
    }

    static class facebookRegisterAndLoginDB{
        private final static String jdbcURL = "jdbc:mysql://localhost:3306/facebookAllUsersDB";
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

                if (resultSet.next()){
                    System.out.println("Login is already using");
                    return false;
                }else {
                    return true;
                }

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

    static class facebookAddUserDeleteUserOrChangeProfile{
        private final static String jdbcURL = "jdbc:mysql://localhost:3306/facebookAllUsersDB";
        private final static String userName = "root";
        private final static String password = "root";

        private static String userLogin;

        private Connection connection;



        private facebookAddUserDeleteUserOrChangeProfile() {
            try {
                connection = DriverManager.getConnection(jdbcURL, userName, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Connection getConnection() {
            return connection;
        }
        public static void setLogin(String login){ userLogin = login; viewProfile();}

        public static void viewProfile(){
            facebookAddUserDeleteUserOrChangeProfile facebookAddUserDeleteUserOrChangeProfile = new facebookAddUserDeleteUserOrChangeProfile();

            try{
                Statement statement = facebookAddUserDeleteUserOrChangeProfile.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("select * from users where userLogin = ('"+userLogin+"')");

                if (resultSet.next()){
                    String userName = resultSet.getString(2);
                    String userSurname = resultSet.getString(3);
                    String userAge = resultSet.getString(4);
                    String userSex = resultSet.getString(5);
                    int avatar = resultSet.getInt(6);

                    String avatarURL;
                    if (avatar == 1)
                        avatarURL = "https://imgur.com/a/R6kTcrM";
                    else
                        avatarURL = "nothing";

                    if (userSex == null)
                        userSex = "not specified";

                    System.out.print("Name: " + userName +
                            "\nSurname: " + userSurname +
                            "\nAge: " + userAge +
                            "\nSex: " + userSex +
                            "\nAvatar: " + avatarURL +
                            "\n");
                }else {
                    System.out.println("Error in system");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public static void addUserInDB(String name, String surname, int age, String sex, int avatar, String login, String password){
            facebookAddUserDeleteUserOrChangeProfile facebookAllUsersInfo = new facebookAddUserDeleteUserOrChangeProfile();
            try {
                Statement statement = facebookAllUsersInfo.getConnection().createStatement();
                statement.executeUpdate("insert into users (name, surname, age, sex, avatar, userLogin, userPassword) values (('"+name+"'), ('"+surname+"'), ('"+age+"'), ('"+sex+"'), ('"+avatar+"'), ('"+login+"'), ('"+password+"'))");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public static void deleteUserFromDB(){
            System.out.println();
        }

        public static void changeProfile(){

        }
    }
}
