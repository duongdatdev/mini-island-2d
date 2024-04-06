package main;

import javax.swing.*;
import java.awt.*;

public class MiniIsland extends JFrame {
    private final GamePanel gamePanel;

    public MiniIsland() {
        gamePanel = new GamePanel();

        gamePanel.start();
        this.add(gamePanel);
        init();
    }

    private void init() {
        this.setTitle("Mini Island");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
//        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
