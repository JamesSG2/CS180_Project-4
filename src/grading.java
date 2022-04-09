import java.util.ArrayList;
public class grading {
    //public boolean[] isCorrect;
    public ArrayList<Questions> quiz;
    public ArrayList<String> answer;
    public double grade;

    public grading(ArrayList<Questions> quiz, ArrayList<String> answer) {
        this.quiz = quiz;
        this.answer = answer;
        grade = 0;
    }

    public void setAnswer(ArrayList<String> answer) {
        this.answer = answer;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setQuiz(ArrayList<Questions> quiz) {
        this.quiz = quiz;
    }

    //public void setIsCorrect

    public ArrayList<Questions> getQuiz() {
        return quiz;
    }

    public ArrayList<String> getAnswer() {
        return answer;
    }

    public double getGrade() {
        return grade;
    }

    public void checkAnswer(ArrayList<String> importAnswers) {

        //System.out.println("check answer: " + answer.get(0));

        for (int i = 0; i < quiz.size(); i++) {
            Questions temp = quiz.get(i);
            //String trueAnswer = temp.getAnswer();
            String trueAnswer = answer.get(0);
            //test method
            //System.out.println("check trueAnswer: " + answer.get(0));

            if (trueAnswer.equals(importAnswers.get(i))) {
                grade += temp.getPoints();
                //test method
                //System.out.println("check grade: " + grade);
            }
        }
    }

}
