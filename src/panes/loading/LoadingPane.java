package panes.loading;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadingPane extends JPanel {
    private String fullMessage = "Loading, please wait...";
    private int currentLength = 0;
    private Timer timer;

    public LoadingPane() {
        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentLength++;
                if (currentLength > fullMessage.length()) {
                    currentLength = 0; // Restart the animation
                }
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Create a gradient from top-left to bottom-right
        Color color1 = new Color(90, 105, 136); // Light blue
        Color color2 = new Color(139, 155, 180);  // Dark blue
        GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);

        // Draw the animated loading message
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        String currentMessage = fullMessage.substring(0, currentLength);
        FontMetrics fm = g2d.getFontMetrics();
        int x = (width - fm.stringWidth(fullMessage)) / 2;
        int y = (height - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(currentMessage, x, y);
    }
}

