import java.io.Serializable;
import java.util.ArrayList;

/**
 * Question
 *
 * Creates a constructor containing the information for one quiz
 *
 * @author Ian Fienberg, L15
 *
 * @version 4/11/2022
 *
 */
public class Quizzes implements Serializable {
    public ArrayList<Questions> questions;
    public String name;

    public Quizzes(ArrayList<Questions> questions, String name) {
        this.questions = questions;
        this.name = name;
    }

    public ArrayList<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Questions> quizzes) {
        this.questions = quizzes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
