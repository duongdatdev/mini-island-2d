package main;

import signIn.SignInPanel;

import javax.swing.*;
import java.awt.*;

public class MiniIsland extends JFrame {
    private final GamePanel gamePanel;
    private CardLayout cardLayout;
    private SignInPanel signInPanel;

    public MiniIsland() {
        gamePanel = new GamePanel();
        signInPanel = new SignInPanel();

        cardLayout = new CardLayout();

        init();
        gamePanel.start();
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
        this.add(gamePanel, "GamePanel");
        this.add(signInPanel, "SignInPanel");
        this.setVisible(true);

    }
}
