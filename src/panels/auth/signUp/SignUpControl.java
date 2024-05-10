package panels.auth.signUp;

public class SignUpControl {
    private SignUpModel model;
    private SignUpPanel view;

    public SignUpControl(SignUpModel model, SignUpPanel view) {
        this.model = model;
        this.view = view;
        view.getSignUpButton().addActionListener(e -> {
            model.setUsername(view.getUsernameField().getText());
            model.setEmail(view.getEmailField().getText());
            model.setPassword(new String(view.getPasswordField().getPassword()));
            model.setConfirmPassword(new String(view.getConfirmPasswordField().getPassword()));
            model.signUp();
        });
        view.getBackButton().addActionListener(e -> {
            view.setVisible(false);
            view.getParent().getComponent(0).setVisible(true);
        });
    }
}
