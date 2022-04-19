import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//A class to test the function in Grading.java, should not be implemented with the Client.java or OptionList.java
//only contains option 1-2 for teacher and 1-3 for student
public class GradingTest {

    public static void main(String[] args) throws IOException {

        // Ian's main method + updated initialization for ArrayList<Questions> quiz = new ArrayList<>();
        //updated parts:
        Scanner scan = new Scanner(System.in);
        ArrayList<Questions> quiz = null;
        ArrayList<String> studentAnswer = null;
        ArrayList<String> correctAnswer = new ArrayList<String>();
        ArrayList<Quizzes> quizzes = new ArrayList<>();
        String grade = "";
        String user = "";
        //updated by Zonglin:
        ArrayList<String> submission = new ArrayList<String>();
        ArrayList<String> sub = new ArrayList<String>();

        //changed by Zonglin:
        String userName = "";
        String password = "";
        Account lo = new Account(userName, password);

        boolean start = true;
        while (start) {
            System.out.println("What would you like to do?\n1. Sign up\n2. Log in");
            int sl = scan.nextInt();
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

                CreateAccount ca = new CreateAccount(userName, password, type);

                boolean create = ca.isCreated();
                if (!create) {
                    System.out.println("The username is already taken! Account was not created.");
                }


            }
            System.out.println("LOG IN");
            System.out.println("What is your username?");
            scan.nextLine();
            userName = scan.nextLine();
            System.out.println("What is your password?");
            password = scan.nextLine();
            lo = new Account(userName, password);
            boolean valid = lo.isValid();
            boolean teach = lo.isTeacher();
            boolean stud = lo.isStudent();

            if (valid) {
                if (teach) {
                    user = "Teacher";
                } else if (stud) {
                    user = "Student";
                }
                start = false;
            } else {
                System.out.println("That is not a valid account!");
            }
        }

