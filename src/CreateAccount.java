import java.io.*;

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
        writer.close();
    }
}
