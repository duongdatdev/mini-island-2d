package panels.auth.signUp;

import javax.swing.*;
import java.net.URL;
import java.awt.*;

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

    //Texture
    private ImageIcon fieldTexture = new ImageIcon(SignUpPanel.class.getResource("/Register/Text_Field.png"));
    private ImageIcon usernameLabelTexture = new ImageIcon(SignUpPanel.class.getResource("/Register/Username_Label.png"));
    private ImageIcon emailLabelTexture = new ImageIcon(SignUpPanel.class.getResource("/Register/Email.png"));
    private ImageIcon passwordLabelTexture = new ImageIcon(SignUpPanel.class.getResource("/Register/Password_Label.png"));
    private ImageIcon confirmPasswordLabelTexture = new ImageIcon(SignUpPanel.class.getResource("/Register/Confirm_Password.png"));

    //Set Texture


    public SignUpPanel() {
        usernameLabel = new JLabel();
        usernameField = new JTextField();
        emailLabel = new JLabel();
        emailField = new JTextField();
        passwordLabel = new JLabel();
        passwordField = new JPasswordField();
        confirmPasswordLabel = new JLabel();
        confirmPasswordField = new JPasswordField();
        signUpButton = new JButton();
        backButton = new JButton();
        init();
    }

    private void init() {
        int x = 322;

        JLabel background = new JLabel();
        background.setIcon(new ImageIcon(SignUpPanel.class.getResource("/Register/Register_Background.png")));

        JPanel panel = new JPanel();

        JLabel usernameFieldTexture = new JLabel();
        usernameFieldTexture.setIcon(fieldTexture);
        JLabel emailFieldTexture = new JLabel();
        emailFieldTexture.setIcon(fieldTexture);
        JLabel passwordFieldTexture = new JLabel();
        passwordFieldTexture.setIcon(fieldTexture);
        JLabel confirmPasswordFieldTexture = new JLabel();
        confirmPasswordFieldTexture.setIcon(fieldTexture);

        usernameLabel.setIcon(usernameLabelTexture);
        emailLabel.setIcon(emailLabelTexture);
        passwordLabel.setIcon(passwordLabelTexture);
        confirmPasswordLabel.setIcon(confirmPasswordLabelTexture);

        signUpButton.setIcon(new ImageIcon(SignUpPanel.class.getResource("/Register/Register.png")));
        backButton.setIcon(new ImageIcon(SignUpPanel.class.getResource("/Register/already account click here !!.png")));

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1024, 768));
        layeredPane.setBounds(0, 0, 1024, 768);

        usernameLabel.setBounds(x, 310, 201, 32);
        layeredPane.add(usernameLabel, JLayeredPane.DEFAULT_LAYER);

        emailLabel.setBounds(x, 384, 201, 32);
        layeredPane.add(emailLabel, JLayeredPane.DEFAULT_LAYER);

        passwordLabel.setBounds(x, 460, 201, 32);
        layeredPane.add(passwordLabel, JLayeredPane.DEFAULT_LAYER);

        confirmPasswordLabel.setBounds(x, 536, 201, 32);
        layeredPane.add(confirmPasswordLabel, JLayeredPane.DEFAULT_LAYER);

        usernameFieldTexture.setBounds(x, 342, 402, 38);
        layeredPane.add(usernameFieldTexture, JLayeredPane.DEFAULT_LAYER);

        emailFieldTexture.setBounds(x, 416, 402, 38);
        layeredPane.add(emailFieldTexture, JLayeredPane.DEFAULT_LAYER);

        passwordFieldTexture.setBounds(x, 492, 402, 38);
        layeredPane.add(passwordFieldTexture, JLayeredPane.DEFAULT_LAYER);

        confirmPasswordFieldTexture.setBounds(x, 568, 402, 38);
        layeredPane.add(confirmPasswordFieldTexture, JLayeredPane.DEFAULT_LAYER);

        usernameField.setBounds(x + 16, 350 - 5, 370, 30);

        emailField.setBounds(x + 16, 424 - 5, 370, 30);

        passwordField.setBounds(x + 16, 500 - 5, 370, 30);

        confirmPasswordField.setBounds(x + 16, 576 - 5, 370, 30);

        signUpButton.setBounds(328, 631, 184, 47);

        backButton.setBounds(559, 636, 145, 42);

        layeredPane.add(usernameField, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(emailField, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(passwordField, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(confirmPasswordField, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(signUpButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(backButton, JLayeredPane.PALETTE_LAYER);

        setFieldNoColor(usernameField);
        setFieldNoColor(emailField);
        setFieldNoColor(passwordField);
        setFieldNoColor(confirmPasswordField);

        setButtonNoColor(signUpButton);
        setButtonNoColor(backButton);

        panel.add(layeredPane);

        panel.setOpaque(false);
        panel.setSize(1024, 768);

        background.add(panel);

        this.add(background);
        this.setSize(1024, 768);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    private void setFieldNoColor(JTextField field) {
        field.setForeground(Color.BLACK);
        field.setCaretColor(Color.BLACK);
        field.setFont(new Font("Arial", Font.PLAIN, 20));
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setOpaque(false);
    }

    private void setButtonNoColor(JButton button) {
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
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
        frame.add(new SignUpPanel());
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
