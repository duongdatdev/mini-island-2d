package panels.loading;

import javax.swing.*;

public class LoadingScreen extends JFrame {
    public LoadingScreen() {
        setTitle("Loading...");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setUndecorated(true); // Remove the window borders

        // Add the gradient panel
        add(new LoadingPanel());

        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoadingScreen();
            }
        });
    }
}


