package main;

import collision.Collision;
import maps.MazeMap;
import maps.PvpMap;
import network.client.Client;
import network.client.Protocol;
import network.entitiesNet.PlayerMP;
import network.leaderBoard.LeaderBoard;
import objects.entities.Player;
import input.KeyHandler;
import maps.Map;
import panes.chat.ChatPane;
import panes.loading.LoadingPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * GameScene class is the main class that handles the game logic and rendering of the game.
 * It extends JPanel and implements Runnable.
 */
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
    private Map map;
    private MazeMap mazeMap;

    //Player
    private boolean isPlayerAlive = true;

    //Scene
    private final JPanel loadingPanel;

    //NPC
    CustomButton teleportButtonPvpNPC;
    CustomButton topButton;
    CustomButton teleportButtonMazeNPC;

    private int fps = 0;

    private Thread gameThread;

    private KeyHandler keyHandler;
    private Player player;

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
    private ChatPane chatPanel;

    private static GameScene instance;

    public static GameScene getInstance() {
        if (instance == null) {
            instance = new GameScene(true);
        }
        return instance;
    }

    public GameScene(boolean isRunning) {
        keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler);

        collision = new Collision(this);

        this.isRunning = isRunning;

        player = new Player(this, keyHandler);

        playerMP = new PlayerMP(player);

        //Maps
        map = new Map(this);
        pvpMap = new PvpMap(this);
        mazeMap = new MazeMap(this);

        //Loading
        loadingPanel = new LoadingPane();

        players = map.players;

        repaint();

        init();

        //Chat
        chatPanel = new ChatPane(this);

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

        teleportButtonPvpNPC = new CustomButton("Teleport");

        teleportButtonPvpNPC.setBounds((screenWidth + 100) / 2, screenHeight - 100, 100, 50);
        teleportButtonPvpNPC.setVisible(false);
        teleportButtonPvpNPC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (currentMap) {
                    case "lobby":
                        player.setWorldX(1000);
                        player.setWorldY(1000);
                        currentMap = "pvp";
                        map.removeAllPlayers();

                        playerMP.setAlive(true);
                        sendRespawnPacket();
                        Client.getGameClient().sendToServer(new Protocol().teleportPacket(playerMP.getUsername(), currentMap, player.getWorldX(), player.getWorldY()));
                        break;
                    case "pvp":
                        player.setDefaultPosition();
                        isPlayerAlive = true;
                        currentMap = "lobby";
                        pvpMap.removeAllPlayers();

                        Client.getGameClient().sendToServer(new Protocol().teleportPacket(playerMP.getUsername(), currentMap, player.getWorldX(), player.getWorldY()));
                        break;
                }
                requestFocusInWindow();
            }
        });

        teleportButtonMazeNPC = new CustomButton("Teleport");

        teleportButtonPvpNPC.setVisible(true);

        teleportButtonMazeNPC.setBounds((screenWidth + 100) / 2, screenHeight - 100, 100, 50);
        teleportButtonMazeNPC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (currentMap) {
                    case "lobby":
                        changeToLoadingScene();
                        currentMap = "loading";

                        Client.getGameClient().sendToServer(new Protocol().enterMazePacket(playerMP.getUsername()));
                        break;
                    case "maze":
                        player.setWorldX(1693);
                        player.setWorldY(535);
                        currentMap = "lobby";

                        map.getMazeNPC().setWorldX(2092);
                        map.getMazeNPC().setWorldY(1075);
                        mazeMap.removeAllPlayers();

                        Client.getGameClient().sendToServer(new Protocol().teleportPacket(playerMP.getUsername(), currentMap, player.getWorldX(), player.getWorldY()));
                        break;
                }
                requestFocusInWindow();
            }
        });

        add(teleportButtonPvpNPC);

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

        add(teleportButtonMazeNPC);

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

        //FPS
        double FPS = 90.0;
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

