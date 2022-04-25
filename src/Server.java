import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Server
 *
 * Receives, stores and retrieves information for multiple Clients.
 *
 * @author James Gilliam, L15
 *
 * @version 4/18/2022
 *
 */
public class Server implements Serializable {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(4242);

        System.out.println("Waiting for the client to connect...");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");

        BufferedReader readClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writeToClient = new PrintWriter(socket.getOutputStream());

        ObjectOutputStream clientObjectOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream clientObjectIn = new ObjectInputStream(socket.getInputStream());

        // Variables necessary from OptionList for the server to work, edited by Zonglin
        ArrayList<Questions> quiz = null;
        ArrayList<String> studentAnswer = null;
        ArrayList<String> correctAnswer = new ArrayList<String>();
        ArrayList<String> submission = new ArrayList<String>();
        ArrayList<String> sub = new ArrayList<String>();

        String userName = readClient.readLine();
        String password = readClient.readLine();
        Account user = new Account(userName, password);


        ArrayList<Quizzes> quizzes = new ArrayList<>(); // JUST AS IN THE CLIENT

        boolean start = true;
        String userType = "";
        while (start) {
            String sl = readClient.readLine();
            if (sl.equals("1")) {
                userName = readClient.readLine();
                password = readClient.readLine();
                Boolean type = Boolean.parseBoolean(readClient.readLine());
                CreateAccount createdAccount = new CreateAccount(userName, password, type);

                boolean create = createdAccount.isCreated();
                writeToClient.println(create);
                writeToClient.flush();
            }

            userName = readClient.readLine();
            password = readClient.readLine();
            user = new Account(userName, password);

            boolean valid = user.isValid();
            boolean teach = user.isTeacher();
            boolean stud = user.isStudent();

            if (valid) {
                if (teach) {
                    userType = "Teacher";
                } else if (stud) {
                    userType = "Student";
                }
                start = false;
            }
            writeToClient.println(valid);
            writeToClient.flush();
            writeToClient.println(teach);
            writeToClient.flush();
            writeToClient.println(stud);
            writeToClient.flush();
        }

        String courseTitle = readClient.readLine();
        Course usersCourse;
        if (userType.equals("Teacher")) {
            usersCourse = new Course(courseTitle);
        } else {
            usersCourse = new Course(courseTitle);
            if (usersCourse.isNewCourseCreated()) {
                usersCourse.deleteCourse();
            }
        }
        writeToClient.println(usersCourse.isNewCourseCreated());
        writeToClient.flush();


        // FILLS THE QUIZZES ARRAYLIST WITH ALL THE QUIZZES IN THE COURSE.
        ArrayList<String> courseQuizTitles = usersCourse.getCourseQuizTitles();
        for (String quizTitle : courseQuizTitles) {
            ArrayList<String> quizText = usersCourse.getQuiz(quizTitle);
            ArrayList<Questions> tempQuestions = new ArrayList<>();

            for (int i = 1; i < quizText.size(); i++) {
                String question = quizText.get(i);
                String option1 = quizText.get(++i);
                String option2 = quizText.get(++i);
                String option3 = quizText.get(++i);
                String option4 = quizText.get(++i);
                String answer = quizText.get(++i);
                int points = Integer.parseInt(quizText.get(++i));
                tempQuestions.add(new Questions(question, option1, option2, option3,
                        option4, answer, points));
            }
            quizzes.add(new Quizzes(tempQuestions, quizTitle));
        }
        clientObjectOut.writeObject(quizzes);
        clientObjectOut.flush();


