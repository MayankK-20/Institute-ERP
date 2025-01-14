public class Feedback<T> {
    private T feedback;

    public Feedback(T feedback) {
        this.feedback = feedback;
    }

    public String toString() {
        return feedback.toString();
    }
}
