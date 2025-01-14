import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Course {

    static ArrayList<Course> courses=new ArrayList<>();

    public String courseName;
    public String courseCode;
    public int credits=4;
    public ArrayList<String> prerequisites=new ArrayList<>();
    public ArrayList<String> classTimings=new ArrayList<>();
    public String syllabus;
    public int enrollmentLimit=1000;
    public String officeHours;
    Professors professor;
    public HashMap<Student,String> students=new HashMap<>();
    public int semester;
    public int enrolledStudents=0;
    public LocalDate dropDeadline;
    public ArrayList<Feedback<?>> courseFeedback=new ArrayList<>();
    public ArrayList<Student> ta=new ArrayList<>();

    static Scanner sc = new Scanner(System.in);


    Course(String courseName, String courseCode, int semester){
        this.courseName=courseName;
        this.courseCode=courseCode;
        this.semester=semester;
        semester--;
        Semester.sem.get(semester).thisSem.add(this);
        courses.add(this);
        dropDeadline = LocalDate.now();
    }

    public void setSyllabus() {
        System.out.println("Current syllabus: "+this.syllabus);
        System.out.print("\nEnter Syllabus: ");
        this.syllabus = sc.nextLine();
    }

    public void setEnrollmentLimit() {
        System.out.println("Current Enrollment Limit: "+this.enrollmentLimit);
        System.out.print("\nEnter Enrollment Limit: ");
        this.enrollmentLimit = sc.nextInt();
        sc.nextLine();
    }

    public void setCredits() {
        System.out.println("Current Credits: "+this.credits);
        System.out.print("\nEnter Credits: ");
        this.credits = sc.nextInt();
        sc.nextLine();
    }

    public void setOfficeHours() {
        System.out.println("Current Office Hours: "+this.officeHours);
        System.out.print("\nEnter Office Hours: ");
        this.officeHours = sc.nextLine();
    }

    public boolean setClassTimings(){
        System.out.println("Current Class Timings:");
        for (int i=0; i<this.classTimings.size(); i++){
            System.out.println(i+" "+this.classTimings.get(i));
        }
        System.out.println("To remove Class Timings press 1");
        System.out.println("To add Class Timings press 2");
        System.out.println("To exit press anything else");
        String input=sc.nextLine();
        switch (input){
            case "1":
                System.out.print("\nEnter index to remove: ");
                classTimings.remove(sc.nextInt());
                break;
            case "2":
                System.out.print("\nEnter Class Timings: ");
                String newTiming=sc.nextLine();
                classTimings.add(newTiming);
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean setPrerequisites(){
        System.out.println("Current Prerequisites:");
        for (int i=0; i<this.prerequisites.size(); i++){
            System.out.println(i+" "+this.prerequisites.get(i));
        }
        System.out.println("To remove Prerequisite press 1");
        System.out.println("To add Prerequisite press 2");
        System.out.println("To exit press anything else");
        String input=sc.nextLine();
        switch (input){
            case "1":
                System.out.print("\nEnter index to remove: ");
                try{
                    int ind=sc.nextInt();
                    sc.nextLine();
                    prerequisites.remove(ind);
                }
                catch(Exception e){
                    System.out.println("Invalid index");
                }
                break;
            case "2":
                System.out.print("\nEnter Prerequisite: ");
                prerequisites.add(sc.nextLine());
                break;
            default:
                return false;
        }
        return true;
    }

    public void setProfessor(Professors professor) {
        System.out.println("Assigned professor");
        this.professor = professor;
    }

    public void update(){
        while (true){
            System.out.println("To update Syllabus press 1");
            System.out.println("To update Enrollment limit press 2");
            System.out.println("To update Class Timings press 3");
            System.out.println("To update prerequisites press 4");
            System.out.println("To update credits press 5");
            System.out.println("To update office hours press 6");
            System.out.println("To assign or view grade of student press 7");
            System.out.println("To assign drop deadline press 8");
            System.out.println("To assign a student as TA press 9");
            System.out.println("To remove TA press 10");
            System.out.println("To exit press anything else");
            System.out.print("\nEnter choice: ");
            String choice = sc.nextLine();
            switch(choice){
                case "1":
                    setSyllabus();
                    break;
                case "2":
                    setEnrollmentLimit();
                    break;
                case "3":
                    while (setClassTimings()){}
                    break;
                case "4":
                    while (setPrerequisites()){}
                    break;
                case "5":
                    setCredits();
                    break;
                case "6":
                    setOfficeHours();
                    break;
                case "7":
                    assignGrade();
                    break;
                case "8":
                    setDropDeadline();
                    break;
                case "9":
                    System.out.println("Input username of the student you want to assign as TA: ");
                    String username=sc.nextLine();
                    if (!Student.registeredStudents.containsKey(username)){
                        System.out.println("Invalid username");
                        break;
                    }
                    Student.getRegisteredStudent(username).makeTA(this);
                    ta.add(Student.getRegisteredStudent(username));
                    break;
                case "10":
                    if (ta.isEmpty()){
                        System.out.println("No TA's to remove");
                        break;
                    }
                    for (int i=0; i<ta.size(); i++){
                        System.out.println(i+". "+ta.get(i).UserName);
                    }
                    System.out.print("\n Input index of TA to remove: ");
                    int index=sc.nextInt();
                    sc.nextLine();
                    try{
                        Student st = ta.get(index);
                        if (st.isTA!=null){
                            if (st.isTA.courses.size()==1){
                                st.isTA=null;
                            }
                            else{
                                st.isTA.courses.remove(this);
                            }
                            ta.remove(index);
                        }
                    }
                    catch(Exception e){
                        System.out.println("Invalid index");
                    }
                    break;
                default:
                    return;
            }
        }
    }

    public void assignGrade(){
        if (students.isEmpty()){
            System.out.println("No students in the course");
            return;
        }
        int index=0;
        for (Student student : students.keySet()) {
            System.out.println(index+". "+student.UserName+"  "+students.get(student));
            index++;
        }
        System.out.println("To exit press 1; To continue to assign grade press anything else");
        if (sc.nextLine().equals("1")){
            return;
        }
        System.out.println("Input index of student you want to assign grade: ");
        int input=sc.nextInt();
        sc.nextLine();
        System.out.print("Input grade of student (0-10) (below 5 is fail): ");
        String grade=sc.nextLine();
        index=0;
        for (Student student : students.keySet()) {
            if (index==input){
                students.put(student,grade);
                break;
            }
            index++;
        }
    }

    public void adminUpdate(){
        while (true){
            System.out.println("To update Syllabus press 1");
            System.out.println("To update Enrollment limit press 2");
            System.out.println("To update Class Timings press 3");
            System.out.println("To update prerequisites press 4");
            System.out.println("To update credits press 5");
            System.out.println("To update office hours press 6");
            System.out.println("To assign professor to course press 7");
            System.out.println("To assign or view grade of student press 8");
            System.out.println("To assign drop deadline press 9");
            System.out.println("To assign TA to the course press 10");
            System.out.println("To remove TA press 11");;
            System.out.println("To exit press anything else");
            System.out.print("\nEnter choice: ");
            String choice = sc.nextLine();
            switch(choice){
                case "1":
                    setSyllabus();
                    break;
                case "2":
                    setEnrollmentLimit();
                    break;
                case "3":
                    while (setClassTimings()){}
                    break;
                case "4":
                    while (setPrerequisites()){}
                    break;
                case "5":
                    setCredits();
                    break;
                case "6":
                    setOfficeHours();
                    break;
                case "7":
                    System.out.print("Input Username of Professor: ");
                    String name=sc.nextLine();
                    if (!Professors.usernameAvailable(name)){
                        setProfessor(Professors.getRegisteredProfessor(name));
                        Professors.getRegisteredProfessor(name).courses.add(this);
                    }
                    else{
                        System.out.println("Invalid Username");
                    }
                    break;
                case "8":
                    assignGrade();
                    break;
                case "9":
                    setDropDeadline();
                    break;
                case "10":
                    System.out.println("Input username of the student you want to assign as TA: ");
                    String username=sc.nextLine();
                    if (!Student.registeredStudents.containsKey(username)){
                        System.out.println("Invalid username");
                        break;
                    }
                    Student.getRegisteredStudent(username).makeTA(this);
                    ta.add(Student.getRegisteredStudent(username));
                    break;
                case "11":
                    if (ta.isEmpty()){
                        System.out.println("No TA's to remove");
                        break;
                    }
                    for (int i=0; i<ta.size(); i++){
                        System.out.println(i+". "+ta.get(i).UserName);
                    }
                    System.out.print("\n Input index of TA to remove: ");
                    int index=sc.nextInt();
                    sc.nextLine();
                    try{
                        Student st = ta.get(index);
                        if (st.isTA!=null){
                            if (st.isTA.courses.size()==1){
                                st.isTA=null;
                            }
                            else{
                                st.isTA.courses.remove(this);
                            }
                            ta.remove(index);
                        }
                    }
                    catch(Exception e){
                        System.out.println("Invalid index");
                    }
                    break;
                default:
                    return;
            }
        }
    }

    public void setDropDeadline() {
        Scanner sc=new Scanner(System.in);
        while (true){
            System.out.print("Enter drop deadline (yyyy-mm-dd): ");
            String date=sc.nextLine();
            try{
                LocalDate d=LocalDate.parse(date);
                this.dropDeadline=d;
                System.out.println("Drop deadline set to "+d);
                return;
            }
            catch(Exception e){
                System.out.println("Invalid date format");
            }
        }
    }

    public static void viewCourse(){
        if (courses.isEmpty()){
            System.out.println("No courses");
            return;
        }
        System.out.println("Courses: ");
        for (int i=0; i<courses.size(); i++){
            System.out.println(i+". "+courses.get(i).courseName+" "+courses.get(i).courseCode);
        }
        System.out.println();
    }

    @Override
    public String toString() {
        String info=this.courseName+" "+this.courseCode+"\n";
        info+="Prerequisites:\n";
        for (int i=0; i<prerequisites.size(); i++){
            info+=prerequisites.get(i)+"\n";
        }
        info+="ClassTimings:\n";
        for (int i=0; i<classTimings.size(); i++){
            info+=classTimings.get(i)+"\n";
        }
        info+="Credits: "+credits+"\n";
        info+="Semester: "+semester+"\n";
        if (professor==null){
            info+="Professor is not assigned\n";
            return info;
        }
        info+="Professor: "+professor.UserName+"\n";
        return info;
    }

    public static void addCourse(){
        System.out.print("Input name of course: ");
        String name=sc.nextLine();
        System.out.print("Input course code: ");
        String code=sc.nextLine();
        System.out.print("Input semester (from 1-8): ");
        int semester=sc.nextInt();
        sc.nextLine();
        Course newCourse=new Course(name,code,semester);
        newCourse.adminUpdate();
    }

    public static void removeCourse(){
        viewCourse();
        if (courses.isEmpty()){
            return;
        }
        System.out.print("Enter index of course to remove: ");
        int index=sc.nextInt();
        Semester.removeCourse(courses.get(index), courses.get(index).semester);
        courses.remove(index);
    }

    public void viewTimings(){
        for (int i=0; i<this.classTimings.size(); i++){
            System.out.println(i+". "+this.classTimings.get(i)+"\n");
        }
        System.out.println();
    }

    public void readFeedback(){
        if (courses.isEmpty()){
            System.out.println("No Feedback");
            return;
        }
        System.out.println("Feedback: ");
        for (Feedback<?> fb: courseFeedback){
            System.out.println(fb.toString());
        }
    }
}
