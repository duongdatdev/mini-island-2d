package panels.auth.signUp;

import javax.swing.*;

public class SignUpPanel extends JPanel {
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel confirmPasswordLabel;
    private JPasswordField confirmPasswordField;
    private JButton signUpButton;
    private JButton backButton;

    public SignUpPanel() {
        usernameLabel = new JLabel("Username");
        usernameField = new JTextField();
        emailLabel = new JLabel("Email");
        emailField = new JTextField();
        passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField();
        confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordField = new JPasswordField();
        signUpButton = new JButton("Sign Up");
        backButton = new JButton("Back");
        init();
    }

    private void init() {
        this.setLayout(null);
        usernameLabel.setBounds(50, 100, 100, 30);
        usernameField.setBounds(150, 100, 200, 30);
        emailLabel.setBounds(50, 150, 100, 30);
        emailField.setBounds(150, 150, 200, 30);
        passwordLabel.setBounds(50, 200, 100, 30);
        passwordField.setBounds(150, 200, 200, 30);
        confirmPasswordLabel.setBounds(50, 250, 100, 30);
        confirmPasswordField.setBounds(150, 250, 200, 30);
        signUpButton.setBounds(150, 300, 200, 30);
        backButton.setBounds(150, 350, 200, 30);

        this.add(usernameLabel);
        this.add(usernameField);
        this.add(emailLabel);
        this.add(emailField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(confirmPasswordLabel);
        this.add(confirmPasswordField);
        this.add(signUpButton);
        this.add(backButton);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public void setEmailField(JTextField emailField) {
        this.emailField = emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public void setConfirmPasswordField(JPasswordField confirmPasswordField) {
        this.confirmPasswordField = confirmPasswordField;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public void setSignUpButton(JButton signUpButton) {
        this.signUpButton = signUpButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void setBackButton(JButton backButton) {
        this.backButton = backButton;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(new SignUpPanel());
        frame.setVisible(true);
    }
}
