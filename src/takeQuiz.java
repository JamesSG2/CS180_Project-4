import java.util.Scanner;
import javax.swing.*;


public class takeQuiz {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // use arraylist string so teachers can add incrementally increasing questions that students have to answer
        String q1 = "whatever question" + "\n(a)answer a\n (b) b \n (c) c \n (d)d";
        String q2 = "whatever second question" + "\n(a)answer a \n (b) b \n (c) c \n(d)d;";
        String q3 = "whatever third question" + "\n(a)answer a \n (b) b \n (c) c \n(d)d;";
        // so on..
        // find a way to make it, so it interacts with teacher to change the question


        Question[] questions = {
                new Question(q1, "a"), // question that corresponds to the correct answer
                new Question(q2, "b"),
                new Question(q3, "c")
        };
        Test(questions);
    }

    public static void Test(Question[] questions) {
        int score = 0;
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i].prompt);
            String answer = sc.nextLine();
            if (answer.equals(questions[i].answer)) {
                score++;
            }
        }
        System.out.println("You got " + score + "/" + questions.length);
    }

    public class DropDown {
        public static void DropDown(String[] args) {
            String[] optionsToChoose = {"option 1", "option 2", "option 3", "option 4", "None of the options listed"};

            String getFavOption = (String) JOptionPane.showInputDialog(
                    null,
                    "input from teacher",
                    "Choose Option that best fits",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    optionsToChoose,
                    optionsToChoose[3]);

            System.out.println("Your chosen option: " + getFavOption);
        }
    }

}


