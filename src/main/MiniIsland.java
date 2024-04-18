package main;

import network.client.Client;
import network.client.ClientRecivingThread;
import network.entitiesNet.PlayerMP;
import panels.signIn.SignInPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MiniIsland extends JFrame {
    private final GameScene gameScene;
    private CardLayout cardLayout;
    private SignInPanel signInPanel;

    private Client client;
    private PlayerMP clientPlayer;

    public MiniIsland() {
        gameScene = new GameScene(true);
        signInPanel = new SignInPanel();

        client = Client.getGameClient();
        clientPlayer = gameScene.getPlayerMP();
        cardLayout = new CardLayout();

        actionRegister();

        init();
        gameScene.start();
        cardLayout.show(this.getContentPane(), "GamePanel");
//        cardLayout.show(this.getContentPane(), "SignInPanel");
        this.pack();
        this.setLocationRelativeTo(null);
    }
    public void actionRegister() {
        try {
            client.register(clientPlayer.getX(), clientPlayer.getY());
            gameScene.setRunning(true);

            gameScene.start();
            gameScene.validate();
            gameScene.repaint();
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
            new ClientRecivingThread(client.getSocket(), clientPlayer, gameScene).start();
            gameScene.setFocusable(true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "The Server is not running, try again later!", "Players 2D Multiplayer Game", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("The Server is not running!");
        }
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
