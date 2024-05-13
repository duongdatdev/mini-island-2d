package network.leaderBoard;

import javax.swing.*;
import java.awt.*;

public class PlayerCellRenderer extends JLabel implements ListCellRenderer<Player> {
    public PlayerCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Player> list, Player value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        setIcon(value.getImage());

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
