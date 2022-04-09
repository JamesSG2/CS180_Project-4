import java.util.ArrayList;
import java.util.Scanner;

public class optionList {

    public static void main(String[] args) {

        // Ian's main method + updated initialization for ArrayList<Questions> quiz = new ArrayList<>();
        //updated parts:
        ArrayList<Questions> quiz = new ArrayList<Questions>();
        ArrayList<String> studentAnswer = new ArrayList<String>();
        ArrayList<String> correctAnswer = new ArrayList<String>();
        String grade = "";

        Scanner scan = new Scanner(System.in);

        System.out.println("Hello teacher. What would you like to do?");
        //updated parts:
        System.out.println("0. quit the application\n" + "1. create a quiz\n" + "2. check the grade");

        int options = scan.nextInt();

        //assuming option 1 for teachers is to create a quiz
        //updated: added a while loop for user to choose option until they want to quit
        scan.nextLine();

        while (options != 0) {
            if (options == 1) {
                System.out.println("What would you like to name this quiz?");

                String quizName = scan.nextLine();
                //ArrayList<Questions> quiz = new ArrayList<>();

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
                    correctAnswer.add(answer);
                    //just for test:
                    studentAnswer.add(answer);
                }

                System.out.println("What do you want to do with the quiz");
                System.out.println("1. grade\n" + "2. create a new quiz\n" + "3. quit the application");
                scan.nextLine();
                String secondOption = scan.nextLine();

                if (secondOption.equals("1")) {
                    /*
                     *function for grading/checking the grade
                     *should have a get method to get the students' answer
                     *below is a temporary solution for testing
                     *assume the student input 2 for the question 1 + 1 = ?
                     */

                    /*
                    for (int j = 0; j < quiz.size(); j++) {
                        System.out.println("test for bound" + studentAnswer.get(j - 1));
                        studentAnswer.set(j, "2");
                        System.out.println("check studentAnswer: " + studentAnswer.get(j));
                    }
                    */

                    grading testOne = new grading(quiz, correctAnswer);
                    testOne.checkAnswer(studentAnswer);
                    System.out.println("The grade is: " + testOne.getGrade());

                    System.out.println("What do you want to do next");
                    System.out.println("2. create a new quiz\n" + "3. quit the application");
                    //scan.nextLine();
                    String thirdOption = scan.nextLine();

                    if (thirdOption.equals("2")) {
                        options = 1;
                    }

                    if (thirdOption.equals("3")) {
                        options = 0;
                        break;
                    }

                } else if (secondOption.equals("2")) {
                    options = 1;
                }

                if (grade.equals("3")) {
                    options = 0;
                    break;
                }

            //other options to be implemented
            }
        }

        if (options == 0) {
            System.out.println("Goodbye!");
        }
    }

}
