import java.io.*;
import java.util.ArrayList;

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
    private boolean created;
    private ArrayList<String> accountsData;

    public CreateAccount(String userName, String password, boolean isTeacher) throws IOException {
        readAccountsDataFile();
        for (String line : accountsData) {
            if (line.substring(userName.length()).equals(userName)) {
                created = true;
                break;
            }
        }

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

    private void readAccountsDataFile() throws IOException {
        File f = new File("AccountsData.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        while (line != null) {
            accountsData.add(line);
            line = br.readLine();
        }
        br.close();
    }

    public boolean isCreated() {
        return created;
    }
}
