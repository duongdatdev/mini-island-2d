package panes.auth.signIn;

import main.MiniIsland;
import panes.auth.AuthHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class SignInControl implements ActionListener {

    //Fields
    private SignInModel model;
    private SignInPane view;
    private MiniIsland miniIsland;

    //Network
    private Socket clientSocket;
    private BufferedWriter outToServer;
    private BufferedReader inFromServer;
    private AuthHandler authHandler;

    public SignInControl(MiniIsland miniIsland, SignInModel model, SignInPane view) {
        this.miniIsland = miniIsland;
        this.model = model;
        this.view = view;

        view.getSignInButton().addActionListener(this);
        view.getSignUpButton().addActionListener(this);
    }

    private void setUsername(String username) {
        model.setUsername(username);
    }

    private void setPassword(String password) {
        model.setPassword(password);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getSignInButton()) {

            String username = view.getUsernameField().getText();
            String password = new String(view.getPasswordField().getPassword());

            setUsername(username);
            setPassword(password);

            model.signIn(username,password,() -> {
                if (model.isSignedIn()) {
                    miniIsland.startGame();
                    miniIsland.getGameScene().getPlayerMP().setUsername(username);

                    miniIsland.changeToGamePanel();

                    miniIsland.validate();
                    miniIsland.repaint();
                }
            });

        } else if (e.getSource() == view.getSignUpButton()) {
            miniIsland.changePanel("SignUpPanel");
        }
    }
}
