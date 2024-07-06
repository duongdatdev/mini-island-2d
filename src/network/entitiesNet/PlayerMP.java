package network.entitiesNet;

import main.GameScene;
import network.client.Client;
import network.client.Protocol;
import objects.entities.Bullet;
import objects.entities.Player;
import panes.chat.DialogText;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PlayerMP {
    private Player player;
    private int x, y;
    private int id;
    private int direction;
    private int lastDirection = 1;
    private String username;

    //Bombs
    private Bullet bullet[] = new Bullet[1000];
    private int curBullet = 0;

    private Socket clientSocket;
    private DataOutputStream writer;

    private BufferedImage chatImage;
    private Timer chatImageTimer;

    private DialogText dialogText;

    private boolean isAlive = true;

    private long lastShotTime;

    public PlayerMP(Player player) {
        this.player = player;
        this.x = player.getWorldX();
        this.y = player.getWorldY();

        writer = Client.getGameClient().getWriter();

        dialogText = new DialogText();

        lastShotTime = System.currentTimeMillis();

        chatImageTimer = new Timer(2000, e -> clearChatImage());
        chatImageTimer.setRepeats(false);
    }

    public PlayerMP(String username, int x, int y, int direction, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.direction = direction;
        this.username = username;
        this.player = new Player(username, x, y, direction, id);

        dialogText = new DialogText();

        lastShotTime = System.currentTimeMillis();

        chatImageTimer = new Timer(2000, e -> clearChatImage());
        chatImageTimer.setRepeats(false);
    }

    //Counter for the number of times the player has moved
    int count = 1;

    /**
     * Updates the player's position and direction
     */
    public void update() {
        if (player.getWorldX() != x || player.getWorldY() != y || player.getId() != id) {
            player.setId(id);
            x = player.getWorldX();
            y = player.getWorldY();
        }
        if (player.isMove() && !player.isCollision()) {
            x = player.getWorldX();
            y = player.getWorldY();
            switch (player.getDirection()) {
                case "DOWN" -> direction = 1;
                case "UP" -> direction = 2;
                case "LEFT" -> direction = 3;
                case "RIGHT" -> direction = 4;
            }
            updatePlayerInServer();

            count = 1;
        } else {
            if (count == 1) {
                if (player.getDirection().equals("STAND")) {
                    lastDirection = direction;
                    direction = 0;
                }
                updatePlayerInServer();

                count = 0;
            }
        }

        player.setWorldX(x);
        player.setWorldY(y);
        player.setId(id);
        switch (direction) {
            case 1 -> player.setDirection("DOWN");
            case 2 -> player.setDirection("UP");
            case 3 -> player.setDirection("LEFT");
            case 4 -> player.setDirection("RIGHT");
            default -> player.setDirection("STAND");
        }
    }

    public void updateLocation() {
        x = player.getWorldX();
        y = player.getWorldY();
    }

    public void updatePlayerInServer() {
        Client.getGameClient().sendToServer(new Protocol().UpdatePacket(username, x, y, direction));
    }

    public void render(Graphics2D g2d, int tileSize) {
        // Calculate the center of the player's sprite
        int centerX = player.getScreenX() + tileSize / 2;

        // Get the width of the username string
        int stringWidth = g2d.getFontMetrics().stringWidth(username);

        // Adjust the x-coordinate of the username to center it
        int usernameX = centerX - stringWidth / 2;

        // Draw the username at the calculated position
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString(username, usernameX, player.getScreenY());
        player.render(g2d, tileSize);
        if (chatImage != null) {
            g2d.drawImage(chatImage, player.getScreenX() - 50, player.getScreenY() - chatImage.getHeight() - 5, null);
        }
    }

    public void sendToServer(String message) {
        if (message.equals("exit"))
            System.exit(0);
        else {
            try {
                writer.writeUTF(message);
            } catch (IOException ex) {
            }
        }

    }

    public void shot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= 300) { // 300 milliseconds have passed since the last shot
            try {
                if (GameScene.getInstance().getCurrentMap().equals("pvp")) {
                    int bombDirection = direction != 0 ? direction : lastDirection;
                    bullet[curBullet] = new Bullet(this.getX(), this.getY(), bombDirection, username);
                    bullet[curBullet].startBombThread(true);
                    curBullet++;

                    Client.getGameClient().sendToServer(new Protocol().ShotPacket(username));
                }
            } catch (Exception e) {
                System.out.println("Out of bullets");
            }
            lastShotTime = currentTime; // Update the last shot time
        }
    }

    public void Shot() {
        try {
            int bombDirection = direction != 0 ? direction : lastDirection;
            bullet[curBullet] = new Bullet(this.getX(), this.getY(), bombDirection, username);
            bullet[curBullet].startBombThread(false);
            curBullet++;
        } catch (Exception e) {
            System.out.println("Out of bullets");
        }

    }

    public int getDirection() {
        switch (player.getDirection()) {

            case "DOWN" -> direction = 1;
            case "UP" -> direction = 2;
            case "LEFT" -> direction = 3;
            case "RIGHT" -> direction = 4;
            case "STAND" -> direction = 0;

        }
        return direction;
    }

    private void clearChatImage() {
        this.chatImage = null;
    }

    public void setDirection(int direction) {
        switch (direction) {

            case 1 -> player.setDirection("DOWN");
            case 2 -> player.setDirection("UP");
            case 3 -> player.setDirection("LEFT");
            case 4 -> player.setDirection("RIGHT");
            default -> player.setDirection("STAND");
        }
        this.direction = direction;
    }

    public Bullet[] getBullet() {
        return bullet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getX() {
        x = player.getWorldX();
        return x;
    }

    public void setX(int x) {
        player.setWorldX(x);
        this.x = x;
    }

    public int getY() {
        y = player.getWorldY();
        return y;
    }

    public void setY(int y) {
        player.setWorldY(y);
        this.y = y;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        player.setId(id);
        this.id = id;
    }

    public DataOutputStream getWriter() {
        return writer;
    }

    public void setWriter(DataOutputStream writer) {
        this.writer = writer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public BufferedImage getChatImage() {
        return chatImage;
    }

    public void setChatImage(BufferedImage chatImage) {
        this.chatImage = chatImage;
        chatImageTimer.restart();
    }

    public DialogText getDialogText() {
        return dialogText;
    }

    public void setDialogText(DialogText dialogText) {
        this.dialogText = dialogText;
    }

    public int getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(int lastDirection) {
        this.lastDirection = lastDirection;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}
