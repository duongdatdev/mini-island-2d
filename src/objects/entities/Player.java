package objects.entities;

import imageRender.ImageHandler;
import input.KeyHandler;
import main.GameScene;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private int screenX;
    private int screenY;
    private int scale = 1;
    private KeyHandler keyHandler;
    private int id;
    private int spriteIndex = 0;
    private int countFrames = 0;
    private BufferedImage[][] standingImages;
    private GameScene gameScene;
    private String username;

    private int state = 0;

    public Player(GameScene gameScene, KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        this.gameScene = gameScene;

        hitBox = new Rectangle();
        hitBox.width = gameScene.getTileSize() - 16;
        hitBox.height = gameScene.getTileSize() - 16;
        hitBox.x = 8;
        hitBox.y = 16;

        setDefaultPosition();
        setDefaultSpeed();
        loadAssets();
    }

    public Player(String username, int x, int y, int dir, int id) {
        this.username = username;
        this.id = id;
        this.worldX = x;
        this.worldY = y;
        this.direction = "DOWN";

//        setDefaultPosition();
//        setDefaultSpeed();
        loadAssets();
    }

    public void loadAssets() {
        upImages = ImageHandler.loadAssets("/player/Character_Up.png", 32, 32);
        downImages = ImageHandler.loadAssets("/player/Character_Down.png", 32, 32);
        leftImages = ImageHandler.loadAssets("/player/Character_Left.png", 32, 32);
        rightImages = ImageHandler.loadAssets("/player/Character_Right.png", 32, 32);
        standingImages = ImageHandler.loadAssets("/player/Character_Stand.png", 32, 32);
    }

    private void setDefaultPosition() {
        screenX = gameScene.getScreenWidth() / 2 - gameScene.getTileSize() * scale / 2;
        screenY = gameScene.getScreenHeight() / 2 - gameScene.getTileSize() * scale / 2;
        worldX = 1000;
        worldY = 1000;
    }

    private void setDefaultSpeed() {
        speed = 3;
    }

    //Counter for the number of times the player has moved
    int count = 1;

    /**
     * Updates the player's position and direction
     */
    public void update() {

        if (isMove()) {

            if (keyHandler.isUp()) {
                direction = "UP";
            }
            if (keyHandler.isDown()) {
                direction = "DOWN";
            }
            if (keyHandler.isLeft()) {
                direction = "LEFT";
            }
            if (keyHandler.isRight()) {
                direction = "RIGHT";
            }
            count = 1;
            collision = false;

            gameScene.getCollisionChecker().checkTile(this);

            if (!collision) {
                if (keyHandler.isUp()) {
                    worldY -= speed;
                }
                if (keyHandler.isDown()) {
                    worldY += speed;
                }
                if (keyHandler.isLeft()) {
                    worldX -= speed;
                }
                if (keyHandler.isRight()) {
                    worldX += speed;
                }
            }
        } else {
            if (count == 1) {
                direction = "STAND";
            }
            count = 0;
        }
    }

    public void render(Graphics2D g2d, int tileSize) {
        g2d.drawImage(currentSprite(), screenX, screenY, tileSize * scale, tileSize * scale, null);
    }

    public BufferedImage currentSprite() {
        BufferedImage playerImage = null;
        countFrames++;
        if (countFrames > 11) {
            spriteIndex++;
            if (spriteIndex > 3) {
                spriteIndex = 0;
            }
            countFrames = 0;
        }
        switch (direction) {
            case "UP":
                if (spriteIndex == 0) {
                    playerImage = upImages[state][0];
                }
                if (spriteIndex == 1) {
                    playerImage = upImages[state][1];
                }
                if (spriteIndex == 2) {
                    playerImage = upImages[state][2];
                }
                if (spriteIndex == 3) {
                    playerImage = upImages[state][3];
                }
                break;
            case "DOWN":
                if (spriteIndex == 0) {
                    playerImage = downImages[state][0];
                }
                if (spriteIndex == 1) {
                    playerImage = downImages[state][1];
                }
                if (spriteIndex == 2) {
                    playerImage = downImages[state][2];
                }
                if (spriteIndex == 3) {
                    playerImage = downImages[state][3];
                }
                break;
            case "LEFT":
                if (spriteIndex == 0) {
                    playerImage = leftImages[state][0];
                }
                if (spriteIndex == 1) {
                    playerImage = leftImages[state][1];
                }
                if (spriteIndex == 2) {
                    playerImage = leftImages[state][2];
                }
                if (spriteIndex == 3) {
                    playerImage = leftImages[state][3];
                }
                break;
            case "RIGHT":
                if (spriteIndex == 0) {
                    playerImage = rightImages[state][0];
                }
                if (spriteIndex == 1) {
                    playerImage = rightImages[state][1];
                }
                if (spriteIndex == 2) {
                    playerImage = rightImages[state][2];
                }
                if (spriteIndex == 3) {
                    playerImage = rightImages[state][3];
                }
                break;
            case "STAND":
                if (spriteIndex == 0) {
                    playerImage = standingImages[state][0];
                }
                if (spriteIndex == 1) {
                    playerImage = standingImages[state][1];
                }
                if (spriteIndex == 2) {
                    playerImage = standingImages[state][2];
                }
                if (spriteIndex == 3) {
                    playerImage = standingImages[state][3];
                }
                break;
        }
        return playerImage;
    }

    public boolean isMove() {
        return (keyHandler.isUp() || keyHandler.isDown() || keyHandler.isLeft() || keyHandler.isRight());
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Graphics graphics) {

    }
}
