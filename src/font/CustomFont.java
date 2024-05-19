package font;

import java.awt.*;
import java.io.InputStream;

public class CustomFont {
    private static final CustomFont CUSTOM_FONT = new CustomFont();
    public static Font pixelGameFont;

    private CustomFont() {

        try {
            InputStream is = this.getClass().getResourceAsStream("/font/PixelGameFont.ttf");
            assert is != null;
            pixelGameFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
            pixelGameFont = pixelGameFont.deriveFont(Font.PLAIN, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
