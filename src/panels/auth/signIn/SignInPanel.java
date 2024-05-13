package panels.auth.signIn;

import javax.swing.*;
import java.awt.*;

public class SignInPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JButton signUpButton;

    public SignInPanel() {
        JLabel usernameLabel = new JLabel();
        JLabel passwordLabel = new JLabel();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        signInButton = new JButton();
        signUpButton = new JButton();

        init(usernameLabel, passwordLabel);
    }

    private void init(JLabel usernameLabel, JLabel passwordLabel) {
        JLabel background = new JLabel();

        int x = 322;

        background.setIcon(new ImageIcon(SignInPanel.class.getResource("/Login/Login_background.png")));
        JPanel panel = new JPanel();

        panel.setOpaque(false);
        panel.setSize(1024, 768);
        panel.setLayout(null);

        panel.add(usernameLabel);

        usernameLabel.setIcon(new ImageIcon(SignInPanel.class.getResource("/Login/Username_Label.png")));
        passwordLabel.setIcon(new ImageIcon(SignInPanel.class.getResource("/Login/Password_Label.png")));

        JLabel usernameFieldTexture = new JLabel();
        usernameFieldTexture.setIcon(new ImageIcon(SignInPanel.class.getResource("/Login/Text_Field.png")));

        JLabel passwordFieldTexture = new JLabel();
        passwordFieldTexture.setIcon(new ImageIcon(SignInPanel.class.getResource("/Login/Text_Field.png")));


        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1024, 768));
        layeredPane.setBounds(0, 0, 1024, 768);
        // Add the texture first so it's at the bottom
        usernameFieldTexture.setBounds(x, 342, 402, 46);
        layeredPane.add(usernameFieldTexture, JLayeredPane.DEFAULT_LAYER);

        // Then add the text field so it's on top
        usernameField = new JTextField();
        usernameField.setBounds(x + 16, 350, 370, 30);
        usernameField.setMinimumSize(new Dimension(370, 30));
        usernameField.setPreferredSize(new Dimension(370, 30));
        usernameField.setForeground(Color.BLACK);
        usernameField.setCaretColor(Color.BLACK);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));
        usernameField.setBorder(BorderFactory.createEmptyBorder());
        layeredPane.add(usernameField, JLayeredPane.PALETTE_LAYER);

        // Add the texture first so it's at the bottom
        passwordFieldTexture = new JLabel();
        passwordFieldTexture.setIcon(new ImageIcon(SignInPanel.class.getResource("/Login/Text_Field.png")));
        passwordFieldTexture.setBounds(x, 417, 402, 46);
        layeredPane.add(passwordFieldTexture, JLayeredPane.DEFAULT_LAYER);

        // Then add the text field so it's on top
        passwordField = new JPasswordField();
        passwordField.setBounds(x + 16, 426, 370, 30);
        passwordField.setMinimumSize(new Dimension(370, 30));
        passwordField.setPreferredSize(new Dimension(370, 30));
        passwordField.setForeground(Color.BLACK);
        passwordField.setCaretColor(Color.BLACK);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        layeredPane.add(passwordField, JLayeredPane.PALETTE_LAYER);

        // Add the layered pane to the panel
        panel.add(layeredPane);

        panel.add(passwordFieldTexture);


        usernameFieldTexture.setBounds(x, 342, 402, 46);
        passwordFieldTexture.setBounds(x, 417, 402, 46);

        usernameLabel.setBounds(x, 310, 200, 31);
        passwordLabel.setBounds(x, 386, 200, 31);

        usernameField.setBounds(x + 16, 350, 370, 30);
        usernameField.setMinimumSize(new Dimension(370, 30));
        usernameField.setPreferredSize(new Dimension(370, 30));

        passwordField.setBounds(x + 16, 426, 370, 30);
        passwordField.setPreferredSize(new Dimension(370, 30));

        usernameField.setOpaque(false);

        usernameField.setForeground(Color.BLACK);
        usernameField.setCaretColor(Color.BLACK);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));
        usernameField.setVisible(true);

        usernameField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setOpaque(false);


        signInButton.setIcon(new ImageIcon(SignInPanel.class.getResource("/Login/Login_Button.png")));
        signInButton.setBounds(x, 482, 225, 52);
        signInButton.setBorder(null);
        signInButton.setContentAreaFilled(false);
        signInButton.setOpaque(false);
        signInButton.setFocusPainted(true);

        signUpButton.setIcon(new ImageIcon(SignInPanel.class.getResource("/Login/create_account.png")));
        signUpButton.setBounds(561, 492, 145, 42);
        signUpButton.setBorder(null);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setOpaque(false);
        signUpButton.setFocusPainted(false);

        panel.add(passwordLabel);
        panel.add(signInButton);
        panel.add(signUpButton);

        background.add(panel);

        this.add(background);

        this.validate();
    }

    public void loadAsset() {
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

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
