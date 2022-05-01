import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Client
 *
 * Handles the GUI and all interactions with the user. Performs most computations except for
 * storage and file manipulation which is done by the server.
 *
 * @author James Gilliam, Ian Fienberg  L15
 * @version 4/28/2022
 *
 */
public class Client implements Serializable {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);

        Socket socket = new Socket("localhost", 4242);
        BufferedReader readServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writeToServer = new PrintWriter(socket.getOutputStream());

        ObjectOutputStream serverObjectOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream serverObjectIn = new ObjectInputStream(socket.getInputStream());

        // Ian's main method + updated initialization for ArrayList<Questions> quiz = new ArrayList<>();
        //updated parts:
        ArrayList<Questions> quiz = null;
        ArrayList<String> studentAnswer = null;
        ArrayList<String> correctAnswer = new ArrayList<String>();
        ArrayList<Quizzes> quizzes = new ArrayList<>();
        String grade = "";
        String userType = "";
        //updated by Zonglin:
        ArrayList<String> submission = new ArrayList<String>();
        ArrayList<String> sub = new ArrayList<String>();

        //changed by Zonglin:
        String userName = "";
        String password = "";
        int ts = -1;
        JFrame frame = new JFrame();

        int s1 = 0;
        boolean start = true;
        while (start) {

            String[] signLog = {"Sign up", "Log in"};
            s1 = JOptionPane.showOptionDialog(frame.getContentPane(), "What would you like to do?",
                    "Start Menu", 0, JOptionPane.INFORMATION_MESSAGE, null, signLog, null);
            s1 += 1;
            writeToServer.println(s1);
            writeToServer.flush();

            if (s1 == 0) {
                JOptionPane.showMessageDialog(null, "Goodbye!", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
                start = false;
            }

            if (s1 == 1) {
                userName = JOptionPane.showInputDialog(null, "What would like your username to be?", "Sign Up", JOptionPane.QUESTION_MESSAGE);
                password = JOptionPane.showInputDialog(null, "What would like your password to be?", "Sign Up", JOptionPane.QUESTION_MESSAGE);

                boolean type = false;

                String[] teachStud = {"Teacher", "Student"};
                ts = JOptionPane.showOptionDialog(frame.getContentPane(), "Are you a teacher or student?", "Sign Up",
                        0, JOptionPane.INFORMATION_MESSAGE, null, teachStud, null);

                if (ts == JOptionPane.YES_OPTION) {
                    type = true;
                }

                writeToServer.println(userName);
                writeToServer.flush();
                writeToServer.println(password);
                writeToServer.flush();
                writeToServer.println(type);
                writeToServer.flush();
                // Send to server to create account

                boolean created = Boolean.parseBoolean(readServer.readLine()); // was account created
                if (!created) {
                    JOptionPane.showMessageDialog(null, "The username is already taken! Account was not created.", "Sign Up", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Account created!", "Sign Up", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            userName = JOptionPane.showInputDialog(null, "What is your username?", "Log In", JOptionPane.QUESTION_MESSAGE);
            password = JOptionPane.showInputDialog(null, "What is your password?", "Log In", JOptionPane.QUESTION_MESSAGE);

            writeToServer.println(userName);
            writeToServer.flush();
            writeToServer.println(password);
            writeToServer.flush();

            boolean valid = Boolean.parseBoolean(readServer.readLine());
            boolean teach = Boolean.parseBoolean(readServer.readLine());
            boolean stud = Boolean.parseBoolean(readServer.readLine());

            if (valid) {
                if (teach) {
                    userType = "Teacher";
                } else if (stud) {
                    userType = "Student";
                }
                start = false;
            } else {
                JOptionPane.showMessageDialog(null, "That is not a valid account!", "Log In", JOptionPane.ERROR_MESSAGE);
            }
        }


        boolean courseInvalid = true;
        while (courseInvalid && s1 != 0) {
            // WILL ACCESS THE COURSE REQUESTED BY THE USER. TEACHERS CAN CREATE COURSES IF THEY WISH.
            String courseTitle;
            if (userType.equalsIgnoreCase("Teacher")) {
                courseTitle = JOptionPane.showInputDialog(null,
                        "What course would you like to access?\n" +
                                "Note: If it's a new course the course will automatically be created.",
                        "Course", JOptionPane.QUESTION_MESSAGE);
            } else {
                courseTitle = JOptionPane.showInputDialog(null,
                        "What course would you like to access?", "Course", JOptionPane.QUESTION_MESSAGE);
            }

            writeToServer.println(courseTitle); // SEND COURSE TITLE TO SERVER TO INITIALIZE THE USER'S COURSE
            writeToServer.flush();

            boolean isCreated = Boolean.parseBoolean(readServer.readLine());

            if (userType.equalsIgnoreCase("Teacher")) {
                courseInvalid = false;
                if (isCreated) {
                    JOptionPane.showMessageDialog(null, "New course created!", "Course", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Course accessed.", "Course", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                if (isCreated) {
                    JOptionPane.showMessageDialog(null, "Error! That course does not exist.", "Course", JOptionPane.ERROR_MESSAGE);
                    courseInvalid = true;
                } else {
                    courseInvalid = false;
                }
            }
        }

        // FILLS THE QUIZZES ARRAYLIST WITH ALL THE QUIZZES IN THE COURSE.
        //System.out.println(userType);

        quizzes = (ArrayList<Quizzes>) serverObjectIn.readObject();

        //System.out.println(userType);

        boolean teacher = true;
        if (userType.equalsIgnoreCase("Teacher")) {
            while (teacher) {

                //DISPLAYS MAIN MENU FOR TEACHERS
                //JFrame frame2 = new JFrame();
                String[] options = {"Log out", "Create new quiz", "Edit quiz", "Delete quiz", "Upload quiz",
                        "View submissions", "Edit account", "Delete account"};
                String reply = (String) JOptionPane.showInputDialog(null,
                        "Hi Teacher! What would you like to do?", "Main Menu",
                        JOptionPane.PLAIN_MESSAGE, null, options, null);

                //SERVER WANTS AN INT FROM OPTIONS, SO THIS WILL WRITE AN INT INSTEAD OF THE STRING
                for (int i = 0; i < options.length; i++) {
                    if (reply.equals(options[i])) {
                        writeToServer.println((i + 1));  // Server needs option selected to follow the client
                        writeToServer.flush();
                    }
                }

                //assuming option 1 for teachers is to create a quiz
                //updated: added a while loop for user to choose option until they want to quit

                //TEACHER CHOOSES TO QUIT
                if (reply == null || reply.equalsIgnoreCase("Log out")) {
                    JOptionPane.showMessageDialog(null, "Goodbye!", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
                    teacher = false;
                }
                //while (options != 0) {
                //TEACHER CHOOSES TO CREATE A QUIZ
                else if (reply.equalsIgnoreCase("Create new quiz")) {
                    quiz = new ArrayList<Questions>();
                    String courseName = "";
                    String quizName = "";
                    boolean rest = true;
                    ArrayList<String> quizText = new ArrayList<>();

                    quizName = JOptionPane.showInputDialog(null, "Please enter the quiz name.", "Create Quiz",
                            JOptionPane.QUESTION_MESSAGE);
                    quizText.add(quizName);

                    int numOfQuestions = Integer.parseInt(JOptionPane.showInputDialog(null, "How many questions will there be in this quiz?", "Create Quiz",
                            JOptionPane.QUESTION_MESSAGE));
                    boolean numErr = false;
                    for (int i = 1; i <= numOfQuestions; i++) {
                        String question = JOptionPane.showInputDialog(null, "What is question " + i + "?", "Create Quiz",
                                JOptionPane.QUESTION_MESSAGE);
                        quizText.add(question);
                        String option1 = JOptionPane.showInputDialog(null, "What is option 1?", "Create Quiz",
                                JOptionPane.QUESTION_MESSAGE);
                        quizText.add(option1);
                        String option2 = JOptionPane.showInputDialog(null, "What is option 2?", "Create Quiz",
                                JOptionPane.QUESTION_MESSAGE);
                        quizText.add(option2);
                        String option3 = JOptionPane.showInputDialog(null, "What is option 3?", "Create Quiz",
                                JOptionPane.QUESTION_MESSAGE);
                        quizText.add(option3);
                        String option4 = JOptionPane.showInputDialog(null, "What is option 4?", "Create Quiz",
                                JOptionPane.QUESTION_MESSAGE);
                        quizText.add(option4);

                        //below is changed by Zonglin to prompt the teacher if they want files as submission
                        String answer = JOptionPane.showInputDialog(null, "Which option is the correct answer (a, b, c, d)."
                                        + "\nOr, if answer should be a file, please enter \"file\"", "Create Quiz",
                                JOptionPane.QUESTION_MESSAGE);

                        // I DON'T KNOW WHAT THE POINT OF THIS CODE IS BELOW
                        if (answer.equals("file")) {

                            answer = JOptionPane.showInputDialog(null,
                                    "Please Add '/' for each new line. For example, " +
                                            "the correct answer of:\nBright \nspace\n" +
                                            "should be written as 'Bright/space/' as there are two lines"
                                            + "Please enter the correct answer:",
                                    JOptionPane.QUESTION_MESSAGE);
                        }

                        quizText.add(answer);
                        int points = 0;
                        try {
                            points = Integer.parseInt(JOptionPane.showInputDialog(null, "How many points is this question worth?", "Create Quiz",
                                    JOptionPane.QUESTION_MESSAGE));
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Error! The point value must be an integer.", "Create Quiz", JOptionPane.ERROR_MESSAGE);
                            numErr = true;
                            break;
                        }

                        quizText.add("" + points);

                        //ADDS QUESTION TO QUIZ ARRAYLIST
                        quiz.add(new Questions(question, option1, option2, option3, option4, answer, points));
                        correctAnswer.add(answer);
                        //just for test:
                        //studentAnswer.add(answer);
                    }
                    writeToServer.println(numErr);
                    writeToServer.flush();

                    if (!numErr) {
                        //ADDS QUIZ ARRAYLIST AND QUIZ NAME TO QUIZZES ARRAYLIST. ALSO SAVES IT TO THE COURSE
                        quizzes.add(new Quizzes(quiz, quizName));

                        serverObjectOut.writeObject(new Quizzes(quiz, quizName));
                        serverObjectOut.flush();
                        writeToServer.println(quizText.size());
                        writeToServer.flush();
                        for (String line : quizText) {
                            writeToServer.println(line);
                            writeToServer.flush();
                        }

                        boolean added = Boolean.parseBoolean(readServer.readLine());
                        if (!added) {
                            JOptionPane.showMessageDialog(null, "Error! That quiz already exists in this course.", "Create Quiz", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Quiz created!", "Create Quiz", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
                // IF A TEACHER WOULD LIKE TO EDIT A QUIZ
                else if (reply.equalsIgnoreCase("Edit quiz")) {
                    String quizName = "";
                    if (quizzes.size() != 0) {
                        quizName = JOptionPane.showInputDialog(null, "Enter the name of the quiz you want to edit.", "Edit Quiz",
                                JOptionPane.QUESTION_MESSAGE);
                        writeToServer.println(quizName);
                        writeToServer.flush();
                    }
                    for (int i = 0; i < quizzes.size(); i++) {
                        if (quizzes.get(i).getName().equalsIgnoreCase(quizName)) {
                            ArrayList<String> quizText = new ArrayList<>();
                            int quizLength = Integer.parseInt(readServer.readLine());
                            for (int j = 0; j < quizLength; j++) {
                                quizText.add(readServer.readLine());
                            }

                            String[] change = {"Name", "Question"};
                            int alter = JOptionPane.showOptionDialog(frame.getContentPane(), "What would you like to change?", "Edit Quiz",
                                    0, JOptionPane.INFORMATION_MESSAGE, null, change, null);
                            alter++;

                            // TO CHANGE THE NAME OF A QUIZ
                            if (alter == 1) {
                                String newName = JOptionPane.showInputDialog(null, "What name would you like to change it to?", "Edit Quiz",
                                        JOptionPane.QUESTION_MESSAGE);
                                quizzes.get(i).setName(newName);

                                quizText.set(0, newName);

                                JOptionPane.showMessageDialog(null, "Name changed!", "Edit Quiz", JOptionPane.INFORMATION_MESSAGE);

                            } else if (alter == 2) {
                                // TO CHANGE AN ENTIRE QUESTION ON A QUIZ
                                int qnum = Integer.parseInt(JOptionPane.showInputDialog(null, "Which question would you like to change?", "Edit Quiz",
                                        JOptionPane.QUESTION_MESSAGE));
                                String question = JOptionPane.showInputDialog(null, "What should this question be?", "Edit Quiz",
                                        JOptionPane.QUESTION_MESSAGE);
                                String option1 = JOptionPane.showInputDialog(null, "What is option 1?", "Edit Quiz",
                                        JOptionPane.QUESTION_MESSAGE);
                                String option2 = JOptionPane.showInputDialog(null, "What is option 2?", "Edit Quiz",
                                        JOptionPane.QUESTION_MESSAGE);
                                String option3 = JOptionPane.showInputDialog(null, "What is option 3?", "Edit Quiz",
                                        JOptionPane.QUESTION_MESSAGE);
                                String option4 = JOptionPane.showInputDialog(null, "What is option 4?", "Edit Quiz",
                                        JOptionPane.QUESTION_MESSAGE);
                                String answer = JOptionPane.showInputDialog(null, "Which option is the correct answer (a, b, c, d). Or,"
                                                + " if student should submit a file, please enter \"file\")", "Edit Quiz",
                                        JOptionPane.QUESTION_MESSAGE);

                                // TEACHER FILE UPLOADS FOR ANSWER
                                if (answer.equals("file")) {
                                    answer = JOptionPane.showInputDialog(null,
                                            "Please Add '/' for each new line. For example, " +
                                                    "the correct answer of:\nBright \nspace\n" +
                                                    "should be written as 'Bright/space/' as there are two lines"
                                                    + "Please enter the correct answer:",
                                            JOptionPane.QUESTION_MESSAGE);
                                }

                                int points = Integer.parseInt(JOptionPane.showInputDialog(null, "How many points is this question worth?", "Edit Quiz",
                                        JOptionPane.QUESTION_MESSAGE));

                                int questionIndex = 1 + (qnum - 1) * 7;
                                //NULL POINTER EXCEPTION
                                quizText.set(questionIndex, question);
                                quizText.set(++questionIndex, option1);
                                quizText.set(++questionIndex, option2);
                                quizText.set(++questionIndex, option3);
                                quizText.set(++questionIndex, option4);
                                quizText.set(++questionIndex, answer);
                                quizText.set(++questionIndex, "" + points);

                                quizzes.get(i).getQuestions().get(qnum - 1).setQuestion(question);
                                quizzes.get(i).getQuestions().get(qnum - 1).setOption1(option1);
                                quizzes.get(i).getQuestions().get(qnum - 1).setOption2(option2);
                                quizzes.get(i).getQuestions().get(qnum - 1).setOption3(option3);
                                quizzes.get(i).getQuestions().get(qnum - 1).setOption4(option4);
                                quizzes.get(i).getQuestions().get(qnum - 1).setAnswer(answer);
                                quizzes.get(i).getQuestions().get(qnum - 1).setPoints(points);

                                // quiz.set(qnum - 1, new Questions(question, option1, option2, option3, option4,
                                // answer, points));
                                // ISNT USED YET
                                // correctAnswer.set(qnum - 1, answer);
                                // just for test:
                                // studentAnswer.set(qnum - 1, answer);
                                // quizzes.add(new Quizzes(quiz, quizName));
                                JOptionPane.showMessageDialog(null, "Quiz edited!", "Edit Quiz", JOptionPane.INFORMATION_MESSAGE);
                            }
                            writeToServer.println(quizText.size());
                            writeToServer.flush();
                            for (String line : quizText) {
                                writeToServer.println(line);
                                writeToServer.flush();
                            }
                            serverObjectOut.writeObject(quizzes);
                            serverObjectOut.flush();

                            break;

                            // IF THERE ARE QUIZZES, BUT THE INPUTTED NAME DOESN'T MATCH ANY
                        } else if (i == quizzes.size() - 1) {
                            JOptionPane.showMessageDialog(null,
                                    "That is not a name of a current quiz!", "Edit Quiz",
                                    JOptionPane.ERROR_MESSAGE);

                        }
                    }

                    // IF THE ARRAYLIST OF QUIZZES IS SIZE 0, PRINT AN ERROR MESSAGE AND TRY AGAIN
                    if (quizzes.size() == 0) {
                        JOptionPane.showMessageDialog(null,
                                "You need to create a quiz before you can edit one!", "Edit Quiz",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }

                // IF A TEACHER WOULD LIKE TO DELETE AN ENTIRE QUIZ
                else if (reply.equalsIgnoreCase("Delete quiz")) {
                    String quizName2 = "";
                    if (quizzes.size() != 0) {
                        quizName2 = JOptionPane.showInputDialog(null, "Enter the name of the quiz you want to delete.", "Delete Quiz",
                                JOptionPane.QUESTION_MESSAGE);

                        writeToServer.println(quizName2);
                        writeToServer.flush();
                    }
                    int p = -1;
                    for (int i = 0; i < quizzes.size(); i++) {
                        if (quizzes.get(i).getName().equalsIgnoreCase(quizName2)) {
                            int sure = JOptionPane.showConfirmDialog(frame, "Are you sure you would like to delete this quiz?",
                                    "Delete Quiz", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                            writeToServer.println(sure);
                            writeToServer.flush();
                            if (sure == 0) {
                                quizzes.remove(i);
                                // DELETES QUIZ FROM THE COURSE IN SERVER
                                JOptionPane.showMessageDialog(null,
                                        "Quiz deleted!", "Delete Quiz",
                                        JOptionPane.INFORMATION_MESSAGE);
                                break;
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Your quiz will not be deleted.", "Delete Quiz",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                            p = i;
                            break;

                            // IF THERE ARE QUIZZES, BUT THE INPUTTED NAME DOESN'T MATCH ANY
                        } else if (i == quizzes.size() - 1) {
                            JOptionPane.showMessageDialog(null,
                                    "That is not a name of a current quiz!", "Delete Quiz",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        p = i;
                    }

                    // IF THE ARRAYLIST OF QUIZZES IS SIZE 0, PRINT AN ERROR MESSAGE AND TRY AGAIN
                    if (quizzes.size() == 0 && p != 0) {
                        JOptionPane.showMessageDialog(null,
                                "You need to create a quiz before you can delete one!", "Delete Quiz",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } else if (reply.equalsIgnoreCase("Upload quiz")) {
                    // IF A TEACHER WOULD LIKE TO UPLOAD A QUIZ FILE
                    // updated by Shruti
                    JOptionPane.showMessageDialog(null, "Note: the file must follow the format of quiz title first then for each \n" +
                            "question: the question, the 4 choices, the correct answer, then the point value." +
                            "\nAt the end of the quiz:" +
                            "type \"--------------------------------------------------\". \nEverything is separated " +
                            "with a new line. See CoursesData.txt\n" +
                            "to look at past quizzes made by this program. Follow that format.", "Upload quiz", JOptionPane.PLAIN_MESSAGE);
                    String quizInp = JOptionPane.showInputDialog(null,
                            "What is the name of the quiz file you would like to upload?", "View Submissions", JOptionPane.QUESTION_MESSAGE);
                    writeToServer.println(quizInp);
                    writeToServer.flush();
                    // System.out.println(quizzes.get(0).getQuestions().get(0).getQuestion());
                    String fileName = scan.nextLine();

                    // WILL READ THE TEACHER'S FILE AND ADD THEIR QUIZ TO "quizzes" ARRAYLIST
                    ArrayList<String> quizText = new ArrayList<>();
                    try {
                        File fi = new File(fileName);
                        writeToServer.println(fi.exists());
                        writeToServer.flush();
                        if (fi.exists()) {
                            BufferedReader buf = new BufferedReader(new FileReader(fi));
                            String p = buf.readLine();
                            String quizName = p;
                            quizText.add(quizName);
                            boolean q = true;
                            while (p != null) {
                                if (p.length() > 0) {
                                    String maybe = "";
                                    ArrayList<Questions> tempQuestions = new ArrayList<>();
                                    for (int i = 0; i < 1; i++) {
                                        String question;
                                        if (q) {
                                            question = buf.readLine();
                                            quizText.add(question);
                                        } else {
                                            question = maybe;
                                        }
                                        String option1 = buf.readLine();
                                        String option2 = buf.readLine();
                                        String option3 = buf.readLine();
                                        String option4 = buf.readLine();
                                        String answer = buf.readLine();
                                        quizText.add(option1);
                                        quizText.add(option2);
                                        quizText.add(option3);
                                        quizText.add(option4);
                                        quizText.add(answer);

                                        int points;
                                        try {
                                            points = Integer.parseInt(buf.readLine());
                                        } catch (NumberFormatException e) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Error! Point value must be an integer.",
                                                    "Upload Quiz", JOptionPane.ERROR_MESSAGE);
                                            break;
                                        }
                                        quizText.add("" + points);
                                        maybe = buf.readLine();
                                        if (!maybe.equals("--------------------------------------------------")) {
                                            q = false;
                                            i--;
                                        }
                                        tempQuestions.add(new Questions(question, option1, option2, option3, option4,
                                                answer, points));
                                    }
                                    quizzes.add(new Quizzes(tempQuestions, quizName));
                                }
                                p = buf.readLine();
                            }

                            writeToServer.println(quizText.size());
                            writeToServer.flush();
                            for (String line : quizText) {  // SERVER SAVES QUIZ TO THE COURSE
                                writeToServer.println(line);
                                writeToServer.flush();
                            }
                            serverObjectOut.writeObject(quizzes);
                            serverObjectOut.flush();

                            boolean added = Boolean.parseBoolean(readServer.readLine());
                            if (!added) {
                                JOptionPane.showMessageDialog(null,
                                        "Error! That quiz already exists in this course.",
                                        "Upload Quiz", JOptionPane.ERROR_MESSAGE);
                            }
                            //writer.write("END OF QUIZ\n");
                            buf.close();
                            //writer.close();
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Error! File Not Found.",
                                    "Upload Quiz", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(quizzes.get(1).getQuestions().get(1).getQuestion());

                    //IF TEACHER CHOOSES TO VIEW SUBMISSIONS
                } else if (reply.equalsIgnoreCase("View submissions")) {

                    String quizName3 = "";
                    if (quizzes.size() != 0) {
                        /*System.out.println("Which quiz would you like to see?");
                        //PRINTS LIST OF QUIZZES BY NAME
                        for (int i = 0; i < quizzes.size(); i++) {
                            System.out.println((i + 1) + ". " + quizzes.get(i).getName());
                        }*/
                        int quizNum1 = 0;
                        String[] availQuizzes = new String[quizzes.size()];

                        for (int i = 0; i < quizzes.size(); i++) {
                            availQuizzes[i] = quizzes.get(i).getName();
                        }

                        String whichQuiz = (String) JOptionPane.showInputDialog(null,
                                "Which quiz would you like to see?", "View Submissions",
                                JOptionPane.PLAIN_MESSAGE, null, availQuizzes, null);

                        for (int i = 0; i < availQuizzes.length; i++) {
                            if (availQuizzes[i].equals(whichQuiz)) {
                                quizNum1 = (i + 1);
                            }
                        }

                        writeToServer.println(quizNum1);
                        writeToServer.flush();

                        String name = JOptionPane.showInputDialog(null,
                                "Please input the student's username.", "View Submissions", JOptionPane.QUESTION_MESSAGE);
                        writeToServer.println(name);
                        writeToServer.flush();
                        String key = JOptionPane.showInputDialog(null,
                                "Please input the student's password.", "View Submissions", JOptionPane.QUESTION_MESSAGE);
                        writeToServer.println(key);
                        writeToServer.flush();
                        int attemptNum = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Please input the student's attempt number.", "View Submissions", JOptionPane.QUESTION_MESSAGE));
                        writeToServer.println(attemptNum);
                        writeToServer.flush();

                        if (readServer.readLine().equals("validInfo")) {
                            JOptionPane.showMessageDialog(null, readServer.readLine(),
                                    "View Submissions", JOptionPane.INFORMATION_MESSAGE);

                            for (String v : sub) {
                                JOptionPane.showMessageDialog(null, readServer.readLine(),
                                        "View Submissions", JOptionPane.INFORMATION_MESSAGE);
                            }

                            //IF THERE ARE QUIZZES, BUT THE INPUTTED NAME DOESN'T MATCH ANY
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "ERROR! THE INFORMATION IS INVALID!",
                                    "View Submissions", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                    //IF THE ARRAYLIST OF QUIZZES IS SIZE 0, PRINT AN ERROR MESSAGE AND TRY AGAIN
                    if (quizzes.size() == 0) {
                        JOptionPane.showMessageDialog(null,
                                "You need to create a quiz before you can view one!",
                                "View Submissions", JOptionPane.ERROR_MESSAGE);
                    }


                } else if (reply.equalsIgnoreCase("Edit account")) {
                    String newUser = JOptionPane.showInputDialog(null, "What would you like your new username to be?", "Edit Account",
                            JOptionPane.QUESTION_MESSAGE);
                    String newPass = JOptionPane.showInputDialog(null, "What would you like your new password to be?", "Edit Account",
                            JOptionPane.QUESTION_MESSAGE);

                    // USER'S ACCOUNT EDITED BY SERVER
                    writeToServer.println(newUser);
                    writeToServer.flush();
                    writeToServer.println(newPass);
                    writeToServer.flush();

                } else if (reply.equalsIgnoreCase("Delete account")) {
                    // USER'S ACCOUNT DELETED BY SERVER
                    JOptionPane.showMessageDialog(null, "Account deleted.",
                            "Delete Account", JOptionPane.INFORMATION_MESSAGE);
                    teacher = false;
                } else {
                    //I DON'T THINK WE EVEN NEED THIS ANYMORE
                    JOptionPane.showMessageDialog(null,
                            "That is not a valid option!",
                            "Menu", JOptionPane.ERROR_MESSAGE);
                }

            }
        }


        // TODO: All codes below are for the student to input
        boolean student = true;

        //int count = 0; //count for attemptNum
        //userType = "student";

        if (userType.equalsIgnoreCase("Student")) {
            while (student) {
                String[] options = {"Log out", "Take a quiz", "View Submissions", "Edit account", "Delete account"};
                String response = (String) JOptionPane.showInputDialog(null,
                        "Hi Student! What would you like to do?", "Main Menu",
                        JOptionPane.PLAIN_MESSAGE, null, options, null);

                //SERVER WANTS AN INT FROM OPTIONS, SO THIS WILL WRITE AN INT INSTEAD OF THE STRING
                if (response == null) {
                    writeToServer.println(1);  // Server needs option selected to follow the client
                    writeToServer.flush();
                }
                for (int i = 0; i < options.length; i++) {
                    if (response != null && response.equals(options[i])) {
                        writeToServer.println((i + 1));  // Server needs option selected to follow the client
                        writeToServer.flush();
                    }
                }

                //STUDENT CHOOSES TO QUIT
                if (response == null || response.equalsIgnoreCase("Log out")) {
                    JOptionPane.showMessageDialog(null, "Goodbye!", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
                    student = false;
                }
                // STUDENT CHOOSES TO TAKE A QUIZ
                else if (response.equalsIgnoreCase("Take a quiz")) {

                    // PRINTS LIST OF QUIZZES BY NAME
                    String[] quizList = new String[quizzes.size()];
                    for (int i = 0; i < quizzes.size(); i++) {
                        quizList[i] = quizzes.get(i).getName();
                    }
                    String whichQuiz = (String) JOptionPane.showInputDialog(null,
                            "Which quiz would you like to take?", "Take a Quiz",
                            JOptionPane.PLAIN_MESSAGE, null, quizList, null);


                    int quizNum = 0;
                    for (int i = 0; i < options.length; i++) {
                        if (whichQuiz.equalsIgnoreCase(quizList[i])) {
                            quizNum = (i + 1);
                            writeToServer.println((i + 1));  // Server needs option selected to follow the client
                            writeToServer.flush();
                            break;
                        }
                    }

                    if (quizNum > 0 && quizNum <= quizzes.size()) {
                        String longString = "";
                        studentAnswer = new ArrayList<String>();
                        String guess = "";
                        // PRINTS EACH QUESTION AND OPTIONS, THEN STORES STUDENTS ANSWERS IN ARRAYLIST "STUDENTANSWER"
                        for (int i = 0; i < quizzes.get(quizNum - 1).getQuestions().size(); i++) {

                            String[] ansChoices = {"a) " + quizzes.get(quizNum - 1).getQuestions().get(i).getOption1(),
                                    "b) " + quizzes.get(quizNum - 1).getQuestions().get(i).getOption2(),
                                    "c) " + quizzes.get(quizNum - 1).getQuestions().get(i).getOption3(),
                                    "d) " + quizzes.get(quizNum - 1).getQuestions().get(i).getOption4(),
                                    "File Upload"};
                            guess = ((String) JOptionPane.showInputDialog(null,
                                    "Question " + (i + 1) + ":\n" +
                                            quizzes.get(quizNum - 1).getQuestions().get(i).getQuestion(),
                                    "Take a Quiz", JOptionPane.QUESTION_MESSAGE, null, ansChoices,
                                    null)).substring(0, 1);
                            if (guess.equals("F")) {
                                guess = "file";
                            }

                            writeToServer.println(guess);
                            writeToServer.flush();

                        }

                        if (guess.equalsIgnoreCase("file")) {
                            //FILE NAME TO BE UPLOADED BY THE STUDENT

                            String file = JOptionPane.showInputDialog(null,
                                    "Please input the name of the file.", "Take a Quiz", JOptionPane.QUESTION_MESSAGE);
                            writeToServer.println(file);
                            writeToServer.flush();

                        }
                    }

                    int submit = JOptionPane.showConfirmDialog(frame, "Would you like to submit this quiz?",
                            "Take a Quiz", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    writeToServer.println(submit);
                    writeToServer.flush();

                    if (submit != 0) {
                        JOptionPane.showMessageDialog(null, "Alright. Your quiz will not be submitted.", "Take a Quiz", JOptionPane.INFORMATION_MESSAGE);
                        continue;
                    } else {
                        JOptionPane.showMessageDialog(null, "Quiz Submitted!", "Take a Quiz", JOptionPane.INFORMATION_MESSAGE);
                    }


                /*int quizNum = 0;
                    if (quizNum < 1 || quizNum > quizzes.size()) {
                        JOptionPane.showMessageDialog(null, "this is not a valid option!", "Quiz", JOptionPane.ERROR_MESSAGE);
                    }*/
                } else if (response.equalsIgnoreCase("View Submissions")) {
                    int quizNum1 = 0;

                    if (quizzes.size() != 0) {
                        String[] availQuizzes = new String[quizzes.size()];

                        for (int i = 0; i < quizzes.size(); i++) {
                            availQuizzes[i] = quizzes.get(i).getName();
                        }

                        //PRINTS LIST OF QUIZZES BY NAME

                        String quizOptions = (String) JOptionPane.showInputDialog(null,
                                "Which quiz would you like to see?", "View Submissions",
                                JOptionPane.QUESTION_MESSAGE, null, availQuizzes, null);

                        for (int i = 0; i < availQuizzes.length; i++) {
                            if (availQuizzes[i].equals(quizOptions)) {
                                quizNum1 = (i + 1);
                            }
                        }

                        //STUDENT'S CHOICE ON WHICH QUIZ TO SEE
                        writeToServer.println(quizNum1);
                        writeToServer.flush();

                        int attemptNum = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Please input the attempt number.", "View Submissions", JOptionPane.QUESTION_MESSAGE));
                        writeToServer.println(attemptNum);
                        writeToServer.flush();

                        if (Boolean.parseBoolean(readServer.readLine())) {  // isValid Submission
                            String attemptNumString = readServer.readLine();

                            int submissionLength = Integer.parseInt(readServer.readLine());
                            String submissionText = "";
                            for (int j = 0; j < submissionLength; j++) {
                                submissionText += readServer.readLine() + "\n";
                            }
                            JOptionPane.showMessageDialog(null, submissionText,
                                    "View Submissions - " + availQuizzes[quizNum1 - 1] + " - "
                                            + attemptNumString, JOptionPane.INFORMATION_MESSAGE);
                            //IF THERE ARE QUIZZES, BUT THE INPUTTED NAME DOESN'T MATCH ANY
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "ERROR! THE INFORMATION IS INVALID!",
                                    "View Submissions", JOptionPane.ERROR_MESSAGE);
                        }

                    } else if (response.equalsIgnoreCase("Edit Account")) {
                        String newUser = JOptionPane.showInputDialog(null, "What would you like your new username to be?", "Edit Account",
                                JOptionPane.QUESTION_MESSAGE);
                        // user's account edited by server
                        writeToServer.println(newUser);
                        writeToServer.flush();
                        String newPass = JOptionPane.showInputDialog(null, "What would you like your new password to be?", "Edit Account",
                                JOptionPane.QUESTION_MESSAGE);
                        writeToServer.println(newPass);
                        writeToServer.flush();
                        JOptionPane.showMessageDialog(null, "Account edited.",
                                "Edit Account", JOptionPane.INFORMATION_MESSAGE);

                    } else if (response.equalsIgnoreCase("Delete account")) {
                        // USER'S ACCOUNT DELETED BY SERVER
                        JOptionPane.showMessageDialog(null, "Account deleted.",
                                "Delete Account", JOptionPane.INFORMATION_MESSAGE);
                        student = false;
                    } else {
                        // no need due to the try catch made earlier
                        JOptionPane.showMessageDialog(null,
                                "That is not a valid option!",
                                "Menu", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        frame.dispose();
        writeToServer.close();
        readServer.close();
        serverObjectIn.close();
        serverObjectOut.close();
        socket.close();
    }
}