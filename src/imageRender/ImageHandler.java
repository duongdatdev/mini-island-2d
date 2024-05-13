package imageRender;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageHandler {
    public static BufferedImage[][] loadAssets(String imagePath,int tileWidth,int tileHeight) {
        try {
            BufferedImage tileMap = ImageIO.read(ImageHandler.class.getResource(imagePath));

            int rows = tileMap.getHeight() / tileHeight;
            int cols = tileMap.getWidth() / tileWidth;


            /*
            * 4 is the number of states of the player
            * 0 -> Default
            * 1 -> Swimming
            * 2 -> Attacking
            * 3 -> Dead
             */
            int states = 4;

            BufferedImage[][] icons = new BufferedImage[4][rows * cols];

            int state = 0;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    icons[state][row * cols + col] = tileMap.getSubimage(col * tileWidth, row * tileHeight, tileWidth, tileHeight);
                }

            }
            return icons;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static BufferedImage[] loadAssets2(String imagePath,int tileWidth,int tileHeight) {
        try {
            BufferedImage tileMap = ImageIO.read(ImageHandler.class.getResource(imagePath));

            int rows = tileMap.getHeight() / tileHeight;
            int cols = tileMap.getWidth() / tileWidth;


            /*
             * 4 is the number of states of the player
             * 0 -> Default
             * 1 -> Swimming
             * 2 -> Attacking
             * 3 -> Dead
             */
            int states = 4;

            BufferedImage[] icons = new BufferedImage[rows * cols];

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    icons[row * cols + col] = tileMap.getSubimage(col * tileWidth, row * tileHeight, tileWidth, tileHeight);
                }

            }
            return icons;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
