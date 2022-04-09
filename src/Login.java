import java.io.*;
import java.util.ArrayList;

public class Login {
    private boolean valid;
    private boolean teacher;
    private boolean student;
    private ArrayList<String> accountsData;
    private int accountIndex;

    public Login(String userName, String password) throws IOException {
        File f = new File("AccountsData.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        while (line != null) {
            accountsData.add(line);
            line = br.readLine();
        }
        br.close();

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

    public boolean isValid() {
        return valid;
    }

    public boolean isTeacher() {
        return teacher;
    }

    public boolean isStudent() {
        return student;
    }
}
