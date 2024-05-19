package main;

import network.client.Client;
import network.client.ClientRecivingThread;
import network.client.Protocol;
import network.entitiesNet.PlayerMP;
import panels.auth.signIn.SignInControl;
import panels.auth.signIn.SignInModel;
import panels.auth.signIn.SignInPanel;
import panels.auth.signUp.SignUpControl;
import panels.auth.signUp.SignUpModel;
import panels.auth.signUp.SignUpPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MiniIsland extends JFrame {
    private GameScene gameScene;
    private CardLayout cardLayout;

    //Network
    Socket socket = null;
    DataOutputStream writer = null;

    //Login
    private SignInPanel signInPanel;
    private SignInControl signInControl;
    private SignInModel signInModel;

    //Register
    private SignUpModel signUpModel;
    private SignUpPanel signUpPanel;
    private SignUpControl signUpControl;

    private Client client;
    private PlayerMP clientPlayer;

    public MiniIsland() {
        signInPanel = new SignInPanel();
        signInModel = SignInModel.getInstance();
        signInControl = new SignInControl(this, signInModel, signInPanel);

        signUpPanel = new SignUpPanel();
        signUpModel = new SignUpModel();

        signUpControl = new SignUpControl(this, signUpModel, signUpPanel);

        client = Client.getGameClient();

        cardLayout = new CardLayout();

        init();

        changePanel("SignInPanel");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showDialogExit();
            }
        });
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void startGame() {

        gameScene = new GameScene(true);
        this.add(gameScene, "GamePanel");

        actionRegister();

        gameScene.start();
        gameScene.setFocusable(true);
        gameScene.requestFocusInWindow();

        client = Client.getGameClient();
        socket = client.getSocket();
        writer = client.getWriter();

        clientPlayer = gameScene.getPlayerMP();
        ClientRecivingThread clientRecivingThread = new ClientRecivingThread(socket, clientPlayer, gameScene);
        clientRecivingThread.start();

        try {
            client.register(new Protocol().HelloPacket(signInModel.getUsername()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clientPlayer.setWriter(writer);

        changeToGamePanel();
    }

    private void sendToServer(String message) {
        if (message.equals("exit")) {
            System.exit(0);
        } else {
            try {
                writer.writeUTF(message);
            } catch (IOException ex) {
            }
        }
    }


    public void changePanel(String panelName) {
        cardLayout.show(this.getContentPane(), panelName);
    }

    public void changeToGamePanel() {
        cardLayout.show(this.getContentPane(), "GamePanel");
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void showDialogExit() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit ?", "2D Multiplayer Game!", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            if (clientPlayer != null) {
                Client.getGameClient().sendToServer(new Protocol().ExitMessagePacket(signInModel.getUsername()));
            }
            System.exit(0);
        } else {
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }

    public void actionRegister() {
        gameScene.setRunning(true);

        gameScene.start();
        gameScene.validate();
        gameScene.repaint();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        gameScene.setFocusable(true);
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    private void init() {
        this.setTitle("Mini Island");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
//        this.setResizable(false);
        this.setLayout(cardLayout);

        this.add(signInPanel, "SignInPanel");
        this.add(signUpPanel, "SignUpPanel");
        this.setVisible(true);

    }

    public DataOutputStream getWriter() {
        return writer;
    }

    public void setWriter(DataOutputStream writer) {
        this.writer = writer;
    }
}
