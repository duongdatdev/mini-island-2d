package main;

import network.client.Client;
import network.client.ClientRecivingThread;
import network.client.Protocol;
import network.entitiesNet.PlayerMP;
import panels.signIn.SignInControl;
import panels.signIn.SignInModel;
import panels.signIn.SignInPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class MiniIsland extends JFrame {
    private final GameScene gameScene;
    private CardLayout cardLayout;
    private SignInPanel signInPanel;
    private SignInControl signInControl;
    private SignInModel signInModel;

    private Client client;
    private PlayerMP clientPlayer;

    public MiniIsland() {
        gameScene = new GameScene(true);
        signInPanel = new SignInPanel();

        signInModel = SignInModel.getInstance();

        signInControl = new SignInControl(this, signInModel, signInPanel);

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
        actionRegister();
        changeToGamePanel();
        gameScene.start();
        gameScene.setFocusable(true);
        gameScene.requestFocusInWindow();
        clientPlayer = gameScene.getPlayerMP();
        new ClientRecivingThread(client.getSocket(), clientPlayer, gameScene).start();
    }

    public void changePanel(String panelName) {
        cardLayout.show(this.getContentPane(), panelName);
    }
    public void changeToGamePanel() {
        cardLayout.show(this.getContentPane(), "GamePanel");
    }
    private void showDialogExit() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit ?", "2D Multiplayer Game!", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            Client.getGameClient().sendToServer(new Protocol().ExitMessagePacket(clientPlayer.getID()));
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
        this.add(gameScene, "GamePanel");
        this.add(signInPanel, "SignInPanel");
        this.setVisible(true);

    }
}
