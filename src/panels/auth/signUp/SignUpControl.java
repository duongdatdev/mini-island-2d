package panels.auth.signUp;

import main.MiniIsland;

import javax.swing.*;

public class SignUpControl {
    private SignUpModel model;
    private SignUpPanel view;
    private MiniIsland miniIsland;

    public SignUpControl(MiniIsland miniIsland, SignUpModel model, SignUpPanel view) {
        this.model = model;
        this.view = view;
        this.miniIsland = miniIsland;
        view.getSignUpButton().addActionListener(e -> {
            model.setUsername(view.getUsernameField().getText());
            model.setEmail(view.getEmailField().getText());
            model.setPassword(new String(view.getPasswordField().getPassword()));
            model.setConfirmPassword(new String(view.getConfirmPasswordField().getPassword()));
            model.signUp( () ->{
                JOptionPane.showMessageDialog(null, "Account created successfully");
                miniIsland.changePanel("SignInPanel");
            });
        });
        view.getBackButton().addActionListener(e -> {
            miniIsland.changePanel("SignInPanel");
        });
    }
}
