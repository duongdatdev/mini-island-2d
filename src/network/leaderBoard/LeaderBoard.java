package network.leaderBoard;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LeaderBoard extends JFrame {
    private DefaultListModel<Player> leaderboardModel;
    private JList<Player> leaderboardList;

    public LeaderBoard() {
        setTitle("Leaderboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);



        leaderboardModel = new DefaultListModel<>();
        leaderboardList = new JList<>(leaderboardModel);
        leaderboardList.setCellRenderer(new PlayerCellRenderer());

//        // Kết nối và truy vấn cơ sở dữ liệu
//        try {
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT name, score FROM players ORDER BY score DESC");
//
//            // Lấy dữ liệu từ cơ sở dữ liệu và thêm vào leaderboardModel
//            while (rs.next()) {
//                String name = rs.getString("name");
//                int score = rs.getInt("score");
//                // Đường dẫn đến hình ảnh có thể được thay đổi tùy thuộc vào cách bạn lưu trữ hình ảnh trong cơ sở dữ liệu hoặc file system
//                ImageIcon image = new ImageIcon("path_to_image_directory/" + name + ".jpg");
//                leaderboardModel.addElement(new Player(name, score, image));
//            }
//
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        leaderboardModel.addElement(new Player("Alice", 100, new ImageIcon(LeaderBoard.class.getResource("/Login/Text_Field.png"))));
        leaderboardModel.addElement(new Player("Bob", 90, new ImageIcon(LeaderBoard.class.getResource("/Login/Text_Field.png"))));
        leaderboardModel.addElement(new Player("Charlie", 80, new ImageIcon(LeaderBoard.class.getResource("/Login/Text_Field.png"))));
        leaderboardModel.addElement(new Player("David", 70, new ImageIcon(LeaderBoard.class.getResource("/Login/Text_Field.png"))));

        JScrollPane scrollPane = new JScrollPane(leaderboardList);

        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());

        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LeaderBoard example = new LeaderBoard();
            example.setVisible(true);
        });
    }
}
