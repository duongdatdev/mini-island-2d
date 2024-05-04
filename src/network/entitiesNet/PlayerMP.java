package network.entitiesNet;

import network.client.Client;
import network.client.Protocol;
import objects.entities.Player;

import java.awt.*;

public class PlayerMP {
    private Player player;
    private int x, y;
    private int id;
    private int direction;
    private String username;

    private Client client;

    public PlayerMP(Player player) {
        this.player = player;
        this.x = player.getWorldX();
        this.y = player.getWorldY();

        this.client = Client.getGameClient();
    }

    public PlayerMP(String username, int x, int y, int direction, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.direction = direction;
        this.username = username;
        this.player = new Player(username, x, y, direction, id);

        client = Client.getGameClient();
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
        if (player.isMove()  && !player.isCollision()) {
            x = player.getWorldX();
            y = player.getWorldY();
            switch (player.getDirection()) {
                case "DOWN" -> direction = 1;
                case "UP" -> direction = 2;
                case "LEFT" -> direction = 3;
                case "RIGHT" -> direction = 4;
            }
            client.sendToServer(new Protocol().UpdatePacket(x, y, id, direction));

            count = 1;
        }
        else {
            if (count == 1){
                if (player.getDirection().equals("STAND")) {
                    direction = 0;
                }
                client.sendToServer(new Protocol().UpdatePacket(x, y, id, direction));
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
    public void render(Graphics2D g2d, int tileSize) {
        g2d.drawString(username, player.getScreenX(), player.getScreenY() - 10);
        player.render(g2d, tileSize);
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

    public int getDirection() {
        switch (player.getDirection()) {
            case "DOWN" -> direction = 1;
            case "UP" -> direction = 2;
            case "LEFT" -> direction = 3;
            case "RIGHT" -> direction = 4;
        }
        return direction;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
