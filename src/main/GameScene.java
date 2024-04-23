package main;

import network.entitiesNet.PlayerMP;
import objects.entities.Player;
import input.KeyHandler;
import maps.Map;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScene extends JPanel implements Runnable {
    //Screen settings
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;
    private final int maxTilesX = 22;
    private final int maxTilesY = 16;
    private final int screenWidth = maxTilesX * tileSize;
    private final int screenHeight = maxTilesY * tileSize;

    //FPS
    private final int FPS = 60;
    private int fps = 0;
    private int frameCount = 0;
    private long startTime = System.nanoTime();

    private Thread gameThread;

    private KeyHandler keyHandler;
    private Player player;
    private Map map;

    //Network
    private PlayerMP playerMP;
    private static ArrayList<PlayerMP> players;

    //Game settings
    private boolean isRunning;

    public GameScene(boolean isRunning){
        keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler);

        this.isRunning = isRunning;

        player = new Player(this, keyHandler);

        playerMP = new PlayerMP(player);

        players = new ArrayList<PlayerMP>();

        map = new Map(this);

        for (int i = 0; i < 100; i++) {
            players.add(null);
        }
        repaint();

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
        long timer = 0;
        int drawCount = 0;
        System.out.println("Game started");
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
        playerMP.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        map.draw(g2d, tileSize);
        playerMP.render(g2d, tileSize);
        isRunning = true;

        if(isRunning) {

            g2d.drawString("FPS: " + fps, 10, 20);

            player.render(g2d, tileSize);

            int i = 1;
            while (i < players.size()) {
                if (players.get(i) != null) {
                    int worldX = players.get(i).getX();
                    int worldY = players.get(i).getY();

                    int screenX = worldX - player.getWorldX() + player.getScreenX();
                    int screenY = worldY - player.getWorldY() + player.getScreenY();

                    if (worldX > player.getWorldX() - player.getScreenX() - tileSize*2
                            && worldX < player.getWorldX() + player.getScreenX() + tileSize*2
                            && worldY > player.getWorldY() - player.getScreenY() - tileSize*2
                            && worldY < player.getWorldY() + player.getScreenY()+ tileSize*2) {
                        g2d.drawImage(players.get(i).getPlayer().getStandingImage(), screenX, screenY, tileSize, tileSize, null);
                        g2d.drawString("Player " + players.get(i).getID(), screenX, screenY - 10);
                    }
                }
                i++;
            }

        }
    }

    public void registerNewPlayer(PlayerMP newPlayer)
    {
        players.set(newPlayer.getID(), newPlayer);
    }
    public void removePlayer(int PlayerID)
    {
        players.set(PlayerID,null);
    }
    public PlayerMP getPlayer(int id)
    {
        return players.get(id);
    }


    //Getters and Setters
    public int getMaxTilesX() {
        return maxTilesX;
    }

    public int getMaxTilesY() {
        return maxTilesY;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getTileSize() {
        return tileSize;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerMP getPlayerMP() {
        return playerMP;
    }

    public void setPlayerMP(PlayerMP playerMP) {
        this.playerMP = playerMP;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
