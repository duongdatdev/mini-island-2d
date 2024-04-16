package main;

import signIn.SignInPanel;

import javax.swing.*;
import java.awt.*;

public class MiniIsland extends JFrame {
    private final GameScene gameScene;
    private CardLayout cardLayout;
    private SignInPanel signInPanel;

    public MiniIsland() {
        gameScene = new GameScene(this);
        signInPanel = new SignInPanel();

        cardLayout = new CardLayout();

        init();
        gameScene.start();
        cardLayout.show(this.getContentPane(), "GamePanel");
//        cardLayout.show(this.getContentPane(), "SignInPanel");
        this.pack();
        this.setLocationRelativeTo(null);
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
