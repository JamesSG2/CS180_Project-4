import java.io.*;
import java.util.ArrayList;

public class Account {
    private boolean valid;
    private boolean teacher;
    private boolean student;
    private ArrayList<String> accountsData;
    private int accountIndex;
    private String submissionSpacer = "--------------------------------------------------";
    private String accountSpacer = "||||||||||||||||||||||||||||||||||||||||||||||||||||||||";

    public Account(String userName, String password) throws IOException {
        accountsData = new ArrayList<>();
        readAccountsDataFile();

        for (int i = 0; i < accountsData.size(); i++) {
            if (accountsData.get(i).equals(userName + password)) {
                accountIndex = i;
                valid = true;
                break;
            }
        }

        if (accountsData.get(accountIndex + 1).equals("teacher")) {
            teacher = true;
        } else if (accountsData.get(accountIndex + 1).equals("student")) {
            student = true;
        }
    }

    public void addSubmission(ArrayList<String> submission) throws IOException {
        readAccountsDataFile();

        int i = accountIndex;
        while (!accountsData.get(i).equals(accountSpacer)) {
            i++;
        }
        for (int j = 0; j < submission.size(); j++) {
            accountsData.add(i + j, submission.get(j));
        }
        accountsData.add(i + submission.size(), submissionSpacer);

        File f = new File("AccountsData.txt");
        PrintWriter writer = new PrintWriter(f);
        for (String line : accountsData) {
            writer.println(line);
        }
        writer.close();
    }

    public ArrayList<String> getSubmission(String quizTitle, String userName, String password) throws IOException {
        readAccountsDataFile();
        Account user = new Account(userName, password);
        if (!user.isValid()) {
            return null;
        }

        int i = user.getAccountIndex();
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

    public boolean isValid() {
        return valid;
    }

    public boolean isTeacher() {
        return teacher;
    }

    public boolean isStudent() {
        return student;
    }

    private int getAccountIndex() {
        return accountIndex;
    }
}
