package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected int worldX;
    protected int worldY;
    protected int speed;
    public BufferedImage[] upImages, downImages, leftImages, rightImages;
    public String direction;
    protected Rectangle hitBox;


    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
