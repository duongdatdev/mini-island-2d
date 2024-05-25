package objects.entities;

import main.GameScene;
import network.client.Client;
import network.client.Protocol;
import network.entitiesNet.PlayerMP;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Bomb {

    /**
     * Creates a new instance of Bomb
     */

    private Image bombImg;
    private BufferedImage bombBuffImage;

    private int xPosi;
    private int yPosi;
    private int direction;
    public boolean stop = false;
    private float velocityX = 0.05f, velocityY = 0.05f;

    public Bomb(int x, int y, int direction) {
//        final SimpleSoundPlayer sound_boom =new SimpleSoundPlayer("boom.wav");
//        final InputStream stream_boom =new ByteArrayInputStream(sound_boom.getSamples());
        xPosi = x;
        yPosi = y;
        this.direction = direction;
        stop = false;
        try {
            bombImg = ImageIO.read(getClass().getResource("/player/Bomb/bomb.PNG"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            bombBuffImage = ImageIO.read(getClass().getResource("/player/Bomb/bomb.PNG"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        bombBuffImage.createGraphics().drawImage(bombImg, 0, 0, null);
//        Thread t= new Thread(new Runnable() {
//            public void run() {
//                sound_boom.play(stream_boom);
//            }
//        });
//        t.start();
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

    public BufferedImage getBomBufferdImg() {
        return bombBuffImage;
    }

    public BufferedImage getBombBuffImage() {
        return bombBuffImage;
    }

    public boolean checkCollision() {
        ArrayList<PlayerMP> clientTanks = GameScene.getInstance().getPlayers();
        int x, y;
        for (int i = 1; i < clientTanks.size(); i++) {
            if (clientTanks.get(i) != null) {
                x = clientTanks.get(i).getX();
                y = clientTanks.get(i).getY();

                if ((yPosi >= y && yPosi <= y + 43) && (xPosi >= x && xPosi <= x + 43)) {

//                    ClientGUI.setScore(50);

//                    ClientGUI.gameStatusPanel.repaint();

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    if (clientTanks.get(i) != null)
                        Client.getGameClient().sendToServer(new Protocol().RemoveClientPacket(clientTanks.get(i).getUsername()));

                    return true;
                }
            }
        }
        return false;
    }


    public void startBombThread(boolean chekCollision) {

        new BombShotThread(chekCollision).start();

    }

    private class BombShotThread extends Thread {
        boolean checkCollis;
        int distanceTraveled = 0; // New variable to track distance traveled
        int maxDistance = 500; // Maximum distance a bomb can travel

        public BombShotThread(boolean chCollision) {
            checkCollis = chCollision;
        }

        public void run() {
            if (checkCollis) {

                if (direction == 1) {
                    xPosi = 17 + xPosi;
                    while (yPosi > 0 && distanceTraveled < maxDistance) {
                        int oldYPosi = yPosi;
                        yPosi = (int) (yPosi - yPosi * velocityY);
                        distanceTraveled += Math.abs(yPosi - oldYPosi); // Update distance traveled
                        if (checkCollision()) {
                            break;
                        }
                        try {

                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }

                } else if (direction == 2) {
                    yPosi = 17 + yPosi;
                    xPosi += 30;
                    while (xPosi < 564 && distanceTraveled < maxDistance) {
                        int oldXPosi = xPosi;
                        xPosi = (int) (xPosi + xPosi * velocityX);
                        distanceTraveled += Math.abs(xPosi - oldXPosi); // Update distance traveled
                        if (checkCollision()) {
                            break;
                        }
                        try {

                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                } else if (direction == 3) {
                    yPosi += 30;
                    xPosi += 20;
                    while (yPosi < 505 && distanceTraveled < maxDistance) {
                        int oldYPosi = yPosi;
                        yPosi = (int) (yPosi + yPosi * velocityY);
                        distanceTraveled += Math.abs(yPosi - oldYPosi); // Update distance traveled
                        if (checkCollision()) {
                            break;
                        }
                        try {

                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                } else if (direction == 4) {
                    yPosi = 21 + yPosi;

                    while (xPosi > 70 && distanceTraveled < maxDistance) {
                        int oldXPosi = xPosi;
                        xPosi = (int) (xPosi - xPosi * velocityX);
                        distanceTraveled += Math.abs(xPosi - oldXPosi); // Update distance traveled
                        if (checkCollision()) {
                            break;
                        }
                        try {

                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                }

                stop = true;
            } else {
                if (direction == 1) {
                    xPosi = 17 + xPosi;
//                    while (yPosi > 50 && distanceTraveled < maxDistance) {
                        while (yPosi > 0 && distanceTraveled < maxDistance) {
                        yPosi = (int) (yPosi - yPosi * velocityY);

                        try {

                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }

                } else if (direction == 2) {
                    yPosi = 17 + yPosi;
                    xPosi += 30;
//                    while (xPosi < 564 && distanceTraveled < maxDistance) {
                        while (xPosi < 20000 && distanceTraveled < maxDistance) {
                        xPosi = (int) (xPosi + xPosi * velocityX);

                        try {

                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                } else if (direction == 3) {
                    yPosi += 30;
                    xPosi += 20;
//                    while (yPosi < 505 && distanceTraveled < maxDistance) {

                    while (yPosi < 20000 && distanceTraveled < maxDistance) {
                        yPosi = (int) (yPosi + yPosi * velocityY);

                        try {

                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                } else if (direction == 4) {
                    yPosi = 21 + yPosi;

//                    while (xPosi > 70 && distanceTraveled < maxDistance) {
                        while (xPosi > 0 && distanceTraveled < maxDistance) {
                        xPosi = (int) (xPosi - xPosi * velocityX);

                        try {

                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }
                }

                stop = true;
            }
        }
    }
}
