package main;

import input.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;

    private final int maxTilesX = 16;
    private final int maxTilesY = 16;
    private final int screenWidth = maxTilesX * tileSize;
    private final int screenHeight = maxTilesY * tileSize;
    private int playerX = 0;
    private int playerY = 0;
    private int playerSpeed = 5;
    //FPS
    private final int FPS = 60;
    private int fps = 0;
    private int frameCount = 0;
    private long startTime = System.nanoTime();

    Thread gameThread;

    KeyHandler keyHandler;

    public GamePanel() {
        keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler);

        init();
    }

    private void init() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setFocusable(true);
        this.requestFocus();
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / 60.0;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer=0;
        int drawCount = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                drawCount++;
                delta--;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                fps = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (keyHandler.isUp()) {
            playerY -= playerSpeed;
        }
        if (keyHandler.isDown()) {
            playerY += playerSpeed;
        }
        if (keyHandler.isLeft()) {
            playerX -= playerSpeed;
        }
        if (keyHandler.isRight()) {
            playerX += playerSpeed;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(playerX, playerY, tileSize, tileSize);
        g2d.setColor(Color.RED);
        g2d.drawString("FPS: " + fps, 10, 20);
    }
}
