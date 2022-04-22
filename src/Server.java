import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

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
public class Server {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(4242);

        System.out.println("Waiting for the client to connect...");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");


        BufferedReader readClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writeToClient = new PrintWriter(socket.getOutputStream());

        // Variables necessary from OptionList for the server to work, edited by Zonglin
        ArrayList<Questions> quiz = null;
        ArrayList<String> studentAnswer = null;
        ArrayList<String> correctAnswer = new ArrayList<String>();
        ArrayList<Quizzes> quizzes = new ArrayList<>();
        ArrayList<String> submission = new ArrayList<String>();
        ArrayList<String> sub = new ArrayList<String>();
        //

        String userName = readClient.readLine();
        String password = readClient.readLine();
        Account user = new Account(userName, password);

        boolean start = true;
        boolean valid = false;
        boolean teach = false;
        boolean stud = false;
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
            valid = user.isValid();
            teach = user.isTeacher();
            stud = user.isStudent();
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
            writeToClient.println(valid);
            writeToClient.flush();
            writeToClient.println(teach);
            writeToClient.flush();
            writeToClient.println(stud);
            writeToClient.flush();


        }

        // TODO: More Client communication to (below Client TODO) needs to be added


        // TODO: Below are option 7 for teacher and option 1 - 6 for Student, edit by Zonglin

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

        // TODO: teacher's option
        /*
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

                //assuming option 1 for teachers is to create a quiz
                //updated: added a while loop for user to choose option until they want to quit

                //TEACHER CHOOSES TO QUIT
                // TODO: do this part at the end
                if (options == 1) {
                    System.out.println("Goodbye!");
                    teacher = false;
                } else if (options == 7) {
                    System.out.println("What would you like your new username to be?");
                    String newUser = scan.nextLine();
                    System.out.println("What would you like your new password to be?");
                    String newPass = scan.nextLine();
                    lo.editAccount(newUser, newPass);
                } else if (options == 8) {
                    lo.deleteAccount();
                    System.out.println("Account Deleted.\nGoodbye!");
                    teacher = false;
                } else {
                    System.out.println("That is not a valid option! Please enter a number 1-7.");
                }

            }
        }


         */

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
        }
    }
}