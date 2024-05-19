package network.leaderBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerCellRenderer extends JLabel implements ListCellRenderer<Player> {
    public PlayerCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Player> list, Player value, int index, boolean isSelected, boolean cellHasFocus) {

        // Create a new image with the player's image and text drawn on it
        Image image = new BufferedImage(value.getImage().getImage().getWidth(null), value.getImage().getImage().getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.drawImage(value.getImage().getImage(), 0, 0, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(value.toString(), 10, 25);
        g.dispose();

        // Set the new image as the icon
        setIcon(new ImageIcon(image));

        // Set the background and foreground colors as before
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
