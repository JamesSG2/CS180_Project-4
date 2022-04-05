import java.util.ArrayList;
import java.util.Scanner;

public class CreateQuiz {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Hello teacher. What would you like to do?");
        int options = scan.nextInt();

        //assuming option 1 for teachers is to create a quiz
        if (options == 1) {
            System.out.println("What would you like to name this quiz?");
            scan.nextLine();
            String quizName = scan.nextLine();
            ArrayList<Questions> quiz = new ArrayList<>();

            System.out.println("How many questions will there be in this quiz?");
            int numOfQuestions = scan.nextInt();

            for (int i = 1; i <= numOfQuestions; i++) {
                System.out.println("What is question " + i + "?");
                scan.nextLine();
                String question = scan.nextLine();
                System.out.println("What is option 1?");
                String option1 = scan.nextLine();
                System.out.println("What is option 2?");
                String option2 = scan.nextLine();
                System.out.println("What is option 3?");
                String option3 = scan.nextLine();
                System.out.println("What is option 4?");
                String option4 = scan.nextLine();
                System.out.println("Which option is the correct answer");
                String answer = scan.nextLine();
                System.out.println("How many points is this question worth?");
                int points = scan.nextInt();

                quiz.add(new Questions(question, option1, option2, option3, option4, answer, points));

            }

        }
    }
}
