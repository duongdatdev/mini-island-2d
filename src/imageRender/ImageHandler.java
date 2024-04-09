package imageRender;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageHandler {
    public static BufferedImage[] loadAssets(String imagePath,int tileWidth,int tileHeight) {
        try {
            BufferedImage tileMap = ImageIO.read(ImageHandler.class.getResource(imagePath));

            int rows = tileMap.getHeight() / tileHeight;
            int cols = tileMap.getWidth() / tileWidth;

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
