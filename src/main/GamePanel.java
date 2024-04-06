package main;

import entities.Player;
import input.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //Screen settings
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;
    private final int maxTilesX = 16;
    private final int maxTilesY = 16;
    private final int screenWidth = maxTilesX * tileSize;
    private final int screenHeight = maxTilesY * tileSize;

    //Player settings
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
    Player player;

    public GamePanel() {
        keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler);

        player = new Player(keyHandler);

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

    public synchronized void start() {
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
        player.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        player.draw(g2d, tileSize);
        g2d.setColor(Color.RED);
        g2d.drawString("FPS: " + fps, 10, 20);
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getTileSize() {
        return tileSize;
    }
}
