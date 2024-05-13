package objects.entities;

import main.GameScene;
import objects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity extends GameObject {
    protected int worldX;
    protected int worldY;
    protected int speed;
    public BufferedImage[][] upImages, downImages, leftImages, rightImages;
    public String direction = "STAND";
    protected Rectangle hitBox;
    protected boolean collision = false;

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }
}
