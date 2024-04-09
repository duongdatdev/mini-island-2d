package signIn;

import javax.swing.*;

public class SignInPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JButton signUpButton;

    public SignInPanel() {
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        signInButton = new JButton("Sign In");
        signUpButton = new JButton("Sign Up");

        init();
    }

    private void init() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(usernameField);
        this.add(passwordField);
        this.add(signInButton);
        this.add(signUpButton);

    }
}
