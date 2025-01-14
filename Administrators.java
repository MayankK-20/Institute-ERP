import exceptions.InvalidLoginException;

import java.util.ArrayList;
import java.util.Scanner;

public class Administrators extends User implements Common{

    public ArrayList<Course> courses;


    private static final String _password="12345678";
    Administrators(String username, String password) {
        super(username, password);
    }

    public static boolean checkDetails(String password) throws InvalidLoginException {
        if (password.equals(_password)){
            return true;
        }
        throw new InvalidLoginException("Invalid password");
    }

    @Override
    public void displayFunc() {
        System.out.println("\nAdministrators Functionalities:");
        System.out.println("To manage Course Catalog Press 1");
        System.out.println("To manage Student Records Press 2");
        System.out.println("To assign Professors to courses Press 3");
        System.out.println("To handle Complaints Press 4");
        System.out.println("To logout from the system Press anything else");
    }

    public boolean courseCatalog(){
        Scanner sc=new Scanner(System.in);
        System.out.println("To view Courses press 1");
        System.out.println("To add Course press 2");
        System.out.println("To delete Course press 3");
        System.out.println("To update Course press 4");
        System.out.println("To exit press anything else");
        System.out.print("\nInput Choice: ");
        String toDo=sc.nextLine();
        if (toDo.equals("1")){
            Course.viewCourse();
        }
        else if (toDo.equals("2")){
            Course.addCourse();
        }
        else if (toDo.equals("3")){
            Course.removeCourse();
        }
        else if (toDo.equals("4")){
            Course.viewCourse();
            System.out.println("Input index of course to Update");
            try{
                Course.courses.get(sc.nextInt()).adminUpdate();
            }
            catch(IndexOutOfBoundsException e){
                System.out.println("Invalid index");
            }
        }
        else{
            return false;
        }
        return true;
    }

    public void AssignProf(){
        Scanner sc=new Scanner(System.in);
        Course.viewCourse();
        System.out.print("Enter index: ");
        int index=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Username of the professor: ");
        String username=sc.nextLine();
        if (!Professors.usernameAvailable(username)){
            Course.courses.get(index).setProfessor(Professors.getRegisteredProfessor(username));
            Professors.getRegisteredProfessor(username).courses.add(Course.courses.get(index));
        }
        else{
            System.out.println("Invalid Username");
        }
    }

    @Override
    public void intrfce(){
        Scanner sc=new Scanner(System.in);
        while (true){
            displayFunc();
            System.out.print("\nInput Choice: ");
            String input=sc.nextLine();
            switch(input){
                case "1":
                    while (courseCatalog()){}
                    break;
                case "2":
                  studentRecords();
                    break;
                case "3":
                    AssignProf();
                    break;
                case "4":
                    Complaint.answerComplaint();
                    break;
                default:
                    this.logout(this);
                    return;
            }
        }
    }

    public void displayStudents(){
        for (String s:Student.registeredStudents.keySet()){
            System.out.println(s);
        }
    }

    public void studentRecords(){
        Scanner sc=new Scanner(System.in);
        displayStudents();
        System.out.print("Input username of student you want to view or update records: ");
        String index=sc.nextLine();
        Student s=Student.registeredStudents.get(index);
        System.out.println("To view Students Record Press 1");
        System.out.println("To update Student Records Press 2");
        System.out.println("To exit press anything else");
        System.out.print("\nInput Choice: ");
        String input=sc.nextLine();
        if (input.equals("1")){
            System.out.println(s);
        }
        else if (input.equals("2")){
            updateRecords(s);
        }
    }

    public void updateRecords(Student s){
        while(true){
            System.out.println(s);
            System.out.println("To update contact details press 1");
            System.out.println("To update cgpa press 2");
            System.out.println("To update sgpa press 3");
            System.out.println("To update credit this semester press 4");
            System.out.println("To update current semester press 5");
            System.out.println("To exit press anything else");
            System.out.print("\nInput Choice: ");
            Scanner sc=new Scanner(System.in);
            String input=sc.nextLine();
            if (input.equals("1")){
                System.out.print("Enter new contact detail: ");
                String inp=sc.nextLine();
                s.setContactDetail(inp);
            }
            else if (input.equals("2")){
                System.out.print("Enter new cgpa: ");
                int inp=sc.nextInt();
                sc.nextLine();
                s.setCgpa(inp);
            }
            else if (input.equals("3")){
                System.out.println("Enter semester you want to update(1-8): ");
                int inp=sc.nextInt();
                sc.nextLine();
                System.out.print("Enter new sgpa: ");
                int sg=sc.nextInt();
                sc.nextLine();
                s.setSgpa(inp, sg);
            }
            else if (input.equals("4")){
                System.out.print("Enter new credit this semester: ");
                int inp=sc.nextInt();
                sc.nextLine();
                s.setCreditThisSemester(inp);
            }
            else if (input.equals("5")){
                System.out.print("Enter new current semester: ");
                int inp=sc.nextInt();
                sc.nextLine();
                s.set_semester(inp);
            }
            else {
                return;
            }
        }
    }
}
