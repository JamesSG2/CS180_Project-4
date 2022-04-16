import java.io.IOException;
import java.util.ArrayList;

/**
 * CourseTest
 *
 * Test class for debugging Course class
 *
 * @author James Gilliam, L15
 *
 * @version 4/15/2022
 *
 */
public class CourseTest {
    public static void main(String[] args) throws IOException {
        Course teachersCourse = new Course("Biology");

        if (teachersCourse.isNewCourseCreated()) {
            System.out.println("New Course Created");
        } else {
            System.out.println("Old Course Accessed");
        }

        // Constructing quiz (as done by OptionList)
        ArrayList<String> quiz = new ArrayList<>();
        quiz.add("Biology Quiz 1"); // quiz title
        quiz.add("a");
        quiz.add("b");
        quiz.add("c");
        // Note: "ENDQUIZ" is unnecessary

        boolean added = teachersCourse.addQuiz(quiz); // returns a isSuccess boolean
        if (!added) {
            System.out.println("Error! That quiz already exists.");
        }

        ArrayList<String> retrievedQuiz = teachersCourse.getQuiz("Biology Quiz 1");

        System.out.println(retrievedQuiz);

        // Uncomment below to test deletions
        /*
        //teachersCourse.deleteCourse();
        boolean deleted = teachersCourse.deleteQuiz("Biology Quiz 1");
        // returns a isSuccess boolean (quiz not found = false)
        if (deleted) {
            System.out.println("Quiz Deleted.");
        } else {
            System.out.println("Error! Quiz not found.");
        }
        */
    }
}
