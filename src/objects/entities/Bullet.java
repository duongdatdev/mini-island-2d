package objects.entities;

import main.GameScene;
import network.client.Client;
import network.client.Protocol;
import network.entitiesNet.PlayerMP;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Bullet {
    private Image bulletImg;
    private BufferedImage bulletBuffImage;

    private int xPosi;
    private int yPosi;
    private int direction;
    public boolean stop = false;
    private float velocity = 10.0f; // Increased velocity for realistic movement

    private String playerShot;

    public Bullet(int x, int y, int direction, String playerShot) {
        xPosi = x;
        yPosi = y;
        this.direction = direction;
        stop = false;
        try {
            bulletImg = ImageIO.read(getClass().getResource("/player/Bomb/bomb.PNG"));
            bulletBuffImage = ImageIO.read(getClass().getResource("/player/Bomb/bomb.PNG"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.playerShot = playerShot;
    }

    public int getPosiX() {
        return xPosi;
    }

    public int getPosiY() {
        return yPosi;
    }

    public void setPosiX(int x) {
        xPosi = x;
    }

    public void setPosiY(int y) {
        yPosi = y;
    }

    public BufferedImage getBulletBuffImage() {
        return bulletBuffImage;
    }

    public boolean checkCollision() {
        ArrayList<PlayerMP> clientPlayers = GameScene.getInstance().getMap().players;
        for (PlayerMP player : clientPlayers) {
            int x = player.getX();
            int y = player.getY();
            if ((yPosi >= y && yPosi <= y + 43) && (xPosi >= x && xPosi <= x + 43)) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                GameScene.getInstance().getMap().removePlayer(player.getUsername());
                if (player.isAlive()) {
                    Client.getGameClient().sendToServer(new Protocol().bulletCollisionPacket(playerShot, player.getUsername()));
                }
                player.setAlive(false);
                return true;
            }
        }
        return false;
    }

    public void startBombThread(boolean checkCollision) {
        new BombShotThread(checkCollision).start();
    }

    private class BombShotThread extends Thread {
        boolean checkCollis;
        int distanceTraveled = 0;
        int maxDistance = 400; // Maximum distance a bomb can travel

        public BombShotThread(boolean chCollision) {
            checkCollis = chCollision;
        }

        public void run() {
            while (!stop && distanceTraveled < maxDistance) {
                int oldXPosi = xPosi;
                int oldYPosi = yPosi;

                switch (direction) {
                    case 2 -> yPosi -= velocity; // Up
                    case 1 -> yPosi += velocity; // Down
                    case 3 -> xPosi -= velocity; // Left
                    case 4 -> xPosi += velocity; // Right
                }

                distanceTraveled += Math.sqrt(Math.pow(xPosi - oldXPosi, 2) + Math.pow(yPosi - oldYPosi, 2));

                if (checkCollis && checkCollision()) {
                    stop = true;
                }

                try {
                    Thread.sleep(40);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            stop = true;
        }
    }

    public Image getBulletImg() {
        return bulletImg;
    }

    public void setBulletImg(Image bulletImg) {
        this.bulletImg = bulletImg;
    }

    public void setBulletBuffImage(BufferedImage bulletBuffImage) {
        this.bulletBuffImage = bulletBuffImage;
    }

    public int getxPosi() {
        return xPosi;
    }

    public void setxPosi(int xPosi) {
        this.xPosi = xPosi;
    }

    public int getyPosi() {
        return yPosi;
    }

    public void setyPosi(int yPosi) {
        this.yPosi = yPosi;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }
}
