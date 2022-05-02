import javax.swing.*;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * LoginPage.java
 *
 * This class acts as the GUI for the login display.
 * includes fields for entering username and password as well
 * as a couple of buttons to accomplish different tasks.
 * @author Shruti Shah
 * @version 4/20
 *
 */

// implement to OptionList, login GUI created by shruti
public class LoginPage implements ActionListener {
    // instantiating objects
    static JFrame frame = new JFrame();
    static JPanel panel = new JPanel();
    static JButton button = new JButton("Login");
    static JButton reset = new JButton("Reset");
    static JTextField userText = new JTextField(20);
    static JPasswordField passwordInput = new JPasswordField();
    static JLabel valid = new JLabel("");
    static JLabel label = new JLabel("Username");



    /*
    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel userIDLabel = new JLabel("userID:");
    JLabel userPasswordLabel = new JLabel("password:");
    JLabel messageLabel = new JLabel();


     */


    public static void main (String[] args) {
        //hasmaps store keys and passwords


        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        frame.add(panel);

        panel.setLayout(null);

        label.setBounds(10, 20, 165, 25);
        panel.add(label);

        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordText = new JLabel ("Password");
        passwordText.setBounds(10, 50, 80, 25);
        panel.add(passwordText);

        passwordInput.setBounds (100, 50, 165, 25);
        panel.add(passwordInput);

        button.setBounds(10, 80, 80, 25);
        panel.add(button);
        button.addActionListener(new LoginPage());


        reset.setBounds(80, 80, 80, 25);
        panel.add(reset);


        valid.setBounds(10, 110, 300, 25);
        panel.add(valid);
        //valid.setText();

        /*JLabel valid = new JLabel("");
        valid.setBounds(10, 110, 300, 25);
        panel.add(valid);

         */



        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String password = passwordInput.getText();
        //String reSet = reset.getText();
        System.out.println(user + "," + password);

        if (user.equals(userName) && password.equals(password)) {
            valid.setText("Login Successful!");
        } else {
            valid.setText("Login Invalid, please try again!");
        }

    }
}



//password = password.getText();
        /*
        if (userText.equals("") && passwordInput.equals("")) {
            //username.getText();
            JOptionPane.showMessageDialog(null, "Login Successful");
        }
        else
            JOptionPane.showMessageDialog(null, "Login is not successful");

         */











