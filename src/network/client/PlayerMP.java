package network.client;

import entities.Player;

public class PlayerMP {
    private int id;
    private int x;
    private int y;
    private int dir;
    public PlayerMP(Player player){
        this.id = player.getID();
        this.x = player.getWorldX();
        this.y = player.getWorldY();
        this.dir = 1;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }
}
