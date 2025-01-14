import exceptions.InvalidLoginException;

import java.util.Scanner;

abstract class User {

    protected String UserName;
    protected String Password;

    public User(){}

    public User(String UserName, String Password) {
        this.UserName = UserName;
        this.Password = Password;
    }

    public static boolean login(){
        Common current;
        Scanner sc = new Scanner(System.in);
        System.out.println("\nTo Login as Student press 1");
        System.out.println("To Login as Professor press 2");
        System.out.println("To Login as Administrator press 3");
        System.out.println("To Exit press any other number");
        System.out.print("\nEnter your choice: ");
        String type = sc.nextLine();
        String username = "Administrator";
        String password;
        switch (type) {
            case "3":
                System.out.print("Input Password: ");
                password = sc.nextLine();
                try{
                    Administrators.checkDetails(password);
                }
                catch (InvalidLoginException e){
                    System.out.println(e.getMessage());
                    break;
                }
                System.out.println("Logged in");
                current = new Administrators(username, password);
                current.intrfce();
                break;
            case "2":
                System.out.print("Input Username: ");
                username = sc.nextLine();
                System.out.print("Input Password: ");
                password = sc.nextLine();
                try {
                    Professors.checkDetails(username, password);
                }
                catch (InvalidLoginException e){
                    System.out.println(e.getMessage());
                    break;
                }
                System.out.println("Logged in");
                Professors.getRegisteredProfessor(username).intrfce();
                break;
            case "1":
                System.out.print("Input Username: ");
                username = sc.nextLine();
                System.out.print("Input Password: ");
                password = sc.nextLine();
                try{
                    Student.checkDetails(username, password);
                }
                catch(InvalidLoginException e){
                    System.out.println(e.getMessage());
                    break;
                }
                System.out.println("Logged in");
                Student.getRegisteredStudent(username).intrfce();
                break;
            default:
                System.out.println("Exiting\n");
                return false;
        }
        return true;
    }

    public static boolean signUp(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nTo Sign Up as Student press 1");
        System.out.println("To Sign Up as Professor press 2");
        System.out.println("To Exit press any other number");
        System.out.print("\nEnter your choice: ");
        String type = sc.nextLine();
        String username;
        String password;
        switch (type) {
            case "2":
                System.out.print("Input Username: ");
                username = sc.nextLine();
                System.out.print("Input Password: ");
                password = sc.nextLine();
                if (Professors.usernameAvailable(username)) {
                    System.out.println("Logged in");
                    Professors newProf = new Professors(username, password);
                    newProf.intrfce();
                } else {
                    System.out.println("Username is already in use");
                }
                break;
            case "1":
                System.out.print("Input Username: ");
                username = sc.nextLine();
                System.out.print("Input Password: ");
                password = sc.nextLine();
                if (Student.usernameAvailable(username)) {
                    System.out.println("Logged in");
                    Student newStudent = new Student(username, password);
                    newStudent.intrfce();
                } else {
                    System.out.println("Username is already in use");
                }
                break;
            default:
                System.out.println("Exiting\n");
                return false;
        }
        return true;
    }

    //Runtime Polymorphism
    public void logout(User user){
        System.out.println("Logging out "+user.UserName);
    }

}
