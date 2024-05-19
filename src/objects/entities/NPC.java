package objects.entities;

import java.awt.*;

public class NPC {
    protected int worldX, worldY;
    protected Image sprite; // NPC's sprite
    protected String name;

    public NPC(String name,int worldX, int worldY,Image sprite) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.sprite = sprite;
        this.name = name;
    }

    public void render(Graphics2D g, int tileSize, int x, int y) {
        g.drawString(name, x, y - 10);
        g.drawImage(sprite, x , y , tileSize, tileSize, null);
    }

    public void checkDraw(Player player,Graphics2D g, int tileSize) {
        int screenX = worldX - player.getWorldX() + player.getScreenX();
        int screenY = worldY - player.getWorldY() + player.getScreenY();

        if (worldX > player.getWorldX() - player.getScreenX() - tileSize*2
                && worldX < player.getWorldX() + player.getScreenX() + tileSize*2
                && worldY > player.getWorldY() - player.getScreenY() - tileSize*2
                && worldY < player.getWorldY() + player.getScreenY()+ tileSize*2) {
            render(g, tileSize, screenX, screenY);
        }
    }

    public boolean isPlayerNear(Player player) {
        int distance = Math.abs(player.getWorldX() - this.worldX) + Math.abs(player.getWorldY() - this.worldY);
        return distance <= 50;
    }

    public int getX() {
        return worldX;
    }

    public void setX(int x) {
        this.worldX = x;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }
}
