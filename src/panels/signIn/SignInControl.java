package panels.signIn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInControl implements ActionListener {
    private SignInModel model;
    private SignInPanel view;
    public SignInControl(SignInModel model, SignInPanel view) {
        this.model = model;
        this.view = view;
    }
    private void setUsername(String username){
        model.setUsername(username);
    }
    private void setPassword(String password){
        model.setPassword(password);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getSignInButton()){
            setUsername(view.getUsernameField().getText());
            setPassword(view.getPasswordField().getText());
            model.signIn();
        }
        else if(e.getSource() == view.getSignUpButton()){
            setUsername(view.getUsernameField().getText());
            setPassword(view.getPasswordField().getText());
            model.signUp();
        }
    }
}
