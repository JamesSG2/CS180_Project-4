import java.io.*;
import java.util.ArrayList;

/**
 * Course
 *
 * Handles and stores the quiz information of quizzes made by teachers for a specific course
 *
 * @author James Gilliam, L15
 *
 * @version 4/15/2022
 *
 */
public class Course {
    private String courseTitle;
    private ArrayList<String> coursesData;
    private boolean newCourseCreated;
    private String quizSpacer = "--------------------------------------------------";
    private String courseSpacer = "||||||||||||||||||||||||||||||||||||||||||||||||||||||||";

    public Course(String courseTitle) throws IOException {
        this.courseTitle = courseTitle;
        readCoursesDataFile();

        if (coursesData.isEmpty()) {
            coursesData.add(courseSpacer);
        }

        int courseIndex = getCourseIndex();
        if (courseIndex == 0) {
            coursesData.add(courseTitle);
            coursesData.add(quizSpacer);
            coursesData.add(courseSpacer);
            newCourseCreated = true;
        }
        writeCoursesDataFile();
    }

    public boolean addQuiz(ArrayList<String> quiz) throws IOException {
        readCoursesDataFile();

        int courseEndIndex = getCourseIndex();
        while (courseEndIndex < coursesData.size() && !(coursesData.get(courseEndIndex).equals(courseSpacer))) {
            courseEndIndex++;
        }

        for (int i = getCourseIndex(); i < courseEndIndex; i++) {
            if (coursesData.get(i - 1).equals(quizSpacer) && coursesData.get(i).equals(quiz.get(0))) {
                return false;
            }
        }

        for (int j = 0; j < quiz.size(); j++) {
            coursesData.add(courseEndIndex + j, quiz.get(j));
        }
        coursesData.add(courseEndIndex + quiz.size(), quizSpacer);

        writeCoursesDataFile();
        return true;
    }

    public ArrayList<String> getQuiz(String quizTitle) throws IOException {
        readCoursesDataFile();

        int quizIndex = getCourseIndex();
        while (!(coursesData.get(quizIndex).equals(quizTitle) ||
                (coursesData.get(quizIndex).equals(courseSpacer)))) {
            quizIndex++;
        }
        if (coursesData.get(quizIndex).equals(courseSpacer)) {
            return null;
        }

        ArrayList<String> quiz = new ArrayList<>();
        int i = quizIndex;
        while (!coursesData.get(i).equals(quizSpacer)) {
            quiz.add(coursesData.get(i));
            i++;
        }
        return quiz;
    }

    public ArrayList<String> getCourseQuizTitles() throws IOException {
        readCoursesDataFile();
        ArrayList<String> quizzes = new ArrayList<>();
        int i = getCourseIndex();
        while (!coursesData.get(i).equals(courseSpacer)) {
            if (coursesData.get(i - 1).equals(quizSpacer)) {
                quizzes.add(coursesData.get(i));
            }
            i++;
        }
        return quizzes;
    }

    public void deleteCourse() throws IOException {
        readCoursesDataFile();
        int i = getCourseIndex();
        while (!coursesData.get(i).equals(courseSpacer)) {
            coursesData.remove(i);
        }
        coursesData.remove(i);
        writeCoursesDataFile();
    }

    public boolean deleteQuiz(String quizTitle) throws IOException {
        readCoursesDataFile();

        int quizIndex = getCourseIndex();
        while (!(coursesData.get(quizIndex).equals(quizTitle) ||
                (coursesData.get(quizIndex).equals(courseSpacer)))) {
            quizIndex++;
        }
        if (coursesData.get(quizIndex).equals(courseSpacer)) {
            return false;
        }

        int i = quizIndex;
        while (!coursesData.get(i).equals(courseSpacer)) {
            coursesData.remove(i);
        }

        writeCoursesDataFile();
        return true;
    }

    public boolean isNewCourseCreated() {
        return newCourseCreated;
    }

    private void readCoursesDataFile() throws IOException {
        File f = new File("CoursesData.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        coursesData = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
            coursesData.add(line);
            line = br.readLine();
        }
        br.close();
    }

    private void writeCoursesDataFile() throws IOException {
        File f = new File("CoursesData.txt");
        PrintWriter writer = new PrintWriter(f);

        for (String line : coursesData) {
            writer.println(line);
        }
        writer.close();
    }

    private int getCourseIndex() {
        int CourseIndex = 0;
        for (int i = 1; i < coursesData.size(); i++) {
            if (coursesData.get(i).equals(courseTitle) && coursesData.get(i - 1).equals(courseSpacer)) {
                CourseIndex = i;
                break;
            }
        }
        return CourseIndex;
    }
}
