import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Objects;

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

public final class LoginPage {

    private JFrame frame;
    private final JPanel panel;
    private final JLabel welcomeLabel;
    private final JTextField usernameTextField;
    private final JTextField passwordTextField;
    private final JButton signIn;
    private final JButton signUp;
    private final JButton changePassword;

    private LoginPage() {
        GridBagLayout layout = new GridBagLayout();

        this.panel = new JPanel(layout);

        this.welcomeLabel = new JLabel("Welcome");

        this.usernameTextField = new JTextField();
        this.passwordTextField = new JTextField();

        this.signIn = new JButton("Sign In");
        this.signUp = new JButton("Sign Up");
        this.changePassword = new JButton("Change Password");
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public JTextField getUsernameTextField() {
        return this.usernameTextField;
    }

    public JTextField getPasswordTextField() {
        return this.passwordTextField;
    }

    public JButton getSignIn() {
        return this.signIn;
    }

    public JButton getSignUp() {
        return this.signUp;
    }

    public JButton getChangePassword() {
        return this.changePassword;
    }

    public static LoginPage create() {
        LoginPage login;
        TitledBorder usernameBorder;
        TitledBorder passwordBorder;
        GridBagConstraints constraints;
        GridBagLayout layout;

        login = new LoginPage();

        login.frame = new JFrame("Login Page");
        layout = new GridBagLayout();
        login.frame.setLayout(layout);

        Objects.requireNonNull(login, "the specified view is null");

        usernameBorder = BorderFactory.createTitledBorder("Username");
        passwordBorder = BorderFactory.createTitledBorder("Password");

        login.usernameTextField.setBorder(usernameBorder);
        login.passwordTextField.setBorder(passwordBorder);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;

        login.panel.add(login.welcomeLabel, constraints);

        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridy = 1;
        login.panel.add(login.usernameTextField, constraints);
        constraints.gridy = 2;
        login.panel.add(login.passwordTextField, constraints);

        constraints.gridy = 3;
        login.panel.add(login.signIn, constraints);
        constraints.gridy = 4;
        login.panel.add(login.signUp, constraints);
        constraints.gridy = 5;
        login.panel.add(login.changePassword, constraints);

        constraints.fill = GridBagConstraints.BOTH;

        login.frame.add(login.panel, constraints);
        login.frame.pack();
        login.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.frame.setLocationRelativeTo(null);
        login.frame.setMinimumSize(new Dimension(300, 250));

        return login;
    }
}







