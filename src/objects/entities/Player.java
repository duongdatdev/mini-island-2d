package objects.entities;

import imageRender.ImageHandler;
import input.KeyHandler;
import main.GameScene;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player extends Entity {
    private int screenX;
    private int screenY;
    private int scale = 1;
    private KeyHandler keyHandler;
    private int id;
    private int spriteIndex = 0;
    private int countFrames = 0;
    private BufferedImage[][] standingImages;
    public GameScene gameScene;
    private String username;

    private String lastDirection = "DOWN";

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
        this.direction = "STAND";

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
            int futureX = worldX;
            int futureY = worldY;

            if (keyHandler.isUp()) {
                direction = "UP";
                futureY -= speed;
            }
            if (keyHandler.isDown()) {
                direction = "DOWN";
                futureY += speed;
            }
            if (keyHandler.isLeft()) {
                direction = "LEFT";
                futureX -= speed;
            }
            if (keyHandler.isRight()) {
                direction = "RIGHT";
                futureX += speed;
            }

            count = 1;
            collision = false;

            gameScene.getCollisionChecker().checkTile(this);
//            gameScene.getCollisionChecker().checkCollision(this, gameScene.getMap().getNpcs());

            if (!collision) {
                worldX = futureX;
                worldY = futureY;
            }

        } else if (isSpace()) {
            System.out.println("Player shot");
            gameScene.getPlayerMP().shot();
        } else {
            if (count == 1) {
                lastDirection = direction;
                direction = "STAND";
            }
            count = 0;
        }

        // update the position of each bullet
//        Iterator<Bullet> iterator = bullets.iterator();
//        while (iterator.hasNext()) {
//            Bullet bullet = iterator.next();
//            bullet.move();
////            if (bullet.isOutOfScreen()) { // assuming you have this method to check if bullet is out of screen
////                iterator.remove();
////            }
//        }
    }

    public void render(Graphics2D g2d, int tileSize) {
        g2d.drawImage(currentSprite(), screenX, screenY, tileSize * scale, tileSize * scale, null);
//        for (Bullet bullet : bullets) {
//            bullet.render(g2d,, 10);
//        }
    }

    public boolean isSpace() {
        return keyHandler.isSpace();
    }

//    public void shot() {
//        Bullet bullet = new Bullet(worldX, worldY, lastDirection);
//        bullets.add(bullet);
//    }

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
