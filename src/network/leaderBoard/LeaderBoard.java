package network.leaderBoard;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LeaderBoard extends JScrollPane {
    private DefaultListModel<Player> leaderboardModel;
    private JList<Player> leaderboardList;

    public LeaderBoard() {
        setSize(300, 400);

        leaderboardModel = new DefaultListModel<>();
        leaderboardList = new JList<>(leaderboardModel);
        leaderboardList.setCellRenderer(new PlayerCellRenderer());

        add("Top",10);

        setViewportView(leaderboardList);

        this.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        this.getHorizontalScrollBar().setUI(new CustomScrollBarUI());

    }

    public void add(String username, int score) {
        Player player1 = new Player(username, score, new ImageIcon(this.getClass().getResource("/Login/Text_Field.png")));
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
