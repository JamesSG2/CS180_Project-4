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
 * @author James Gilliam, Ian Fienberg L15
 *
 * @version 4/18/2022
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
        writeToServer.println(userName);
        writeToServer.flush();
        writeToServer.println(password);
        writeToServer.flush();
        // Initialize account in server

        boolean start = true;
        while (start) {
            System.out.println("What would you like to do?\n1. Sign up\n2. Log in");
            int sl = scan.nextInt();
            writeToServer.println(sl);
            writeToServer.flush();

            if (sl == 1) {
                System.out.println("What would like your username to be?");
                scan.nextLine();
                userName = scan.nextLine();
                System.out.println("What would like your password to be?");
                password = scan.nextLine();
                boolean type = false;
                System.out.println("Are you a teacher or student?\n1. Teacher\n2. Student");
                int ts = scan.nextInt();
                if (ts == 1) {
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
                    System.out.println("The username is already taken! Account was not created.");
                }
            }

            System.out.println("LOG IN");
            System.out.println("What is your username?");
            scan.nextLine();
            userName = scan.nextLine();
            System.out.println("What is your password?");
            password = scan.nextLine();

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
                System.out.println("That is not a valid account!");
            }
        }

        // WILL ACCESS THE COURSE REQUESTED BY THE USER. TEACHERS CAN CREATE COURSES IF THEY WISH.
        System.out.println("What course would you like to access?");
        if (userType.equals("Teacher")) {
            System.out.println("Note: If it's a new course the course will automatically be created.");
        }
        String courseTitle = scan.nextLine();

        writeToServer.println(courseTitle); // SEND COURSE TITLE TO SERVER TO INITIALIZE THE USER'S COURSE
        writeToServer.flush();

        boolean isCreated = Boolean.parseBoolean(readServer.readLine());

        if (userType.equals("Teacher")) {
            if (isCreated) {
                System.out.println("New course created!");
            } else {
                System.out.println("Old course accessed.");
            }
        } else {
            if (isCreated) {
                System.out.println("Error! That course does not exist.");
            }
        }

        // FILLS THE QUIZZES ARRAYLIST WITH ALL THE QUIZZES IN THE COURSE.
        quizzes = (ArrayList<Quizzes>) serverObjectIn.readObject();

        boolean teacher = true;
        if (userType.equalsIgnoreCase("Teacher")) {
            System.out.println("Hello teacher");
            while (teacher) {
                System.out.println("What would you like to do?");
                //updated parts:
                System.out.println("1. Log out\n" + "2. Create a new quiz\n" + "3. Edit a quiz\n"
                        + "4. Delete a quiz\n"
                        + "5. Upload a quiz\n" + "6. View submissions\n"
                        + "7. Edit account\n" + "8. Delete account");
                int options = scan.nextInt();
                scan.nextLine();

                writeToServer.println(options);  // Server needs option selected to follow the client
                writeToServer.flush();

                //assuming option 1 for teachers is to create a quiz
                //updated: added a while loop for user to choose option until they want to quit

                //TEACHER CHOOSES TO QUIT
                if (options == 1) {
                    System.out.println("Goodbye!");
                    teacher = false;
                }
                //while (options != 0) {
                //TEACHER CHOOSES TO CREATE A QUIZ
                else if (options == 2) {
                    quiz = new ArrayList<Questions>();
                    ArrayList<String> quizText = new ArrayList<>();

                    System.out.println("Enter what you want to call the quiz.");
                    String quizName = scan.nextLine();
                    quizText.add(quizName);

                    System.out.println("How many questions will there be in this quiz?");
                    int numOfQuestions = scan.nextInt();

                    for (int i = 1; i <= numOfQuestions; i++) {
                        System.out.println("What is question " + i + "?");
                        scan.nextLine();
                        String question = scan.nextLine();
                        quizText.add(question);
                        System.out.println("What is option 1?");
                        String option1 = scan.nextLine();
                        quizText.add(option1);
                        System.out.println("What is option 2?");
                        String option2 = scan.nextLine();
                        quizText.add(option2);
                        System.out.println("What is option 3?");
                        String option3 = scan.nextLine();
                        quizText.add(option3);
                        System.out.println("What is option 4?");
                        String option4 = scan.nextLine();
                        quizText.add(option4);

                        //below is changed by Zonglin to prompt the teacher if they want files as submission
                        System.out.println("Which option is the correct answer (a, b, c, d, "
                                + "or otherwise if student should submit a file, please enter 'file')");
                        String answer = scan.nextLine();

                        //if the teacher enter 'file', they should input a String of correct answer allowing AutoGrading
                        if (answer.equals("file")) {
                            System.out.println("/****************");
                            System.out.println("*Please Add '/' for each new line. For example, " +
                                    "the correct answer of:\n*Bright \n*space\n"  +
                                    "*should be written as 'Bright/space/' as there are two lines");
                            System.out.println("/****************");
                            System.out.println("Please enter the correct answer:");
                            answer = scan.nextLine();
                        }

                        quizText.add(answer);

                        System.out.println("How many points is this question worth?");
                        int points = scan.nextInt();
                        quizText.add("" + points);

                        //ADDS QUESTION TO QUIZ ARRAYLIST
                        quiz.add(new Questions(question, option1, option2, option3, option4, answer, points));
                        correctAnswer.add(answer);
                        //just for test:
                        //studentAnswer.add(answer);
                    }

                    //ADDS QUIZ ARRAYLIST AND QUIZ NAME TO QUIZZES ARRAYLIST. ALSO SAVES IT TO THE COURSE
                    quizzes.add(new Quizzes(quiz, quizName));

                    serverObjectOut.writeObject(new Quizzes(quiz, quizName));
                    serverObjectOut.flush();
                    serverObjectOut.writeObject(quizText);
                    serverObjectOut.flush();

                    boolean added = Boolean.parseBoolean(readServer.readLine());
                    if (!added) {
                        System.out.println("Error! That quiz already exists in this course.");
                    } else {
                        System.out.println("Quiz created!");
                    }

                }
                // IF A TEACHER WOULD LIKE TO EDIT A QUIZ
                else if (options == 3) {
                    String quizName = "";
                    if (quizzes.size() != 0) {
                        System.out.println("Enter the quiz title of the quiz you want to edit.");
                        quizName = scan.nextLine();
                        writeToServer.println(quizName);
                        writeToServer.flush();
                    }
                    for (int i = 0; i < quizzes.size(); i++) {
                        if (quizzes.get(i).getName().equalsIgnoreCase(quizName)) {
                            ArrayList<String> quizText = (ArrayList<String>) serverObjectIn.readObject();

                            System.out.println("What would you like to change?");
                            System.out.println("1. Name\n2. Question");
                            int alter = scan.nextInt();

                            // TO CHANGE THE NAME OF A QUIZ
                            if (alter == 1) {
                                System.out.println("What name would you like to change it to?");
                                scan.nextLine();
                                String newName = scan.nextLine();
                                quizzes.get(i).setName(newName);

                                quizText.set(0, newName);

                                System.out.println("Name changed!");
                                // System.out.println(quizzes.toArray());
                                // break;
                                // TO CHANGE AN ENTIRE QUESTION ON A QUIZ

                            } else if (alter == 2) {
                                System.out.println("Which question would you like to change?");
                                int qnum = scan.nextInt();
                                System.out.println("What should this question be?");
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
                                System.out.println("Which option is the correct answer (a, b, c, d, "
                                        + "or otherwise if student should submit a file, please enter 'file')");
                                String answer = scan.nextLine();

                                if (answer.equals("file")) {
                                    System.out.println("/****************");
                                    System.out.println("*Please Add '/' for each new line. For example, " +
                                            "the correct answer of:\n*Bright \n*space\n"  +
                                            "*should be written as 'Bright/space/' as there are two lines");
                                    System.out.println("/****************");
                                    System.out.println("Please enter the correct answer:");
                                    answer = scan.nextLine();
                                }
                                System.out.println("How many points is this question worth?");
                                int points = scan.nextInt();

                                int questionIndex = 1 + (qnum - 1) * 7;
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
                                System.out.println("Quiz edited!");
                                // break;
                            }

                            serverObjectOut.writeObject(quizText);
                            serverObjectOut.flush();
                            serverObjectOut.writeObject(quizzes);
                            serverObjectOut.flush();

                            break;

                            // IF THERE ARE QUIZZES, BUT THE INPUTTED NAME DOESN'T MATCH ANY
                        } else if (i == quizzes.size() - 1) {
                            System.out.println("That is not a name of a current quiz!");
                        }
                    }

                    // IF THE ARRAYLIST OF QUIZZES IS SIZE 0, PRINT AN ERROR MESSAGE AND TRY AGAIN
                    if (quizzes.size() == 0) {
                        System.out.println("You need to create a quiz before you can edit one!");
                    }

                }

                // IF A TEACHER WOULD LIKE TO DELETE AN ENTIRE QUIZ
                else if (options == 4) {
                    String quizName2 = "";
                    if (quizzes.size() != 0) {
                        System.out.println("Enter the quiz title of the quiz you want to delete.");
                        quizName2 = scan.nextLine();

                        writeToServer.println(quizName2);
                        writeToServer.flush();
                    }
                    int p = -1;
                    for (int i = 0; i < quizzes.size(); i++) {
                        if (quizzes.get(i).getName().equalsIgnoreCase(quizName2)) {
                            System.out.println("Are you sure you would like to delete this quiz? (yes/no)");
                            String sure = scan.nextLine();

                            writeToServer.println(sure);
                            writeToServer.flush();
                            if (!sure.equalsIgnoreCase("no")) {
                                quizzes.remove(i);
                                // DELETES QUIZ FROM THE COURSE IN SERVER
                                System.out.println("Quiz deleted!");

                                break;
                            } else {
                                System.out.println("Okay. Your quiz will not be deleted.");
                            }
                            p = i;
                            break;

                            // IF THERE ARE QUIZZES, BUT THE INPUTTED NAME DOESN'T MATCH ANY
                        } else if (i == quizzes.size() - 1) {
                            System.out.println("That is not a name of a current quiz!");
                        }
                        p = i;
                    }

                    // IF THE ARRAYLIST OF QUIZZES IS SIZE 0, PRINT AN ERROR MESSAGE AND TRY AGAIN
                    if (quizzes.size() == 0 && p != 0) {
                        System.out.println("You need to create a quiz before you can delete one!");
                    }
                    // IF A TEACHER WOULD LIKE TO UPLOAD A QUIZ FILE
                } else if (options == 5) {
                    System.out.println("Note: the file must follow the format of quiz title first then for each \n" +
                            "question: the question, the 4 choices, the correct answer, then the point value." +
                            "\nAt the end of the quiz:" +
                            "type \"--------------------------------------------------\". \nEverything is separated " +
                            "with a new line. See CoursesData.txt\n" +
                            "to look at past quizzes made by this program. Follow that format.");
                    System.out.println("What is the name of the quiz file you would like to upload?");
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
                                            System.out.println("Error! Point value must be an integer.");
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

                            serverObjectOut.writeObject(quizText);  // SERVER SAVES QUIZ TO THE COURSE
                            serverObjectOut.flush();
                            serverObjectOut.writeObject(quizzes);
                            serverObjectOut.flush();

                            boolean added = Boolean.parseBoolean(readServer.readLine());
                            if (!added) {
                                System.out.println("Error! That quiz already exists in this course.");
                            }
                            //writer.write("END OF QUIZ\n");
                            buf.close();
                            //writer.close();
                        } else {
                            System.out.println("Error! File Not Found.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(quizzes.get(1).getQuestions().get(1).getQuestion());

                    //IF TEACHER CHOOSES TO VIEW SUBMISSIONS
                } else if (options == 6) {

                    String quizName3 = "";
                    if (quizzes.size() != 0) {
                        System.out.println("Which quiz would you like to see?");
                        //PRINTS LIST OF QUIZZES BY NAME
                        for (int i = 0; i < quizzes.size(); i++) {
                            System.out.println((i + 1) + ". " + quizzes.get(i).getName());
                        }
                        int quizNum1 = scan.nextInt();
                        scan.nextLine();
                        writeToServer.println(quizNum1);
                        writeToServer.flush();


                        System.out.println("Please input the student's username: ");
                        String name = scan.nextLine();
                        writeToServer.println(name);
                        writeToServer.flush();
                        System.out.println("Please input the student's password: ");
                        String key = scan.nextLine();
                        writeToServer.println(key);
                        writeToServer.flush();
                        System.out.println("Please input the student's attempt number: ");
                        int i = scan.nextInt();
                        writeToServer.println(i);
                        writeToServer.flush();

                        if (readServer.readLine().equals("validInfo")) {

                            System.out.println(readServer.readLine());

                            for (String v : sub) {
                                System.out.println(readServer.readLine());
                            }

                            //IF THERE ARE QUIZZES, BUT THE INPUTTED NAME DOESN'T MATCH ANY
                        } else {
                            System.out.println("ERROR! THE INFORMATION IS INVALID!");
                        }

                    }
                    //IF THE ARRAYLIST OF QUIZZES IS SIZE 0, PRINT AN ERROR MESSAGE AND TRY AGAIN
                    if (quizzes.size() == 0) {
                        System.out.println("You need to create a quiz before you can see one!");
                    }


                } else if (options == 7) {
                    System.out.println("What would you like your new username to be?");
                    String newUser = scan.nextLine();
                    System.out.println("What would you like your new password to be?");
                    String newPass = scan.nextLine();

                    // USER'S ACCOUNT EDITED BY SERVER
                    writeToServer.println(newUser);
                    writeToServer.flush();
                    writeToServer.println(newPass);
                    writeToServer.flush();

                } else if (options == 8) {
                    // USER'S ACCOUNT DELETED BY SERVER
                    System.out.println("Account Deleted.\nGoodbye!");
                    teacher = false;
                } else {
                    System.out.println("That is not a valid option! Please enter a number 1-7.");
                }

            }
        }


        // TODO: All codes below are for the student to input
        boolean student = true;

        //int count = 0; //count for attemptNum

        if (userType.equalsIgnoreCase("Student")) {
            System.out.println("Hello student");
            while (student) {
                System.out.println("What would you like to do?");
                System.out.println("1. Log out\n" + "2. Take a quiz\n" + "3. See your submission\n" +
                        "4. Edit account\n" + "5. Delete account");

                //OPTIONS of Student
                int options = scan.nextInt();
                scan.nextLine();
                writeToServer.println(options);
                writeToServer.flush();

                // STUDENT CHOOSES TO QUIT
                if (options == 1) {
                    System.out.println("Goodbye!");
                    student = false;
                    // STUDENT CHOOSES TO TAKE A QUIZ
                } else if (options == 2) {
                    System.out.println("Which quiz would you like to take?");
                    // PRINTS LIST OF QUIZZES BY NAME
                    for (int i = 0; i < quizzes.size(); i++) {
                        System.out.println((i + 1) + ". " + quizzes.get(i).getName());
                    }


                    // QUIZ SELECTED BY STUDENT TO TAKE
                    int quizNum = scan.nextInt();
                    scan.nextLine();
                    writeToServer.println(quizNum);
                    writeToServer.flush();

                    if (quizNum > 0 && quizNum <= quizzes.size()) {
                        String longString = "";
                        studentAnswer = new ArrayList<String>();
                        // PRINTS EACH QUESTION AND OPTIONS, THEN STORES STUDENTS ANSWERS IN ARRAYLIST "STUDENTANSWER"
                        for (int i = 0; i < quizzes.get(quizNum - 1).getQuestions().size(); i++) {
                            boolean askAgain = false;
                            String guess = "";
                            do {
                                askAgain = false;
                                System.out.println("Question " + (i + 1) + ":");
                                System.out.println(quizzes.get(quizNum - 1).getQuestions().get(i).getQuestion());
                                System.out.println("a) "
                                        + quizzes.get(quizNum - 1).getQuestions().get(i).getOption1());
                                System.out.println("b) "
                                        + quizzes.get(quizNum - 1).getQuestions().get(i).getOption2());
                                System.out.println("c) "
                                        + quizzes.get(quizNum - 1).getQuestions().get(i).getOption3());
                                System.out.println("d) "
                                        + quizzes.get(quizNum - 1).getQuestions().get(i).getOption4());

                                System.out.println("If you would like to attach a file as your answer, " +
                                        "enter \"file\", " +
                                        "otherwise, enter a, b, c or d");

                                //CHOICE MADE BY THE STUDENT ON THE QUESTION
                                guess = scan.nextLine();
                                writeToServer.println(guess);
                                writeToServer.flush();

                                if (!(guess.equals("a") || guess.equals("b") || guess.equals("c") || guess.equals("d")
                                        || guess.equals("file"))) {
                                    System.out.println("Error! Invalid option.");
                                    askAgain = true;
                                }
                            } while (askAgain);

                            //TODO: Upload a file to server
                            if (guess.equalsIgnoreCase("file")) {


                                //FILE NAME TO BE UPLOADED BY THE STUDENT

                                System.out.println("please input the name of the file");
                                String file = scan.nextLine();
                                writeToServer.println(file);
                                writeToServer.flush();

                            }
                        }


                        System.out.println("Would you like to submit? (yes/no)");
                        String submit = scan.nextLine();
                        writeToServer.println(submit);
                        writeToServer.flush();

                        if (submit.equalsIgnoreCase("no") || submit.equalsIgnoreCase("n")) {
                            System.out.println("Alright. Your quiz will not be submitted.");
                            //studentAnswers.remove(index);
                            continue;
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

                        //STUDENT'S CHOICE ON WHICH QUIZ TO SEE
                        int quizNum1 = scan.nextInt();
                        writeToServer.println(quizNum1);
                        writeToServer.flush();

                        String name = userName;
                        String key = password;

                        System.out.println("Please input the attempt number: ");
                        int i = scan.nextInt();

                        writeToServer.println(i);
                        writeToServer.flush();


                        // TODO: check the condition for Client to print the right thing
                        if (readServer.readLine().equals("validInfo")) {

                            System.out.println(readServer.readLine());

                            for (String v : sub) {
                                System.out.println(readServer.readLine());
                            }

                        } else {
                            System.out.println("ERROR! THE INFORMATION IS INVALID!");
                        }
                    }

                } else if (options == 4) {
                    System.out.println("What would you like your new username to be?");
                    String newUser = scan.nextLine();
                    writeToServer.println(newUser);
                    writeToServer.flush();
                    System.out.println("What would you like your new password to be?");
                    String newPass = scan.nextLine();
                    writeToServer.println(newPass);
                    writeToServer.flush();

                    //lo.editAccount(newUser, newPass);
                } else if (options == 5) {
                    //lo.deleteAccount();
                    System.out.println("Account Deleted.\nGoodbye!");
                    student = false;
                } else {
                    System.out.println("That is not a valid option! Please enter a number 1-3");
                }
            }
        }
        socket.close();
    }
}
