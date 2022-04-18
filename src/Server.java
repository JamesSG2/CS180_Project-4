import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException  {
        ServerSocket serverSocket = new ServerSocket(4242);

        System.out.println("Waiting for the client to connect...");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");


        BufferedReader readClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writeToClient = new PrintWriter(socket.getOutputStream());


        String userName = readClient.readLine();
        String password = readClient.readLine();
        Account user = new Account(userName, password);

        boolean start = true;
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
            boolean valid = user.isValid();
            boolean teach = user.isTeacher();
            boolean stud = user.isStudent();
            writeToClient.println(valid);
            writeToClient.flush();
            writeToClient.println(teach);
            writeToClient.flush();
            writeToClient.println(stud);
            writeToClient.flush();

            if (valid) {
                start = false;
            }
        }

        // TODO: More Client communication to (below Client TODO) needs to be added


        writeToClient.close();
        readClient.close();
    }
}
