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
public class Account implements Serializable {
    private boolean valid;
    private boolean teacher;
    private boolean student;
    private ArrayList<String> accountsData;
    private String userName;
    private String password;
    private String submissionSpacer = "--------------------------------------------------";
    private String accountSpacer = "||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
    private boolean created;
    private static Object accountGatekeeper = new Object();

    public Account(String userName, String password) throws IOException {
        synchronized (accountGatekeeper) {
            readAccountsDataFile();

            this.userName = userName;
            this.password = password;

            int accountIndex = getAccountIndex(userName, password);

            if (accountsData.size() > 0) {
                if (accountsData.get(accountIndex + 1).equals("Status: teacher")) {
                    teacher = true;
                } else if (accountsData.get(accountIndex + 1).equals("Status: student")) {
                    student = true;
                }
            }
        }
    }  // For accessing an old account

    public Account(String userName, String password, boolean isTeacher) throws IOException {
        synchronized (accountGatekeeper) {
            readAccountsDataFile();

            if (accountsData.isEmpty()) {
                accountsData.add(accountSpacer);
            }

            for (int i = 1; i < accountsData.size(); i++) {
                String line = accountsData.get(i);
                if (line.length() >= userName.length()) {
                    if (line.substring(0, userName.length()).equals(userName)) {
                        if (accountsData.get(i - 1).equals(accountSpacer)) {
                            created = false;
                            return;
                        }
                    }
                }
            }

            accountsData.add(userName + " " + password);
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
    }  // For initially creating an account

    public void addSubmission(ArrayList<String> submission) throws IOException {
        synchronized (accountGatekeeper) {
            readAccountsDataFile();

            int i = getAccountIndex(userName, password);
            while (i < accountsData.size() && !(accountsData.get(i).equals(accountSpacer))) {
                i++;
            }

            for (int j = 0; j < submission.size(); j++) {
                accountsData.add(i + j, submission.get(j));
            }
            accountsData.add(i + submission.size(), submissionSpacer);

            writeAccountsDataFile();
        }
    }

    public ArrayList<String> getSubmission(String quizTitle, String userName, String password,
                                           int attemptNumber) throws IOException {
        synchronized (accountGatekeeper) {
            readAccountsDataFile();
            Account user = new Account(userName, password);
            if (!user.isValid()) {
                return null;
            }

            int i = user.getAccountIndex(userName, password);
            int j = 1;
            while (!((accountsData.get(i).equals(quizTitle) && (j == attemptNumber)) ||
                    ((i + 1) == accountsData.size()))) {
                if (accountsData.get(i).equals(quizTitle)) {
                    j++;
                }
                i++;
            }
            if ((i + 1) == accountsData.size()) {
                return null;
            }
            ArrayList<String> submission = new ArrayList<>();
            while (!accountsData.get(i).equals(submissionSpacer)) {
                submission.add(accountsData.get(i));
                i++;
            }
            return submission;
        }
    }

    public boolean setSubmission(String quizTitle, int attemptNumber, String userName, String password,
                                 ArrayList<String> newSubmission) throws IOException {
        synchronized (accountGatekeeper) {
            readAccountsDataFile();
            Account user = new Account(userName, password);
            if (!user.isValid()) {
                return false;
            }

            int i = user.getAccountIndex(userName, password);
            int j = 1;
            while (!((accountsData.get(i).equals(quizTitle) && (j == attemptNumber)) ||
                    ((i + 1) == accountsData.size()))) {
                if (accountsData.get(i).equals(quizTitle)) {
                    j++;
                }
                i++;
            }
            if ((i + 1) == accountsData.size()) {
                return false;
            }

            while (!accountsData.get(i).equals(submissionSpacer)) {
                accountsData.remove(i);
            }

            for (int k = 0; k < newSubmission.size(); k++) {
                accountsData.add((i + k), newSubmission.get(k));
            }
            writeAccountsDataFile();
            return true;
        }
    }

    public void deleteAccount() throws IOException {
        synchronized (accountGatekeeper) {
            readAccountsDataFile();
            int i = getAccountIndex(userName, password);
            while (!accountsData.get(i).equals(accountSpacer)) {
                accountsData.remove(i);
            }
            accountsData.remove(i);
            writeAccountsDataFile();
        }
    }

    public boolean editAccount(String newUsername, String newPassword) throws IOException {
        synchronized (accountGatekeeper) {
            readAccountsDataFile();
            if (newUsername != userName) {
                String prevLine = "";
                for (String line : accountsData) {
                    if (prevLine.equals(accountSpacer)) {
                        if (line.length() >= newUsername.length()) {
                            if (line.substring(0, newUsername.length()).equals(newUsername)) {
                                return false;
                            }
                        }
                    }
                    prevLine = line;
                }
            }
            accountsData.set(getAccountIndex(userName, password), newUsername + " " + newPassword);
            userName = newUsername;
            password = newPassword;
            writeAccountsDataFile();
            return true;
        }
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

        for (String line : accountsData) {
            writer.println(line);
        }
        writer.close();
    }

    public boolean isCreated() {
        return created;
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
        for (int i = 1; i < accountsData.size(); i++) {
            if (accountsData.get(i - 1).equals(accountSpacer)) {
                if (accountsData.get(i).equals(userName + " " + password)) {
                    accountIndex = i;
                    valid = true;
                    break;
                }
            }
        }
        return accountIndex;
    }
}
