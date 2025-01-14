import exceptions.InvalidLoginException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

public class Professors extends User implements Common{

    private static final HashMap<String,Professors> registeredProfessors=new HashMap<>();
    ArrayList<Course> courses=new ArrayList<>();

    Professors(String UserName, String Password) {
        super(UserName, Password);
        courses=new ArrayList<>();
        registeredProfessors.put(UserName,this);
    }

    public void viewCourses() {
        System.out.println("COURSES: ");
        for (int i=0;i<courses.size();i++) {
            System.out.println(i+" "+courses.get(i).courseName);
        }
        System.out.println();
    }

    public boolean manageCourses(){
        Scanner sc = new Scanner(System.in);
        System.out.println("To view your courses press 1");
        System.out.println("To update your courses press 2");
        System.out.println("To read feedback press 3");
        System.out.println("To exit press anything else");
        System.out.println("\nEnter your choice: ");
        String input = sc.nextLine();
        if (input.equals("1")) {
            if (!courses.isEmpty()) {
                viewCourses();
            }
            else{
                System.out.println("\nYou have no courses");
            }
        }
        else if (input.equals("2")) {
            if (!courses.isEmpty()) {
                viewCourses();
                System.out.print("\nInput index of the course you want to update: ");
                int index = sc.nextInt();
                if (index<courses.size() && index>=0) {
                    courses.get(index).update();
                }
                else{
                    System.out.println("Invalid index");
                }
            }
            else{
                System.out.println("\nYou have no courses");
            }
        }
        else if (input.equals("3")) {
            if (!courses.isEmpty()) {
                viewCourses();
                System.out.print("\nInput index of the course you want to read feedback for: ");
                int index = sc.nextInt();
                try{
                    courses.get(index).readFeedback();
                }
                catch (Exception e){
                    System.out.println("Invalid index");
                }
            }
            else{
                System.out.println("\nYou have no courses");
            }
        }
        else{
            return false;
        }
        return true;
    }

    private void displayEnrolledStudents(){
        System.out.println("List of students with username, cgpa, contact details");
        for (Course c : courses) {
            System.out.println("Registered students in course "+c.courseName+": ");
            for (Map.Entry<Student, String> k : c.students.entrySet()) {
                Student s=k.getKey();
                System.out.println(s.UserName+":  "+s.cgpa+"  "+s.contactDetail);
            }
            System.out.println();
        }
        System.out.println();
    }


    public static boolean checkDetails(String username, String password) throws InvalidLoginException {
        if (registeredProfessors.containsKey(username) && registeredProfessors.get(username).Password.equals(password)){
            return true;
        }
        throw new InvalidLoginException("Invalid Username or Password");
    }

    public static boolean usernameAvailable(String username) {
        return !registeredProfessors.containsKey(username);
    }

    public static Professors getRegisteredProfessor(String username) {
        return registeredProfessors.get(username);
    }

    @Override
    public void displayFunc() {
        System.out.println("To manage your courses press 1");
        System.out.println("To view enrolled Students press 2");
        System.out.println("To Logout press anything else");
    }

    @Override
    public void intrfce() {
        Scanner sc = new Scanner(System.in);
        while (true){
            displayFunc();
            String toDo=sc.nextLine();
            switch (toDo){
                case "1":
                    while (manageCourses()){}
                    break;
                case "2":
                    displayEnrolledStudents();
                    break;
                default:
                    this.logout(this);
                    return;
            }
        }
    }
}