        //WILL READ QUIZINFO.TXT AND ADD PREVIOUS QUIZZES TO "quizzes" ARRAYLIST
        try {
            File fi = new File("QuizInfo.txt");
            fi.createNewFile();
            if (fi.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(fi));
                            /*while ((p = br.readLine()) != null) {
                                log.add(p);
                            }*/
                String p = "";
                while ((p = br.readLine()) != null) {
                    if (p.length() > 0) {
                        String quizName = p;
                        String maybe = "";
                        boolean q = true;
                        ArrayList<Questions> tempQuestions = new ArrayList<>();
                        for (int i = 0; i < 1; i++) {
                            String question;
                            if (q) {
                                question = br.readLine();
                            } else {
                                question = maybe;
                            }
                            String option1 = br.readLine();
                            String option2 = br.readLine();
                            String option3 = br.readLine();
                            String option4 = br.readLine();
                            String answer = br.readLine();
                            int points = Integer.parseInt(br.readLine());
                            maybe = br.readLine();
                            if (maybe.equals("END OF QUIZ")) {
                            } else {
                                q = false;
                                i--;
                            }
                            tempQuestions.add(new Questions(question, option1, option2, option3,
                                    option4, answer, points));
                        }
                        quizzes.add(new Quizzes(tempQuestions, quizName));
                    }
                }
                //writer.write("END OF QUIZ\n");
                br.close();
                //writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean teacher = true;
        if (user.equalsIgnoreCase("Teacher")) {
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

                //assuming option 1 for teachers is to create a quiz
                //updated: added a while loop for user to choose option until they want to quit

                //TEACHER CHOOSES TO QUIT
                if (options == 1) {
                    System.out.println("Goodbye!");
                    teacher = false;
                }
                //TEACHER CHOOSES TO CREATE A QUIZ
                else if (options == 2) {
                    quiz = new ArrayList<Questions>();
                    System.out.println("Enter the course name and what you what to call the quiz. \n" +
                            "(i.e. Course Name: quiz#1) Note: If it's a new course the course will " +
                            "automatically be added");
                    String quizName = scan.nextLine();

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

                        //ADDS QUESTION TO QUIZ ARRAYLIST
                        quiz.add(new Questions(question, option1, option2, option3, option4, answer, points));
                        correctAnswer.add(answer);
                        //just for test:
                        //studentAnswer.add(answer);
                    }
                    //ADDS QUIZ ARRAYLIST AND QUIZ NAME TO QUIZZES ARRAYLIST
                    quizzes.add(new Quizzes(quiz, quizName));
                    try {
                        File f = new File("QuizInfo.txt");
                        f.createNewFile();
                        if (f.exists()) {
                            /*BufferedReader br = new BufferedReader(new FileReader(f));
                            while ((p = br.readLine()) != null) {
                                log.add(p);
                            }*/
                            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
                                    "QuizInfo.txt", true)));
                            int quizIndex = 0;
                            for (int i = 0; i < quizzes.size(); i++) {
                                if (quizzes.get(i).getName().equals(quizName)) {
                                    quizIndex = i;
                                    break;
                                }
                            }
                            //WRITES ENTIRE QUIZ TO QUIZINFO.TXT
                            writer.write(quizzes.get(quizIndex).getName() + "\n");
                            for (int i = 0; i < quizzes.get(quizIndex).getQuestions().size(); i++) {
                                writer.write(quizzes.get(quizIndex).getQuestions().get(i).getQuestion() + "\n");
                                writer.write(quizzes.get(quizIndex).getQuestions().get(i).getOption1() + "\n");
                                writer.write(quizzes.get(quizIndex).getQuestions().get(i).getOption2() + "\n");
                                writer.write(quizzes.get(quizIndex).getQuestions().get(i).getOption3() + "\n");
                                writer.write(quizzes.get(quizIndex).getQuestions().get(i).getOption4() + "\n");
                                writer.write(quizzes.get(quizIndex).getQuestions().get(i).getAnswer() + "\n");
                                writer.write(quizzes.get(quizIndex).getQuestions().get(i).getPoints() + "\n");
                            }
                            writer.write("END OF QUIZ\n");
                            //br.close();
                            writer.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Quiz created!");
                }
                //while (options != 0) {

            }
        }
        boolean student = true;

        if (user.equalsIgnoreCase("Student")) {
            System.out.println("Hello student");
            while (student) {
                System.out.println("What would you like to do?");
                System.out.println("1. Log out\n" + "2. Take a quiz\n" + "3. See your submission\n" +
                        "4. Edit account\n" + "5. Delete account");
                int options = scan.nextInt();
                scan.nextLine();
                //STUDENT CHOOSES TO QUIT
                if (options == 1) {
                    System.out.println("Goodbye!");
                    student = false;
                    //STUDENT CHOOSES TO TAKE A QUIZ
                } else if (options == 2) {
                    System.out.println("Which quiz would you like to take?");
                    //PRINTS LIST OF QUIZZES BY NAME
                    for (int i = 0; i < quizzes.size(); i++) {
                        System.out.println((i + 1) + ". " + quizzes.get(i).getName());
                    }
                    int quizNum = scan.nextInt();
                    scan.nextLine();

                    if (quizNum > 0 && quizNum <= quizzes.size()) {
                        String longString = "";
                        studentAnswer = new ArrayList<String>();
                        //PRINTS EACH QUESTION AND OPTIONS, THEN STORES STUDENTS ANSWERS IN ARRAYLIST "STUDENTANSWER"
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
                                guess = scan.nextLine();

                                if (!(guess.equals("a") || guess.equals("b") || guess.equals("c") || guess.equals("d")
                                        || guess.equals("file"))) {
                                    System.out.println("Error! Invalid option.");
                                    askAgain = true;
                                }
                            } while (askAgain);

                            if (guess.equalsIgnoreCase("file")) {

                                System.out.println("please input the name of the file");
                                String file = scan.nextLine();

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

                                for (String a : list) {
                                    longString += a + "/";
                                }

                                studentAnswer.add(longString);

                            } else {

                                studentAnswer.add(guess);

                            }
                        }
                        System.out.println("Would you like to submit? (yes/no)");
                        String submit = scan.nextLine();

                        if (submit.equalsIgnoreCase("no") || submit.equalsIgnoreCase("n")) {
                            System.out.println("Alright. Your quiz will not be submitted.");
                            //studentAnswers.remove(index);
                            continue;
                        } else {
                            //to automatically grade
                            ArrayList<String> tempAnswerList = new ArrayList<String>();
                            ArrayList<Integer> tempPointList = new ArrayList<Integer>();

                            for (int j = 0; j < quizzes.get(quizNum - 1).getQuestions().size(); j++) {
                                tempAnswerList.add(quizzes.get(quizNum - 1).getQuestions().get(j).getAnswer());
                                tempPointList.add(quizzes.get(quizNum - 1).getQuestions().get(j).getPoints());
                            }

                            Grading testGrade = new Grading(quizzes.get(quizNum - 1).getQuestions(),
                                    tempAnswerList, tempPointList);
                            submission = testGrade.autoGrade(studentAnswer,
                                    quizzes.get(quizNum - 1).getName(), userName);

                            lo.addSubmission(submission);

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
                        int quizNum1 = scan.nextInt();
                        scan.nextLine();

                        String name = userName;
                        String key = password;

                        System.out.println("Please input the attempt number: ");
                        int i = scan.nextInt();
                        if (lo.getSubmission(quizzes.get(quizNum1 - 1).getName(), name, key, i) != null) {

                            sub = lo.getSubmission(quizzes.get(quizNum1 - 1).getName(), name, key, i);

                            System.out.println("Attempt: " + i);

                            for (String v : sub) {
                                System.out.println(v);
                            }

                        } else {
                            System.out.println("ERROR! THE INFORMATION IS INVALID!");
                        }

                    }

                }
            }
        }
    }
}