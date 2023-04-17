package facebook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class RegisterUserAndLoginUser extends mainMenuFacebook{
    public static void registerUser(){
        Scanner in = new Scanner(System.in);

        System.out.print("What's your name: ");
        String userName = in.nextLine();

        System.out.println("Surname: ");
        String userSurname = in.nextLine();

        System.out.println("");
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

        public Connection getConnection() {
            return connection;
        }

    }
}
