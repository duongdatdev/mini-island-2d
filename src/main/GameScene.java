package main;

import collision.Collision;
import font.CustomFont;
import maps.pvp.PvpMap;
import network.client.Client;
import network.client.Protocol;
import network.entitiesNet.PlayerMP;
import network.leaderBoard.LeaderBoard;
import objects.entities.NPC;
import objects.entities.Player;
import input.KeyHandler;
import maps.Map;
import panels.chat.ChatPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameScene extends JPanel implements Runnable {
    //Screen settings
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;
    private final int maxTilesX = 22;
    private final int maxTilesY = 16;
    private final int screenWidth = maxTilesX * tileSize;
    private final int screenHeight = maxTilesY * tileSize;

    //Map
    public String currentMap = "lobby";

    private PvpMap pvpMap;

    //NPC
    private NPC pvpNPC;
    CustomButton teleportButton;

    private NPC topNPC;
    CustomButton topButton;

    //FPS
    private final double FPS = 90.0;
    private int fps = 0;

    private Thread gameThread;

    private KeyHandler keyHandler;
    private Player player;
    private Map map;

    //Network
    private PlayerMP playerMP;
    private ArrayList<PlayerMP> players;

    //Leaderboard
    private LeaderBoard leaderBoard;

    //Game settings
    private boolean isRunning;

    //Handler for the collision
    private Collision collision;

    //Handler for the game scene

    //Chat
    private ChatPanel chatPanel;

    public GameScene(boolean isRunning) {
        keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler);


        //NPC
        try {
            pvpNPC = new NPC("PvP", 1000, 1000, ImageIO.read(getClass().getResource("/Maps/Pvp/PvpNPC.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            topNPC = new NPC("Top 20", 1693, 535, ImageIO.read(getClass().getResource("/NPC/top20NPC.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        collision = new Collision(this);

        this.isRunning = isRunning;

        player = new Player(this, keyHandler);

        playerMP = new PlayerMP(player);


        map = new Map(this);

        pvpMap = new PvpMap(this);

        players = map.players;

        repaint();

        init();

        //Chat
        chatPanel = new ChatPanel(this);

        chatPanel.setBackground(Color.WHITE);
        chatPanel.setPreferredSize(new Dimension(200, 100));

        CustomButton chatButton = new CustomButton("Chat");
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatPanel.setVisible(!chatPanel.isVisible());
                requestFocusInWindow();
            }
        });

        //Leaderboard

        leaderBoard = new LeaderBoard();

        topButton = new CustomButton("Top");
        topButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leaderBoard.setVisible(!leaderBoard.isVisible());
                requestFocusInWindow();
            }
        });

        teleportButton = new CustomButton("Teleport");

        teleportButton.setBounds(screenWidth - 100, 20, 100, 50);
        teleportButton.setVisible(false);
        teleportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (currentMap) {
                    case "lobby":
                        player.setWorldX(1000);
                        player.setWorldY(1000);
                        currentMap = "pvp";
                        map.removeAllPlayers();

                        Client.getGameClient().sendToServer(new Protocol().teleportPacket(playerMP.getUsername(), currentMap, player.getWorldX(), player.getWorldY()));
                        break;
                    case "pvp":
                        player.setWorldX(1000);
                        player.setWorldY(1000);
                        currentMap = "lobby";
                        pvpMap.removeAllPlayers();

                        Client.getGameClient().sendToServer(new Protocol().teleportPacket(playerMP.getUsername(), currentMap, player.getWorldX(), player.getWorldY()));
                        break;
                }
                requestFocusInWindow();
            }
        });
        teleportButton.setVisible(false);
        add(teleportButton);

        setLayout(null);

        chatPanel.setSize(400, 100);

        int centerXChat = (screenWidth - chatPanel.getWidth()) / 2;
        int centerYChat = (screenHeight - chatPanel.getHeight()) / 2;

        chatPanel.setLocation(centerXChat, screenHeight - 120);

        leaderBoard.setSize(300, 400);
        int centerXLeaderBoard = (screenWidth - leaderBoard.getWidth()) / 2;
        int centerYLeaderBoard = (screenHeight - leaderBoard.getHeight()) / 2;
        leaderBoard.setLocation(centerXLeaderBoard, centerYLeaderBoard);

        leaderBoard.setVisible(false);

        add(leaderBoard);

        chatButton.setBounds(screenWidth - 60, 20, 50, 50);

        topButton.setBounds((screenWidth - 100) / 2, screenHeight - 100, 100, 50);

        add(topButton);

        chatPanel.setVisible(false);

        add(chatPanel);

        add(chatButton);

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

        double drawInterval = 1000000000 / FPS;
        double delta = 0.0;
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

            if (delta >= 1.0) {
                updateAndRepaint();
//                drawCount++;
                delta--;
            }

            if (pvpNPC.isPlayerNear(player)) {
                teleportButton.setVisible(true);
            } else {
                teleportButton.setVisible(false);
            }

            if (topNPC.isPlayerNear(player)) {
                topButton.setVisible(true);
            } else {
                if (topButton.isVisible())
                    topButton.setVisible(false);
            }
//            if (timer >= 1000000000) {
//                fps = drawCount;
//                drawCount = 0;
//                timer = 0;
//                chatPanel.setChatImage(null);
//            }
//            if (timer >= 2000000000) {
//                chatPanel.setChatImage(null);
//            }
        }
    }

    private synchronized void updateAndRepaint() {
        // Update game logic
        update();

        // Repaint the scene
        repaint();
    }

    public void update() {
        player.update();
        playerMP.update();
    }

    public int drawChat = 0;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setFont(new Font("Arial", Font.BOLD, 20));
//        playerMP.render(g2d, tileSize);
        isRunning = true;

        if (isRunning) {
            switch (currentMap) {
                case "lobby":
                    map.draw(g2d, tileSize);
                    break;
                case "pvp":
                    pvpMap.draw(g2d, tileSize);
                    break;
            }

            pvpNPC.checkDraw(player, g2d, tileSize);
            topNPC.checkDraw(player, g2d, tileSize * 2);

            playerMP.render(g2d, tileSize);

        }
    }

    public void registerNewPlayer(PlayerMP newPlayer) {
        getMap().addPlayer(newPlayer);
    }

    public void teleportPlayer(String username, String map, int x, int y) {
        System.out.println(username);

        PlayerMP player = getMap().getPlayer(username);
        if (player != null) {
            player.setX(x);
            player.setY(y);
            player.getPlayer().setWorldX(x);
            player.getPlayer().setWorldY(y);

            if (!currentMap.equals(map)) {
                System.out.println("Teleporting player" + map);
                getMap().removePlayer(username);
            }
        }
    }

    public void removePlayer(String username) {
        map.removePlayer(username);
    }

    public PlayerMP getPlayer(String username) {
        for (PlayerMP mp : players) {
            if (mp != null && mp.getUsername().equals(username)) {
                return mp;
            }
        }
        return null;
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

    public Map getMap() {
        return switch (currentMap) {
            case "lobby" -> map;
            case "pvp" -> pvpMap;
            default -> map;
        };
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Collision getCollisionChecker() {
        return collision;
    }

    public void setCollisionChecker(Collision collision) {
        this.collision = collision;
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }

    public void setChatPanel(ChatPanel chatPanel) {
        this.chatPanel = chatPanel;
    }

    public LeaderBoard getLeaderBoard() {
        return leaderBoard;
    }

    public void setLeaderBoard(LeaderBoard leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }
}
