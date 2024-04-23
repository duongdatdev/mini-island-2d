package panels.signIn;

import main.MiniIsland;
import network.client.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInControl implements ActionListener {
    private SignInModel model;
    private SignInPanel view;
    private MiniIsland miniIsland;
    public SignInControl(MiniIsland miniIsland, SignInModel model, SignInPanel view) {
        this.miniIsland = miniIsland;
        this.model = model;
        this.view = view;
        view.getSignInButton().addActionListener(this);
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
            setPassword(new String(view.getPasswordField().getPassword()));
            model.signIn();
            miniIsland.startGame();
            if (model.isSignedIn()) {
//                miniIsland.changePanel("GameScene");
                miniIsland.changeToGamePanel();
                miniIsland.validate();
                miniIsland.repaint();
            }
        }
        else if(e.getSource() == view.getSignUpButton()){
            setUsername(view.getUsernameField().getText());
            setPassword(new String(view.getPasswordField().getPassword()));
            model.signUp();
        }
    }
}
