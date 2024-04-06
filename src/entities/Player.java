package entities;

import imageRender.ImageHandler;
import input.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private KeyHandler keyHandler;
    private int id;
    private String direction = "DOWN";
    private int spriteIndex = 0;
    private int countFrames = 0;

    public Player(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        setDefaultPosition();
        setDefaultSpeed();
        loadAssets();
    }

    public void loadAssets() {
        upImages = ImageHandler.loadAssets("/player/Character_Up.png", 32, 32);
        downImages = ImageHandler.loadAssets("/player/Character_Down.png", 32, 32);
        leftImages = ImageHandler.loadAssets("/player/Character_Left.png", 32, 32);
        rightImages = ImageHandler.loadAssets("/player/Character_Right.png", 32, 32);
    }

    private void setDefaultPosition() {
        x = 0;
        y = 0;
    }

    private void setDefaultSpeed() {
        speed = 5;
    }

    public void update() {
        if (isMove()) {
            if (keyHandler.isUp()) {
                direction = "UP";
                y -= speed;
            }
            if (keyHandler.isDown()) {
                direction = "DOWN";
                y += speed;
            }
            if (keyHandler.isLeft()) {
                direction = "LEFT";
                x -= speed;
            }
            if (keyHandler.isRight()) {
                direction = "RIGHT";
                x += speed;
            }
            countFrames++;
        }
        if (countFrames > 11) {
            spriteIndex++;
            if (spriteIndex > 3) {
                spriteIndex = 0;
            }
            countFrames = 0;
        }

    }

    public void draw(Graphics2D g2d, int tileSize) {
        BufferedImage playerImage = null;
        switch (direction) {
            case "UP":
                if (spriteIndex == 0) {
                    playerImage = upImages[0];
                }
                if (spriteIndex == 1) {
                    playerImage = upImages[1];
                }
                if (spriteIndex == 2) {
                    playerImage = upImages[2];
                }
                if (spriteIndex == 3) {
                    playerImage = upImages[3];
                }
                break;
            case "DOWN":
                if (spriteIndex == 0) {
                    playerImage = downImages[0];
                }
                if (spriteIndex == 1) {
                    playerImage = downImages[1];
                }
                if (spriteIndex == 2) {
                    playerImage = downImages[2];
                }
                if (spriteIndex == 3) {
                    playerImage = downImages[3];
                }
                break;
            case "LEFT":
                if (spriteIndex == 0) {
                    playerImage = leftImages[0];
                }
                if (spriteIndex == 1) {
                    playerImage = leftImages[1];
                }
                if (spriteIndex == 2) {
                    playerImage = leftImages[2];
                }
                if (spriteIndex == 3) {
                    playerImage = leftImages[3];
                }
                break;
            case "RIGHT":
                if (spriteIndex == 0) {
                    playerImage = rightImages[0];
                }
                if (spriteIndex == 1) {
                    playerImage = rightImages[1];
                }
                if (spriteIndex == 2) {
                    playerImage = rightImages[2];
                }
                if (spriteIndex == 3) {
                    playerImage = rightImages[3];
                }
                break;
        }
        g2d.drawImage(playerImage, x, y, tileSize * 2, tileSize * 2, null);
    }

    public boolean isMove() {
        return (keyHandler.isUp() || keyHandler.isDown() || keyHandler.isLeft() || keyHandler.isRight());
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public void setY(int y) {
        super.setY(y);
    }

    @Override
    public int getSpeed() {
        return super.getSpeed();
    }

    @Override
    public void setSpeed(int speed) {
        super.setSpeed(speed);
    }

}
