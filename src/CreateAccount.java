import java.io.*;

/**
 * CreateAccount
 *
 * Creates an account and stores the username, password and teacher/student status in the AccountsData.txt to be
 * accessed later
 *
 * @author James Gilliam, L15
 *
 * @version 4/10/2022
 *
 */
public class CreateAccount {
    public CreateAccount(String userName, String password, boolean isTeacher) throws IOException {
        File f = new File("AccountsData.txt");
        PrintWriter writer = new PrintWriter(f);

        writer.println(userName + password);
        if (isTeacher) {
            writer.println("teacher");
        } else {
            writer.println("student");
        }

        String submissionSpacer = "--------------------------------------------------";
        writer.println(submissionSpacer);

        String accountSpacer = "||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
        writer.println(accountSpacer);

        writer.close();
    }
}
