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

    public PlayerMP(int x, int y, int direction, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.direction = direction;
        this.player = new Player(x, y, direction, id);

        client = Client.getGameClient();
    }

    public PlayerMP(String username, int x, int y, int direction, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.direction = direction;
        this.username = username;
        this.player = new Player(x, y, direction, id);

        client = Client.getGameClient();
    }

    public void update() {
        if (player.getWorldX() != x || player.getWorldY() != y || player.getId() != id) {
            player.setId(id);
            x = player.getWorldX();
            y = player.getWorldY();
        }
        if (player.isMove()) {
            x = player.getWorldX();
            y = player.getWorldY();
            direction = 1;
            client.sendToServer(new Protocol().UpdatePacket(x, y, id, direction));
        }
    }

    public void render(Graphics2D g2d, int tileSize) {
        player.render(g2d, tileSize);
    }

    public void renderMP(Graphics2D g2d, int tileSize) {

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
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
