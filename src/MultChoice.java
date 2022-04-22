import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class MultChoice extends JFrame implements ActionListener {
    JButton button, buttonTwo, buttonThree, buttonFour, bton;
    JLabel label, title;


    MultChoice() {
        this.setSize(800, 850);
        this.setTitle("Take Quiz");
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        title = new JLabel ("Multiple Choice Question");
        title.setBounds (300,100,300,50);
        // can add various features
        //title setFont(new Font ("Times New Roman", Font.BOLD, 28));
        //title.setBorder(new EmptyBoarder(0,10,0,0));

        label = new JLabel ("Question 1: Do you believe that you are hot ");
        label.setBounds (300,100,360,50);
        //title setFont(new Font ("Times New Roman", Font.BOLD, 28));
        //label.setBorder(new EmptyBoarder(0,10,0,0));

        bton = new JButton ("Check Answer");
        bton.setBounds (250,420,250,50);


        button = new JButton("Option 1");
        button.setBounds (300,200,300,50);

        buttonTwo = new JButton("Option 2");
        buttonTwo.setBounds(300,250,300,50);

        buttonThree = new JButton ("Option 3");
        buttonThree.setBounds(300,300,300,50);

        buttonFour = new JButton ("Option 3");
        buttonFour.setBounds(300,350,300,50);

        ButtonGroup bGroup = new ButtonGroup();
        bGroup.add(button);
        bGroup.add(buttonTwo);
        bGroup.add(buttonThree);
        bGroup.add(buttonFour);

        this.add(button);
        this.add(buttonTwo);
        this.add(buttonThree);
        this.add(label);
        this.add(buttonFour);
        this.add(bton);

        this.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String message= "";
        if (button.isSelected()) {
            message += "incorrect";
            JOptionPane.showMessageDialog(this, "Your answer is incorrect");
        }
        if (buttonTwo.isSelected()) {
            message += "correct";
            JOptionPane.showMessageDialog(this, "Your answer is correct!");
        }
        if (buttonThree.isSelected()) {
            message += "incorrect";
            JOptionPane.showMessageDialog(this, "Your answer is incorrect");
        }
        if (buttonFour.isSelected()) {
            message += "incorrect";
            JOptionPane.showMessageDialog(this, "Your answer is incorrect");
        }

    }
}
