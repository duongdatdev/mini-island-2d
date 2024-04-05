package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;

    private final int maxTilesX = 16;
    private final int maxTilesY = 16;
    private final int screenWidth = maxTilesX * tileSize;
    private final int screenHeight = maxTilesY * tileSize;

    public GamePanel() {
        init();
    }

    private void init() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setFocusable(true);
        this.requestFocus();
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }
}
