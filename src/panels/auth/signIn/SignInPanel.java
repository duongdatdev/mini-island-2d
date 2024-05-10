package panels.auth.signIn;

import javax.swing.*;
import java.awt.*;

public class SignInPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JButton signUpButton;

    public SignInPanel() {
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        signInButton = new JButton("Sign In");
        signUpButton = new JButton("Sign Up");

        init(usernameLabel, passwordLabel);
    }

    private void init(JLabel usernameLabel, JLabel passwordLabel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(usernameLabel);
        this.add(usernameField);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(signInButton);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public JButton getSignInButton() {
        return signInButton;
    }

    public void setSignInButton(JButton signInButton) {
        this.signInButton = signInButton;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public void setSignUpButton(JButton signUpButton) {
        this.signUpButton = signUpButton;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sign In");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);

        SignInPanel panel = new SignInPanel();
//        SignInControl control = new SignInControl(SignInModel.getInstance(), panel);

        frame.setVisible(true);
    }
}
