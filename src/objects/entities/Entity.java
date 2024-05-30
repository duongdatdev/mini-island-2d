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
    protected boolean flagUpdate = true;

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }

    public int getSpeed() {
        return speed + 2;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isFlagUpdate() {
        return flagUpdate;
    }

    public void setFlagUpdate(boolean flagUpdate) {
        this.flagUpdate = flagUpdate;
    }
}
