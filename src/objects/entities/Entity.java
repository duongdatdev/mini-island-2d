package objects.entities;

import main.GameScene;
import objects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity extends GameObject{
    protected int worldX;
    protected int worldY;
    protected int speed;
    public BufferedImage[] upImages, downImages, leftImages, rightImages;
    public String direction;
    protected Rectangle hitBox;


    /**
     * Abstract class for almost every in-game object.
     *
     * @param gameScene in-game scene
     */

}
