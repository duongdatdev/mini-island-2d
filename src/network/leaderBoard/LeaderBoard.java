package network.leaderBoard;

import javax.swing.*;
import java.awt.*;

public class LeaderBoard extends JScrollPane {
    private DefaultListModel<PlayerLB> leaderboardModel;
    private JList<PlayerLB> leaderboardList;

    public LeaderBoard() {
        setSize(300, 400);
        setBorder(BorderFactory.createEmptyBorder());

        leaderboardModel = new DefaultListModel<>();
        leaderboardList = new JList<>(leaderboardModel);
        leaderboardList.setCellRenderer(new PlayerCellRenderer());

        add("Top", 0);

        leaderboardList.setBackground(new Color(205,184,145));

        setViewportView(leaderboardList);

        this.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        this.getHorizontalScrollBar().setUI(new CustomScrollBarUI());

    }

    public void add(String username, int score) {
        PlayerLB player1 = new PlayerLB(username, score, new ImageIcon(this.getClass().getResource("/LeaderBoard/playerLB.png")));
        leaderboardModel.addElement(player1);
    }

    public void clear() {
        leaderboardModel.clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LeaderBoard example = new LeaderBoard();

            JFrame frame = new JFrame();

            frame.add(example);

            frame.setSize(300, 400);


            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });
    }
}
