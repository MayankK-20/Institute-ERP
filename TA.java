import java.util.ArrayList;
import java.util.Scanner;

public class TA extends Student{

    public ArrayList<Course> courses=new ArrayList();

    public TA() {
        super();
    }

    public void TACourse(Course c) {
        courses.add(c);
    }

    @Override
    public void displayFunc(){
        System.out.println("To use TA functions press 1");
        System.out.println("To use Student functions press anything else");
    }

    @Override
    public void intrfce(){
        Scanner sc = new Scanner(System.in);
        while (true){
            displayFunc();
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                assist();
            } else {
                return;
            }
        }
    }

    public void assist(){
        Scanner sc = new Scanner(System.in);
        if (courses.isEmpty()){
            System.out.println("You have no courses");
            return;
        }
        for (int i=0;i<courses.size();i++){
            System.out.println(i+". "+courses.get(i).courseName);
        }
        System.out.println();
        System.out.print("Input Index of course you want to assist Professor for: ");
        Course c;
        try{
            int index = sc.nextInt();
            c = courses.get(index);
        }
        catch (Exception e){
            System.out.println("Index not valid");
            return;
        }
        System.out.println("Index, Student Name, Grade");
        c.assignGrade();
    }

    @Override
    public String toString() {
        return "TA [courses=" + courses + "]";
    }


}