package imageRender;

import java.awt.image.BufferedImage;

public class ImageLoader {
    private static ImageLoader instance;
    private int tileSize = 32;
    public BufferedImage[][] upImages, downImages, leftImages, rightImages, standingImages;

    private ImageLoader() {
        upImages = new BufferedImage[2][];
        downImages = new BufferedImage[2][];
        leftImages = new BufferedImage[2][];
        rightImages = new BufferedImage[2][];
        standingImages = new BufferedImage[2][];
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    public void loadImage() {
        upImages[0] = ImageHandler.loadAssets("/player/1/Character_Up.png", tileSize, tileSize);
        downImages[0] = ImageHandler.loadAssets("/player/1/Character_Down.png", tileSize, tileSize);
        leftImages[0] = ImageHandler.loadAssets("/player/1/Character_Left.png", tileSize, tileSize);
        rightImages[0] = ImageHandler.loadAssets("/player/1/Character_Right.png", tileSize, tileSize);
        standingImages[0] = ImageHandler.loadAssets("/player/1/Character_Stand.png", tileSize, tileSize);

        int tileSize2 = 16;
        upImages[1] = ImageHandler.loadAssets("/player/2/Character_Up.png", tileSize2, tileSize2);
        downImages[1] = ImageHandler.loadAssets("/player/2/Character_Down.png", tileSize2, tileSize2);
        leftImages[1] = ImageHandler.loadAssets("/player/2/Character_Left.png", tileSize2, tileSize2);
        rightImages[1] = ImageHandler.loadAssets("/player/2/Character_Right.png", tileSize2, tileSize2);
        standingImages[1] = ImageHandler.loadAssets("/player/2/Character_Stand.png", tileSize2, tileSize2);
    }
}
