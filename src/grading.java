import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Grading
 *
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
    public ArrayList<String> answer;
    public ArrayList<Integer> points;
    public ArrayList<String> gradeAnswer;
    public double pointsEarned;
    public double pointsTotal;

    public Grading(ArrayList<Questions> questions, ArrayList<String> answer, ArrayList<Integer> points) {
        this.questions = questions;
        this.answer = answer;
        this.points = points;
        pointsEarned = 0;
        pointsTotal = 0;
    }



    public void setAnswer(ArrayList<String> answer) {
        this.answer = answer;
    }

    public void setPointsEarned(double grade) {
        this.pointsEarned = grade;
    }

    public void setQuiz(ArrayList<Questions> quiz) {
        this.questions = questions;
    }

    //public void setIsCorrect

    public ArrayList<Questions> getQuiz() {
        return questions;
    }

    public ArrayList<String> getAnswer() {
        return answer;
    }

    public double getPointsEarned() {
        return pointsEarned;
    }


    public ArrayList<String> gradeAnswer(String studentFileName, String quizName, String stuName) {


        //read the correct answer from the input fileName of quiz
        //while the OptionList give the constructor correct answers and questions and points
        //this readFile method could be omitted
        //read the student's answer from the input fileName

        ArrayList<String> submission = new ArrayList<String>();
        submission.add(quizName);

        ArrayList<String> list = new ArrayList<String>();
        int g = 0;
        int temp1 = 0;
        int temp2 = 0;

        try {
            File fi = new File(studentFileName);
            fi.createNewFile();
            if (fi.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(fi));

                String line = br.readLine();
                while (line != null) {
                    list.add(line);
                    line = br.readLine();
                    g++;
                }

                int c = 0;
                int d = 0;
                int f = 0;

                for (int j = 0; j < g; j++) {
                    if (list.get(j).contains(quizName)) {

                        f = j;

                    }
                }

                //System.out.println("test of list's start = " + list.get(f));

                for (int i = 0; i < questions.size(); i++) {

                    if (f == 0) {
                        temp2 = 1;
                    } else {
                        temp1 = i + 1;
                        temp2 = temp1 + f;
                    }

                    String stuTempAns = list.get(temp2);

                    pointsTotal += points.get(i);

                    if (answer.get(i).equals(stuTempAns)) {
                        c++;
                        pointsEarned += points.get(i);

                        //System.out.println("correct answer count: " + c);
                    }

                    d = i + 1;
                    //System.out.println("test of loop num " + d);
                    //System.out.println();

                }
                String percent = "%";
                String score = String.format("Student " + stuName + "'s grade: %.2f", (pointsEarned/pointsTotal) * 100);
                String combo = score + percent;

                submission.add(combo);

                br.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return submission;
    }


}
