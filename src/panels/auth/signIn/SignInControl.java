package panels.auth.signIn;

import main.MiniIsland;
import network.client.Client;
import panels.auth.AuthHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class SignInControl implements ActionListener {

    //Fields
    private SignInModel model;
    private SignInPanel view;
    private MiniIsland miniIsland;

    //Network
    private Socket clientSocket;
    private BufferedWriter outToServer;
    private BufferedReader inFromServer;
    private AuthHandler authHandler;

    public SignInControl(MiniIsland miniIsland, SignInModel model, SignInPanel view) {
        this.miniIsland = miniIsland;
        this.model = model;
        this.view = view;

//        clientSocket = new Socket("localhost", 6788);
//        outToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

//        authHandler = new AuthHandler(Client.getGameClient().getSocket());
//        authHandler.start();

        view.getSignInButton().addActionListener(this);
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

            model.signIn(() -> {
                miniIsland.startGame();
                miniIsland.getGameScene().getPlayerMP().setUsername(username);
                miniIsland.changeToGamePanel();

                miniIsland.validate();
                miniIsland.repaint();
            });

            System.out.println("HHHHHHHHHHHHHH");

//            if (model.isSignedIn()) {
//                miniIsland.startGame();
//                miniIsland.getGameScene().getPlayerMP().setUsername(username);
//                miniIsland.changeToGamePanel();
//
//                System.out.println("HHH44444444444HHH");
//
//                miniIsland.validate();
//                miniIsland.repaint();
//            }
        } else if (e.getSource() == view.getSignUpButton()) {

            setUsername(view.getUsernameField().getText());
            setPassword(new String(view.getPasswordField().getPassword()));

            model.signUp();

        }
    }
}
