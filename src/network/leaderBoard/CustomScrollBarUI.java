package network.leaderBoard;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CustomScrollBarUI extends BasicScrollBarUI {
    private BufferedImage thumbImage, trackImage, arrowUpImage, arrowDownImage;

    public CustomScrollBarUI() {
        try {
            thumbImage = ImageIO.read(getClass().getResource("/Ui/thumb.png"));
            trackImage = ImageIO.read(getClass().getResource("/Ui/track.png"));
            arrowUpImage = ImageIO.read(getClass().getResource("/Ui/arrowUp.png"));
            arrowDownImage = ImageIO.read(getClass().getResource("/Ui/arrowDown.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage(thumbImage, thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, null);
            g2d.dispose();
        } else {
            super.paintThumb(g, c, thumbBounds);
        }
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        if (trackImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage(trackImage, trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, null);
            g2d.dispose();
        } else {
            super.paintTrack(g, c, trackBounds);
        }
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setVisible(false);
        return button;
    }
    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setVisible(false);
        return button;
    }
}

