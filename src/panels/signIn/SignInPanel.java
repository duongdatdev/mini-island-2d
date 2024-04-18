package panels.signIn;

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
        SignInControl control = new SignInControl(new SignInModel(), panel);

        frame.setVisible(true);
    }
}
