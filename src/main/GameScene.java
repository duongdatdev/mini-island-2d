package main;

import collision.Collision;
import maps.maze.MazeMap;
import maps.pvp.PvpMap;
import network.client.Client;
import network.client.Protocol;
import network.entitiesNet.PlayerMP;
import network.leaderBoard.LeaderBoard;
import objects.entities.Player;
import input.KeyHandler;
import maps.Map;
import panels.chat.ChatPanel;
import panels.loading.LoadingPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    //Map
    public String currentMap = "lobby";
    private PvpMap pvpMap;
    private Map map;
    private MazeMap mazeMap;

    //Scene
    private JPanel loadingPanel;

    //NPC
    CustomButton teleportButtonPvpNPC;
    CustomButton topButton;
    CustomButton teleportButtonMazeNPC;

    //FPS
    private final double FPS = 90.0;
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
    private ChatPanel chatPanel;

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


        //NPC
//        try {
//            pvpNPC = new NPC("PvP", 1000, 1000, ImageIO.read(getClass().getResource("/Maps/Pvp/PvpNPC.png")));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            topNPC = new NPC("Top 20", 1693, 535, ImageIO.read(getClass().getResource("/NPC/top20NPC.png")));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        collision = new Collision(this);

        this.isRunning = isRunning;

        player = new Player(this, keyHandler);

        playerMP = new PlayerMP(player);

        //Maps
        map = new Map(this);
        pvpMap = new PvpMap(this);
        mazeMap = new MazeMap(this);

        //Loading
        loadingPanel = new LoadingPanel();

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
        requestFocusInWindow();
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

//            timer += currentTime - lastTime;

            lastTime = currentTime;

            if (delta >= 1.0) {
                updateAndRepaint();
//                drawCount++;
                delta--;
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
                case "maze":
                    mazeMap.draw(g2d, tileSize);
            }

            playerMP.render(g2d, tileSize);

            int worldX = playerMP.getX();
            int worldY = playerMP.getY();

            int screenX = worldX - getPlayer().getWorldX() + getPlayer().getScreenX();
            int screenY = worldY - getPlayer().getWorldY() + getPlayer().getScreenY();

            if (worldX > getPlayer().getWorldX() - getPlayer().getScreenX() - tileSize * 2
                    && worldX < getPlayer().getWorldX() + getPlayer().getScreenX() + tileSize * 2
                    && worldY > getPlayer().getWorldY() - getPlayer().getScreenY() - tileSize * 2
                    && worldY < getPlayer().getWorldY() + getPlayer().getScreenY() + tileSize * 2) {
                for (int j = 0; j < 1000; j++) {
                    if (playerMP.getBomb()[j] != null) {
                        if (playerMP.getBomb()[j].stop == false) {
                            playerMP.getBomb()[j].setPosiX(screenX);
                            playerMP.getBomb()[j].setPosiY(screenY);
                            g2d.drawImage(playerMP.getBomb()[j].getBomBufferdImg(), playerMP.getBomb()[j].getPosiX(), playerMP.getBomb()[j].getPosiY(), 10, 10, this);
                        }
                    }
                }
            }

//            for (int j = 0; j < 1000; j++) {
//                if (playerMP.getBomb()[j] != null) {
//                    if (playerMP.getBomb()[j].stop == false) {
//                        g2d.drawImage(playerMP.getBomb()[j].getBomBufferdImg(), playerMP.getBomb()[j].getPosiX(), playerMP.getBomb()[j].getPosiY(), this);
//                    }
//                }
//            }

        }
    }

    public void changeToLoadingScene() {
        loadingPanel.setSize(screenWidth, screenHeight);
        add(loadingPanel);

    }

    public void changeToMazeMap() {
        remove(loadingPanel);
        currentMap = "maze";
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
            case "maze" -> mazeMap;
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

    public ArrayList<PlayerMP> getPlayers() {
        return pvpMap.players;
    }

    public MazeMap getMazeMap() {
        return mazeMap;
    }

    public void setMazeMap(MazeMap mazeMap) {
        this.mazeMap = mazeMap;
    }
}
