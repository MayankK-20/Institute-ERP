    import java.util.Scanner;

    public class Main {
        public static void main(String[] args) {
            System.out.println("\n\nINDRAPRASTHA INSTITUTE of INFORMATION TECHNOLOGY DELHI");
            System.out.println("**********************************************************\n");

//will be used in the demo only created objects not assigned anything.
//in front of TA first assign professor to courses then select courses from student and then if needed assign grade and move on to next semester.
//password for the administrator is 12345678.
            for (int i=0; i<8; i++){
                Semester sem = new Semester();
            }
            Student s1=new Student("s1","1");
            Student s2=new Student("s2","2");
            Student s3=new Student("s3","3");
            Professors p1=new Professors("p1","1");
            Professors p2=new Professors("p2","2");
            Course c1=new Course("IP","101",1);
            Course c2=new Course("LA","102",1);
            Course c3=new Course("AP","103",3);
            Course c4=new Course("DSA","104",2);
            Course c5=new Course("DC","105",1);

            String choice;
            Scanner sc = new Scanner(System.in);
            boolean exit = false;
            while (!exit){
                System.out.println("\nTo Login press 1");
                System.out.println("To Signup press 2");
                System.out.println("To Exit press anything else");
                System.out.print("\nEnter your choice: ");
                choice=sc.nextLine();
                switch (choice){
                    case "1":
                        while(User.login()){}
                        break;
                    case "2":
                        while(User.signUp()){}
                        break;
                    default:
                        exit=true;
                }
            }
            System.out.println("\nThank you for using our system.");
            System.out.println("**********************************************************\n\n");
        }
    }
