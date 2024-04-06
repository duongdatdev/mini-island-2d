package entities;

import java.awt.image.BufferedImage;

public abstract class Entity {
    protected int x;
    protected int y;
    protected int speed;
    public BufferedImage[] upImages, downImages, leftImages, rightImages;
    public String direction;


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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
