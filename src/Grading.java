import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Grading
 * <p>
 * Used to grade the quizzes
 *
 * @author Zonglin Jia, L15
 *
 * @version 4/11/2022
 *
 */
public class Grading {
    //public boolean[] isCorrect;
    public ArrayList<Questions> questions;
    public ArrayList<String> correctAnswer;
    //ArrayList<String> studentsAnswer;
    public ArrayList<Integer> points;
    public ArrayList<String> gradeAnswer;
    public double pointsEarned;
    public double pointsTotal;

    public Grading(ArrayList<Questions> questions, ArrayList<String> correctAnswer, ArrayList<Integer> points) {
        this.questions = questions;
        this.correctAnswer = correctAnswer;
        this.points = points;
        pointsEarned = 0;
        pointsTotal = 0;
    }

    public void setCorrectAnswerAnswer(ArrayList<String> answer) {
        this.correctAnswer = answer;
    }

    public void setPointsEarned(double grade) {
        this.pointsEarned = grade;
    }

    public void setPointsTotal(double grade) {
        this.pointsTotal = grade;
    }

    public void setCorrectAnswer(ArrayList<String> correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setQuiz(ArrayList<Questions> quiz) {
        this.questions = questions;
    }

    public ArrayList<Questions> getQuiz() {
        return questions;
    }

    public ArrayList<String> getAnswer() {
        return correctAnswer;
    }

    public double getPointsEarned() {
        return pointsEarned;
    }


    public ArrayList<String> autoGrade (ArrayList<String> studentsAnswer, String quizName, String stuName) {

        //read the correct answer from the input fileName of quiz
        //read the student's answer from the input fileName
        //while the OptionList give the constructor correct answers and questions and points
        //this readFile method could be omitted

        ArrayList<String> submission = new ArrayList<String>();
        submission.add(quizName);
        // print current time stamp
        // added by Shruti
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        submission.add("Time Stamp: " + timestamp);

        int countOfRightAnswer = 0;

        for (int i = 0; i < questions.size(); i++) {

            String stuTempAns = studentsAnswer.get(i);

            pointsTotal += points.get(i);

            if (correctAnswer.get(i).equals(stuTempAns)) {
                countOfRightAnswer++;
                pointsEarned += points.get(i);
            }

            int qNum = i + 1;
            submission.add("Question #" + qNum + ":");

            if (stuTempAns.indexOf("/") >= 0) {

                String replace = stuTempAns;
                int countsOfLine = 0;

                for (char y : stuTempAns.toCharArray()) {
                    if (y == '/') {
                        countsOfLine++;
                    }
                }

                for (int l = 0; l < countsOfLine; l++) {

                    int temp = replace.indexOf("/");
                    String upload = replace.substring(0, temp);

                    if (l == 0) {
                        submission.add("The student's answer: ");
                        submission.add(upload);

                        replace = stuTempAns.substring(temp + 1);

                    } else {
                        submission.add(upload);
                        temp = replace.indexOf("/");
                        replace = replace.substring(temp + 1);
                    }
                }

            } else {
                submission.add("The student's answer: " + stuTempAns);
            }

            submission.add("The correct answer: " + correctAnswer.get(i));
            submission.add("Point Value: " + points.get(i));

        }

        String percent = "%";
        String score = String.format("Student " + stuName + "'s grade: %.2f",
                (pointsEarned / pointsTotal) * 100);
        String combo = score + percent;

        submission.add(combo);

        return submission;
    }
}