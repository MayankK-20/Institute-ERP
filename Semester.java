import java.util.ArrayList;

public class Semester {
    public static ArrayList<Semester> sem=new ArrayList<>();
    public ArrayList<Course> thisSem=new ArrayList<>(); {}

    Semester(){
        sem.add(this);
    }

    public static void viewSem(int i){
        i--;
        i%=8;
        for (Course c : Semester.sem.get(i).thisSem) {
            System.out.println(c);
        }
    }

    public static void removeCourse(Course c, int i){
        for (int j=0; j<Semester.sem.get(i-1).thisSem.size(); j++){
            if (Semester.sem.get(i-1).thisSem.get(j).equals(c)){
                Semester.sem.get(i-1).thisSem.remove(j);
                return;
            }
        }
    }
}
