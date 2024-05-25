package objects.entities;

import java.awt.*;

public class NPC extends Entity {
    protected Image sprite; // NPC's sprite
    protected String name;
    protected int tileSize;

    public NPC(String name,int worldX, int worldY,Image sprite, int tileSize) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.sprite = sprite;
        this.name = name;
        this.tileSize = tileSize;
    }

    public void render(Graphics2D g, int x, int y) {
        g.drawString(name, x, y - 10);
        g.drawImage(sprite, x , y , tileSize, tileSize, null);
    }

    public void checkDraw(Player player,Graphics2D g) {
        int screenX = worldX - player.getWorldX() + player.getScreenX();
        int screenY = worldY - player.getWorldY() + player.getScreenY();

        if (worldX > player.getWorldX() - player.getScreenX() - tileSize*2
                && worldX < player.getWorldX() + player.getScreenX() + tileSize*2
                && worldY > player.getWorldY() - player.getScreenY() - tileSize*2
                && worldY < player.getWorldY() + player.getScreenY()+ tileSize*2) {
            render(g, screenX, screenY);
        }
    }

    public boolean isPlayerNear(Player player) {
        int playerX = player.getWorldX();
        int playerY = player.getWorldY();

        // Assuming the sprite's width and height are equal to tileSize
        int spriteWidth = tileSize;
        int spriteHeight = tileSize;

        int expanded = 15;

        // Calculate the bounds of the NPC's sprite box
        int leftBound = worldX - expanded;
        int rightBound = worldX + spriteWidth + expanded;
        int upperBound = worldY - expanded;
        int lowerBound = worldY + spriteHeight + expanded;

        // Check if the player's position is within the bounds of the NPC's sprite box
        return playerX >= leftBound && playerX <= rightBound && playerY >= upperBound && playerY <= lowerBound;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Graphics graphics) {

    }
}