        boolean teacher = true;
        if (userType.equalsIgnoreCase("Teacher")) {
            while (teacher) {
                int options = Integer.parseInt(readClient.readLine());

                if (options == 1) {
                    teacher = false;
                } else if (options == 2) {
                    //ADDS QUIZ ARRAYLIST AND QUIZ NAME TO QUIZZES ARRAYLIST. ALSO SAVES IT TO THE COURSE
                    quizzes.add((Quizzes) clientObjectIn.readObject());
                    boolean added = usersCourse.addQuiz((ArrayList<String>) clientObjectIn.readObject());
                    writeToClient.println(added);
                    writeToClient.flush();
                } else if (options == 3) {
                    String quizName = "";
                    if (quizzes.size() != 0) {
                        quizName = readClient.readLine();
                    }
                    for (int i = 0; i < quizzes.size(); i++) {
                        if (quizzes.get(i).getName().equalsIgnoreCase(quizName)) {
                            ArrayList<String> quizText = usersCourse.getQuiz(quizName);
                            clientObjectOut.writeObject(quizText);
                            clientObjectOut.flush();

                            usersCourse.deleteQuiz(quizName);
                            quizText = (ArrayList<String>) clientObjectIn.readObject();
                            usersCourse.addQuiz(quizText);

                            quizzes = (ArrayList<Quizzes>) clientObjectIn.readObject();
                            break;
                        }
                    }
                } else if (options == 4) {
                    String quizName2 = "";
                    if (quizzes.size() != 0) {
                        quizName2 = readClient.readLine();
                    }
                    for (int i = 0; i < quizzes.size(); i++) {
                        if (quizzes.get(i).getName().equalsIgnoreCase(quizName2)) {
                            String sure = readClient.readLine();
                            if (!sure.equalsIgnoreCase("no")) {
                                quizzes.remove(i);
                                usersCourse.deleteQuiz(quizName2);  // DELETES QUIZ FROM THE COURSE
                            }
                            break;
                        }
                    }
                } else if (options == 5) {
                    if (Boolean.parseBoolean(readClient.readLine())) {   // IF TEACHER'S FILE EXISTS
                        ArrayList quizText = (ArrayList) clientObjectIn.readObject();  // READS FILE QUIZ TEXT
                        quizzes = (ArrayList<Quizzes>) clientObjectIn.readObject();  // READS UPDATED QUIZZES

                        boolean added = usersCourse.addQuiz(quizText); // SAVES QUIZ TO THE COURSE
                        writeToClient.println(added);
                        writeToClient.flush();
                    }
                } else if (options == 6) {
                    // TODO: Option 6 to be done by Zonglin
                    int quizNum1 = Integer.parseInt(readClient.readLine());
                    String name = readClient.readLine();
                    String key = readClient.readLine();
                    int i = Integer.parseInt(readClient.readLine());

                    if (user.getSubmission(quizzes.get(quizNum1 - 1).getName(), name, key, i) != null) {

                        writeToClient.println("validInfo");
                        writeToClient.flush();
                        sub = user.getSubmission(quizzes.get(quizNum1 - 1).getName(), name, key, i);

                        //System.out.println("Attempt: " + i);
                        writeToClient.println("Attempt: " + i);
                        writeToClient.flush();

                        for (String v : sub) {
                            writeToClient.println(v);
                            writeToClient.flush();
                        }

                    } else if (options == 7) {
                        String newUser = readClient.readLine();
                        String newPass = readClient.readLine();
                        user.editAccount(newUser, newPass);
                    } else if (options == 8) {
                        user.deleteAccount();
                        teacher = false;
                    }

                }
            }

            // TODO: More Client communication (below Client TODO) needs to be added
            // TODO: Below are option 7 for teacher and option 1 - 6 for Student, edit by Zonglin
            // TODO: teacher's option

            // TODO: All options for students
            boolean student = true;

            if (userType.equalsIgnoreCase("Student")) {

                while (student) {

                    int options = Integer.parseInt(readClient.readLine());
                    //scan.nextLine();

                    //STUDENT CHOOSES TO QUIT
                    if (options == 1) {
                        student = false;
                        //STUDENT CHOOSES TO TAKE A QUIZ
                    } else if (options == 2) {

                        //PRINTS LIST OF QUIZZES BY NAME
                        for (int i = 0; i < quizzes.size(); i++) {
                            System.out.println((i + 1) + ". " + quizzes.get(i).getName());
                        }

                        int quizNum = Integer.parseInt(readClient.readLine());
                        //scan.nextLine();

                        if (quizNum > 0 && quizNum <= quizzes.size()) {
                            String longString = "";
                            studentAnswer = new ArrayList<String>();
                            //PRINTS EACH QUESTION AND OPTIONS, THEN STORES STUDENTS ANSWERS IN ARRAYLIST "STUDENTANSWER"
                            for (int i = 0; i < quizzes.get(quizNum - 1).getQuestions().size(); i++) {
                                boolean askAgain = false;
                                String guess = "";
                                do {
                                    askAgain = false;
                                    guess = readClient.readLine();

                                    if (!(guess.equals("a") || guess.equals("b") || guess.equals("c") || guess.equals("d")
                                            || guess.equals("file"))) {
                                        askAgain = true;
                                    }
                                } while (askAgain);

                                // TODO: store the file uploaded to the server
                                if (guess.equalsIgnoreCase("file")) {

                                    String file = readClient.readLine();

                                    ArrayList<String> list = new ArrayList<>();
                                    File f = new File(file);

                                    try {

                                        FileReader fr = new FileReader(f);
                                        BufferedReader bfr = new BufferedReader(fr);
                                        String line = bfr.readLine();

                                        while (line != null) {
                                            list.add(line);
                                            line = bfr.readLine();
                                        }

                                        bfr.close();

                                    } catch (FileNotFoundException e) {
                                        throw e;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    //for each new line, a '/' is added for Grading.java to read
                                    //print longString to know the format of file submission of student
                                    for (String a : list) {
                                        longString += a + "/";
                                    }

                                    studentAnswer.add(longString);

                                } else {

                                    studentAnswer.add(guess);

                                }
                            }
                            System.out.println("Would you like to submit? (yes/no)");
                            String submit = readClient.readLine();


                            if (submit.equalsIgnoreCase("no") || submit.equalsIgnoreCase("n")) {
                                System.out.println("Alright. Your quiz will not be submitted.");
                                //studentAnswers.remove(index);
                                continue;
                            } else {
                                //Setup input for the quiz to be automatically graded
                                ArrayList<String> correctAnswerList = new ArrayList<String>();
                                ArrayList<Integer> PointList = new ArrayList<Integer>();

                                for (int j = 0; j < quizzes.get(quizNum - 1).getQuestions().size(); j++) {
                                    correctAnswerList.add(quizzes.get(quizNum - 1).getQuestions().get(j).getAnswer());
                                    PointList.add(quizzes.get(quizNum - 1).getQuestions().get(j).getPoints());
                                }

                                Grading testGrade = new Grading(quizzes.get(quizNum - 1).getQuestions(),
                                        correctAnswerList, PointList);
                                submission = testGrade.autoGrade(studentAnswer,
                                        quizzes.get(quizNum - 1).getName(), userName);

                                user.addSubmission(submission);

                            }

                            System.out.println("Quiz submitted!");
                        }

                        if (quizNum < 1 || quizNum > quizzes.size()) {
                            System.out.println("That is not a valid option!");
                        }
                    } else if (options == 3) {

                        if (quizzes.size() != 0) {
                            System.out.println("Which quiz would you like to see?");
                            //PRINTS LIST OF QUIZZES BY NAME
                            for (int i = 0; i < quizzes.size(); i++) {
                                System.out.println((i + 1) + ". " + quizzes.get(i).getName());
                            }
                            //int quizNum1 = scan.nextInt();
                            int quizNum1 = Integer.parseInt(readClient.readLine());
                            //scan.nextLine();

                            String name = userName;
                            String key = password;

                            System.out.println("Please input the attempt number: ");
                            //int i = scan.nextInt();
                            int i = Integer.parseInt(readClient.readLine());

                            if (user.getSubmission(quizzes.get(quizNum1 - 1).getName(), name, key, i) != null) {

                                writeToClient.println("validInfo");
                                writeToClient.flush();
                                sub = user.getSubmission(quizzes.get(quizNum1 - 1).getName(), name, key, i);

                                //System.out.println("Attempt: " + i);
                                writeToClient.println("Attempt: " + i);
                                writeToClient.flush();

                                for (String v : sub) {
                                    writeToClient.println(v);
                                    writeToClient.flush();
                                }

                            }

                        } else if (options == 4) {

                            String newUser = readClient.readLine();
                            String newPass = readClient.readLine();
                            user.editAccount(newUser, newPass);

                        } else if (options == 5) {
                            user.deleteAccount();
                            student = false;
                        } else {

                        }
                    }
                }

                writeToClient.close();
                readClient.close();
                serverSocket.close();
                socket.close();
            }
        }
    }
}
