import java.io.*;
import java.util.ArrayList;

/**
 * CreateAccount
 *
 * Creates an account and stores the username, password and teacher/student status in the
 * AccountsData.txt to be accessed later
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
        accountsData = new ArrayList<>();
        readAccountsDataFile();
        for (String line : accountsData) {
            if (line.length() >= userName.length()) {
                if (line.substring(0, userName.length()).equals(userName)) {
                    created = false;
                    return;
                }
            }
        }


        accountsData.add(userName + password);
        if (isTeacher) {
            accountsData.add("Status: teacher");
        } else {
            accountsData.add("Status: student");
        }

        String submissionSpacer = "--------------------------------------------------";
        accountsData.add(submissionSpacer);

        String accountSpacer = "||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
        accountsData.add(accountSpacer);

        writeAccountsDataFile();
        created = true;
    }

    private void readAccountsDataFile() throws IOException {
        File f = new File("AccountsData.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        accountsData = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
            accountsData.add(line);
            line = br.readLine();
        }
        br.close();
    }

    private void writeAccountsDataFile() throws IOException {
        File f = new File("AccountsData.txt");
        PrintWriter writer = new PrintWriter(f);

        readAccountsDataFile();
        for (String line : accountsData) {
            writer.append(null);
        }

        for (String line : accountsData) {
            writer.println(line);
        }
        writer.close();
    }

    public boolean isCreated() {
        return created;
    }
}
