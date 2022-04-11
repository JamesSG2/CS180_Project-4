import java.io.*;
import java.util.ArrayList;

/**
 * Account
 *
 * Handles the personal information of students and teachers as well as their past submissions.
 *
 * @author James Gilliam, L15
 *
 * @version 4/10/2022
 *
 */
public class Account {
    private boolean valid;
    private boolean teacher;
    private boolean student;
    private ArrayList<String> accountsData;
    private String userName;
    private String password;
    private String submissionSpacer = "--------------------------------------------------";
    private String accountSpacer = "||||||||||||||||||||||||||||||||||||||||||||||||||||||||";

    public Account(String userName, String password) throws IOException {
        accountsData = new ArrayList<>();
        readAccountsDataFile();

        this.userName = userName;
        this.password = password;

        int accountIndex = getAccountIndex(userName, password);

        if (accountsData.get(accountIndex + 1).equals("teacher")) {
            teacher = true;
        } else if (accountsData.get(accountIndex + 1).equals("student")) {
            student = true;
        }
    }

    public void addSubmission(ArrayList<String> submission) throws IOException {
        readAccountsDataFile();

        int i = getAccountIndex(userName, password);
        while (!accountsData.get(i).equals(accountSpacer)) {
            i++;
        }
        for (int j = 0; j < submission.size(); j++) {
            accountsData.add(i + j, submission.get(j));
        }
        accountsData.add(i + submission.size(), submissionSpacer);

        writeAccountsDataFile();
    }

    public ArrayList<String> getSubmission(String quizTitle, String userName, String password) throws IOException {
        readAccountsDataFile();
        Account user = new Account(userName, password);
        if (!user.isValid()) {
            return null;
        }

        int i = getAccountIndex(userName, password);
        while (!accountsData.get(i).equals(quizTitle)) {
            i++;
        }
        ArrayList<String> submission = new ArrayList<>();
        while (!accountsData.get(i).equals(submissionSpacer)) {
            submission.add(accountsData.get(i));
            i++;
        }
        return submission;
    }

    public void deleteAccount() throws IOException {
        readAccountsDataFile();
        int i = getAccountIndex(userName, password);
        while (!accountsData.get(i).equals(accountSpacer)) {
            accountsData.remove(i);
        }
        accountsData.remove(i);
        writeAccountsDataFile();
    }

    public void editAccount(String newUsername, String newPassword) throws IOException {
        readAccountsDataFile();
        accountsData.set(getAccountIndex(userName, password), newUsername + newPassword);
        writeAccountsDataFile();
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

    private void writeAccountsDataFile() throws IOException {
        File f = new File("AccountsData.txt");
        PrintWriter writer = new PrintWriter(f);
        for (String line : accountsData) {
            writer.println(line);
        }
        writer.close();
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isTeacher() {
        return teacher;
    }

    public boolean isStudent() {
        return student;
    }

    private int getAccountIndex(String userName, String password) {
        int accountIndex = 0;
        for (int i = 0; i < accountsData.size(); i++) {
            if (accountsData.get(i).equals(userName + password)) {
                accountIndex = i;
                valid = true;
                break;
            }
        }
        return accountIndex;
    }
}
