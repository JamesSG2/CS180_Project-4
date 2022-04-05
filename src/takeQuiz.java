import java.util.Scanner;

public class takeQuiz {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String q1 = "whatever question" + "\n(a)answer a\n (b) b \n (c) c \n (d)d";
        String q2 = "whatever second question" + "\n(a)answer a \n (b) b \n (c) c \n(d)d;";
        // so on..
        // find a way to make it, so it interacts with teacher to change the question


        Question[] questions = {
                new Question(q1, "a"), // question that corresponds to the qurrect answer
                new Question(q2, "b")
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
}


