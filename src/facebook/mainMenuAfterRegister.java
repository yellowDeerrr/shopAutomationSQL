package facebook;

import java.sql.*;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class mainMenuAfterRegister {
    public static void mainMenu() {
        Scanner in = new Scanner(System.in);

        System.out.print("1 - View my profile\n2 - View profile others\n3 - Play game\nYour choose: ");
        int userAnswer = in.nextInt();

        if (userAnswer == 1) {
            RegisterUserAndLoginUser.facebookAddUserDeleteUserOrChangeProfile.viewProfile();
            mainMenu();
        } else if (userAnswer == 2) {
            viewOtherProfileMenu();
        } else if (userAnswer ==3 ) {
            game.gameMenu();
        } else {
            System.out.println("Error");
            mainMenuAfterRegister mainMenuAfterRegister = new mainMenuAfterRegister();
        }
    }

        public static void viewOtherProfileMenu(){
        Scanner in = new Scanner(System.in);

        System.out.print("Name other person: ");
        String nameOtherPerson = in.nextLine();

        System.out.print("Surname: ");
        String surNameOtherPerson = in.nextLine();
        facebookAllInfoAboutUsers.searchOthersProfile(nameOtherPerson, surNameOtherPerson);
        }
    }
    class facebookAllInfoAboutUsers{
        private final static String jdbcURL = "jdbc:mysql://localhost:3306/facebookAllUsersDB";
        private final static String userName = "root";
        private final static String password = "root";

        private static String userLogin;
        private Connection connection;

        private facebookAllInfoAboutUsers(){
            try{
                connection = DriverManager.getConnection(jdbcURL, userName, password);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        private Connection getConnection(){ return connection; }

        static void searchOthersProfile(String name, String surname) {
            Scanner in = new Scanner(System.in);
            facebookAllInfoAboutUsers facebookAllInfoAboutUsers = new facebookAllInfoAboutUsers();

            try {
                Statement statement = facebookAllInfoAboutUsers.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = statement.executeQuery("select * from users where name = ('"+name+"') and surname = ('"+surname+"')");

                if (resultSet.isBeforeFirst()) {
                    if (resultSet.last()){
                        int rows = resultSet.getRow() + 1;
                        resultSet.beforeFirst();

                        int[] acounts = new int[rows];
                        int i = 0;
                        while (resultSet.next()) {
                            i++;

                            int id = resultSet.getInt(1);
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
                            acounts[i] = id;
                            System.out.print("\n\n" +
                                    i + " - person" +
                                    "\nName: " + userName +
                                    "\nSurname: " + userSurname +
                                    "\nAge: " + userAge +
                                    "\nSex: " + userSex +
                                    "\nAvatar: " + avatarURL +
                                    "\n");
                            TimeUnit.SECONDS.sleep((long) 1.5);
                        }
                        System.out.print("\n\nYour choose: ");
                        int userAnswer = in.nextInt();
                        int idAcountSearch = acounts[userAnswer];

                        viewOtherProfile(idAcountSearch);
                    }


                }else{
                    System.out.println("Nothing");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }


        private static void viewOtherProfile(int idAcount){
            facebookAllInfoAboutUsers facebookAllInfoAboutUsers = new facebookAllInfoAboutUsers();

            try{
                Statement statement = facebookAllInfoAboutUsers.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("select * from users where id = ('"+idAcount+"')");

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
    }