//            timer += currentTime - lastTime;

            lastTime = currentTime;

            if (delta >= 1.0) {
                updateAndRepaint();
//                drawCount++;
                delta--;
            }
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

        if (map.getPvpNPC().isPlayerNear(player)) {
            teleportButtonPvpNPC.setVisible(true);
        } else {
            teleportButtonPvpNPC.setVisible(false);
        }

        if (map.getTopNPC().isPlayerNear(player)) {
            topButton.setVisible(true);
        } else {
            if (topButton.isVisible()) {
                topButton.setVisible(false);
            }
            if (leaderBoard.isVisible()) {
                leaderBoard.setVisible(false);
            }
        }

        if (map.getMazeNPC().isPlayerNear(player)) {
            teleportButtonMazeNPC.setVisible(true);
        } else {
            teleportButtonMazeNPC.setVisible(false);
        }
    }

    public int drawChat = 0;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        isRunning = true;

        if (isRunning) {
            switch (currentMap) {
                case "lobby":
                    map.draw(g2d, tileSize);
                    break;
                case "pvp":
                    pvpMap.draw(g2d, tileSize);
                    break;
                case "maze":
                    mazeMap.draw(g2d, tileSize);
            }

            playerMP.render(g2d, tileSize);

            // Render the bullet
            for (int j = 0; j < 1000; j++) {
                if (playerMP.getBullet()[j] != null) {
                    if (!playerMP.getBullet()[j].stop) {
                        int bombScreenX = playerMP.getBullet()[j].getPosiX() - player.getWorldX() + player.getScreenX();
                        int bombScreenY = playerMP.getBullet()[j].getPosiY() - player.getWorldY() + player.getScreenY();
                        g2d.drawImage(playerMP.getBullet()[j].getBulletImg(), bombScreenX, bombScreenY, 10, 10, this);
                    }
                }
            }

            //Render the bullet all players

            if (currentMap.equals("pvp")) {
                for (PlayerMP playerMP : getMap().players) {
                    for (int i = 0; i < 1000; i++) {
                        if (playerMP.getBullet()[i] != null) {
                            if (!playerMP.getBullet()[i].stop) {
                                int bulletScreenX = playerMP.getBullet()[i].getPosiX() - player.getWorldX() + player.getScreenX();
                                int bulletScreenY = playerMP.getBullet()[i].getPosiY() - player.getWorldY() + player.getScreenY();

                                g2d.drawImage(playerMP.getBullet()[i].getBulletImg(), bulletScreenX, bulletScreenY, 10, 10, this);
                            }
                        }
                    }
                }
            }

        }
    }

    public void sendTeleportPacket(String username, String map, int x, int y) {
        Client.getGameClient().sendToServer(new Protocol().teleportPacket(username, map, x, y));
    }

    public void changeToLoadingScene() {
        loadingPanel.setSize(screenWidth, screenHeight);
        add(loadingPanel);

    }

    public void changeToMazeMap() {
        remove(loadingPanel);
        currentMap = "maze";
    }

    public void changeToLobby(Map map) {
        player.setDefaultPosition();

        currentMap = "lobby";

        map.removeAllPlayers();
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

    public void winMaze() {
        changeToLobby(mazeMap);

        Client.getGameClient().sendToServer(new Protocol().winMazePacket(playerMP.getUsername()));
    }

    public void sendRespawnPacket() {
        Client.getGameClient().sendToServer(new Protocol().respawnPacket(playerMP.getUsername()));
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
            case "pvp" -> pvpMap;
            case "maze" -> mazeMap;
            default -> map;
        };
    }

    public Map getLobbyMap() {
        return map;
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

    public ChatPane getChatPanel() {
        return chatPanel;
    }

    public void setChatPanel(ChatPane chatPanel) {
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

    public ArrayList<PlayerMP> getPlayers() {
        return pvpMap.players;
    }

    public MazeMap getMazeMap() {
        return mazeMap;
    }

    public void setMazeMap(MazeMap mazeMap) {
        this.mazeMap = mazeMap;
    }

    public PvpMap getPvpMap() {
        return pvpMap;
    }

    public void setPvpMap(PvpMap pvpMap) {
        this.pvpMap = pvpMap;
    }

    public boolean isPlayerAlive() {
        return isPlayerAlive;
    }

    public void setPlayerAlive(boolean isPlayerAlive) {
        this.isPlayerAlive = isPlayerAlive;
    }
}
