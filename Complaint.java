import java.util.ArrayList;
import java.util.Scanner;

public class Complaint {

    private static ArrayList<Complaint> complaints=new ArrayList<>();
    private static ArrayList<Complaint> answeredComplaints=new ArrayList<>();
    String query;
    int status;
    String answer;

    Complaint(String query) {
        this.query = query;
        this.status = 0;
        complaints.add(this);
    }

    public static void viewComplaints() {
        System.out.println("Unanswered complaints: ");
        for (int i=0;i<complaints.size();i++) {
            System.out.println(i+". "+complaints.get(i).query);
        }
        System.out.println("Answered complaints: ");
        for (int i=0;i<answeredComplaints.size();i++) {
            System.out.println(i+". "+answeredComplaints.get(i).query +"  "+answeredComplaints.get(i).answer);
        }
    }

    @Override
    public String toString() {
        String s;
        if (this.status == 0) {
            s="Not answered";
        }
        else{
            s="Answered";
        }
        return "Complaint{" +
                "query='" + query + '\'' +
                ", status=" + s +
                ", answer='" + answer + '\'' +
                '}';
    }

    public static void answerComplaint() {
        if (complaints.isEmpty()){
            System.out.println("Nothing to answer");
            return;
        }
        viewComplaints();
        System.out.print("Input index of complaint to answer (0 based indexing): ");
        Scanner sc=new Scanner(System.in);
        int index=sc.nextInt();
        sc.nextLine();
        index%=complaints.size();
        Complaint complaint=complaints.get(index);
        System.out.println(complaint.query);
        System.out.print("Input answer to the query: ");
        complaint.answer= sc.nextLine();
        complaint.status=1;
        answeredComplaints.add(complaint);
        complaints.remove(index);
    }
}
