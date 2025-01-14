import exceptions.CourseFullException;
import exceptions.DropDeadlinePassedException;
import exceptions.InvalidLoginException;

import javax.lang.model.type.NullType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Student extends User implements Common{
    private int _semester=1;
    public int cgpa=0;
    public String contactDetail="9181828281";
    public int creditThisSemester=0;
    public ArrayList<Course> coursesCompleted=new ArrayList<>();
    public ArrayList<Course> ongoingCourses=new ArrayList<>();
    public ArrayList<Integer> sgpa=new ArrayList<>(8);
    public static final HashMap<String,Student> registeredStudents=new HashMap<>();
    public ArrayList<Complaint> complaintsSubmitted=new ArrayList<>();
    public TA isTA=null;

    Student(String Username, String Password){
        super(Username, Password);
        registeredStudents.put(Username, this);
    }

    Student(){}

    Student(String Username, String Password, int _semester) {
        super(Username, Password);
        this._semester = _semester;
        registeredStudents.put(Username, this);
    }

    public void setCgpa(int cgpa) {
        this.cgpa = cgpa;
    }

    public void setContactDetail(String contactDetail) {
        this.contactDetail = contactDetail;
    }

    public void setCreditThisSemester(int creditThisSemester) {
        this.creditThisSemester = creditThisSemester;
    }

    public void setSgpa(int i, int sgpa) {
        while (this.sgpa.size()<i){
            this.sgpa.add(0);
        }
        this.sgpa.set(i-1,sgpa);
    }

    @Override
    public String toString() {
        return "Student{" +
                "semester=" + _semester +
                ", cgpa=" + cgpa +
                ", contactDetail='" + contactDetail + '\'' +
                ", creditThisSemester=" + creditThisSemester +
                ", coursesCompleted=" + coursesCompleted +
                ", ongoingCourses=" + ongoingCourses +
                ", sgpa=" + sgpa +
                '}';
    }

    public void dropCourse() throws DropDeadlinePassedException{
        if (ongoingCourses.isEmpty()){
            System.out.println("No course to drop");
            return;
        }
        for (int i=0;i<ongoingCourses.size();i++){
            System.out.println(i+". "+ongoingCourses.get(i).courseName);
        }
        System.out.print("Input index of course to drop: ");
        Scanner sc=new Scanner(System.in);
        int input=sc.nextInt();
        sc.nextLine();
        if (LocalDate.now().isAfter(ongoingCourses.get(input).dropDeadline)){
            throw new DropDeadlinePassedException("\nThe deadline to drop this course has passed\n");
        }
        ongoingCourses.get(input).students.remove(this);
        ongoingCourses.remove(input);
    }

    public int get_semester() {
        return _semester;
    }
    public void set_semester(int _semester) {
        this._semester = _semester;
    }

    public static boolean checkDetails(String username, String password) throws InvalidLoginException {
        if (registeredStudents.containsKey(username) && registeredStudents.get(username).Password.equals(password)){
            return true;
        }
        throw new InvalidLoginException("Invalid Username or Password");
    }

    public static Student getRegisteredStudent(String username) {
        return registeredStudents.get(username);
    }

    public static boolean usernameAvailable(String username) {
        return !registeredStudents.containsKey(username);
    }

    @Override
    public void displayFunc() {
        System.out.println("To view Available Courses press 1");
        System.out.println("To register for Course press 2");
        System.out.println("To view Schedule press 3");
        System.out.println("To drop course press 4");
        System.out.println("To get academic progress press 5");
        System.out.println("To submit or check status of Complaint press 6");
        System.out.println("If semester completed press 7");
        System.out.println("To check your own information press 8");
        System.out.println("To give a course feedback press 9");
        System.out.println("To exit press anything else");
    }

    @Override
    public void intrfce() {
        Scanner sc = new Scanner(System.in);
        while(true){
            if (isTA!=null){
                isTA.intrfce();
            }
            displayFunc();
            String toDo=sc.nextLine();
            switch (toDo){
                case "1":
                    Semester.viewSem(this._semester);
                    break;
                case "2":
                    try{
                        registerCourse();
                    }
                    catch(CourseFullException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    viewSchedule();
                    break;
                case "4":
                    try{
                        dropCourse();
                    }
                    catch (DropDeadlinePassedException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "5":
                    grade();
                    break;
                case "6":
                    complaint();
                    break;
                case "7":
                    calcGrade();
                    break;
                case "8":
                    System.out.println(this);
                    break;
                case "9":
                    giveFeedback();
                    break;
                default:
                    this.logout(this);
                    return;
            }
        }
    }

    public void giveFeedback(){
        Scanner sc = new Scanner(System.in);
        if (coursesCompleted.isEmpty()){
            System.out.println("No courses Completed to give feedback for");
            return;
        }
        for (int i=0; i<coursesCompleted.size();i++){
            System.out.println(i+". "+coursesCompleted.get(i).courseName);
        }
        System.out.println("Input index of course to give feedback for: ");
        int input=sc.nextInt();
        sc.nextLine();
        System.out.println("Input 1 if you are giving rating (integer), 2 if Comment, anything else to exit");
        String i=sc.nextLine();
        switch (i){
            case "1":
                System.out.print("Input rating: ");
                int feedback=sc.nextInt();
                Feedback<Integer> fb=new Feedback<>(feedback);
                coursesCompleted.get(input).courseFeedback.add(fb);
                break;
            case "2":
                System.out.print("Input Comment: ");
                String feed=sc.nextLine();
                Feedback<String> f = new Feedback(feed);
                coursesCompleted.get(input).courseFeedback.add(f);
                break;
        }
    }

    public void calcGrade(){
        int thisSem=0;
        int cred=0;
        for (int i=0; i<ongoingCourses.size();i++){
            cred+=ongoingCourses.get(i).credits;
            try {
                int grad=Integer.parseInt(ongoingCourses.get(i).students.get(this));
                if (grad<5){
                    System.out.println("You failed this sem");
                    return;
                }
                thisSem += ongoingCourses.get(i).credits * grad;
            } catch (Exception e) {
                System.out.println(ongoingCourses.get(i).courseName+" grade not assigned");
                return;
            }
        }
        try{
            sgpa.add(thisSem/cred);
            _semester++;
            creditThisSemester=0;
            coursesCompleted.addAll(ongoingCourses);
            ongoingCourses.clear();
        }
        catch(Exception e){
            System.out.println("credits are 0 please put a complaint");
            return;
        }
        int cg=0;
        for (int i=0; i<sgpa.size(); i++){
            cg+=sgpa.get(i);
        }
        this.cgpa=(cg/(sgpa.size()));
    }

    public void checkStatus(){
        if (complaintsSubmitted.isEmpty()){
            System.out.println("No complaints submitted");
            return;
        }
        for (Complaint complaint : complaintsSubmitted) {
            System.out.println(complaint);
        }
    }

    public void complaint(){
        System.out.println("To check status of complaint press 1");
        System.out.println("To submit complaint press 2");
        System.out.println("To exit press anything else");
        System.out.print("Input choice: ");
        Scanner sc=new Scanner(System.in);
        String choice=sc.nextLine();
        if (choice.equals("1")){
            checkStatus();
        }
        else if (choice.equals("2")){
            System.out.println("Input Query: ");
            String query=sc.nextLine();
            Complaint c=new Complaint(query);
            complaintsSubmitted.add(c);
        }
    }

    public void registerCourse() throws CourseFullException {
        System.out.print("Input semester you want to choose course from: ");
        Scanner sc=new Scanner(System.in);
        int input=sc.nextInt();
        sc.nextLine();
        if (input>_semester){
            System.out.println("You cannot choose courses from this semester");
            return;
        }
        Semester.viewSem(input);
        System.out.print("Choose index of course you want to register (0 based indexing): ");
        int index=sc.nextInt();
        sc.nextLine();
        Course c=Semester.sem.get(input-1).thisSem.get(index);
        if (c.credits+creditThisSemester>20){
            System.out.println("Overload not allowed");
            return;
        }
        if (checkPrerequisites(c)){
            if (c.enrolledStudents==c.enrollmentLimit){
                throw new CourseFullException("\nCourse is Full\n");
            }
            c.students.put(this,"Not Assigned");
            ongoingCourses.add(c);
            creditThisSemester+=c.credits;
            c.enrolledStudents++;
        }
        else{
            System.out.println("Prerequisites not achieved");
        }
    }

    public boolean checkPrerequisites(Course c){
        for (int i=0; i<c.prerequisites.size(); i++){
            for (int j=0; j<this.coursesCompleted.size(); j++){
                if (c.prerequisites.get(i).equals(this.coursesCompleted.get(i).courseName)){
                    break;
                }
                if (j==this.coursesCompleted.size()-1){
                    return false;
                }
            }
            if (this.coursesCompleted.isEmpty()){
                return false;
            }
        }
        return true;
    }

    public void viewSchedule(){
        for (Course ongoingCours : ongoingCourses) {
            System.out.println(ongoingCours.courseName);
            try{
                System.out.println(ongoingCours.professor.UserName);
            }
            catch(Exception e){
                System.out.println("Professor not assigned");
            }
            ongoingCours.viewTimings();
        }
    }

    public void grade(){
        System.out.println("CGPA: "+cgpa);
        System.out.println("SGPA: ");
        for (int i=0; i<sgpa.size(); i++){
            System.out.println((i+1)+". "+sgpa.get(i));
        }
        for (Course ongoingCourse : ongoingCourses) {
            System.out.println(ongoingCourse.courseName+"  "+ongoingCourse.students.get(this));
        }
        for (Course course : coursesCompleted){
            System.out.println(course.courseName+"  "+course.students.get(this));
        }
    }

    public void makeTA(Course c){
        if (isTA==null){
            isTA=new TA();
        }
        isTA.TACourse(c);
    }
}
