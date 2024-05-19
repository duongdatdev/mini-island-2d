package panels.chat;

import font.CustomFont;
import main.CustomButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;

public class DialogText {
    private BufferedImage chatImage;
    private static BufferedImage oneLineImage;
    private BufferedImage twoLineImage;
    private BufferedImage threeLineImage;

    private DialogText instance;

    public DialogText() {
        try {
            oneLineImage = ImageIO.read(getClass().getResourceAsStream("/Chat/1line.png"));
            twoLineImage = ImageIO.read(getClass().getResourceAsStream("/Chat/2line.png"));
            threeLineImage = ImageIO.read(getClass().getResourceAsStream("/Chat/3line.png"));
        } catch (IOException e) {
            System.out.println("Error loading chat images");
            throw new RuntimeException(e);
        }

    }

    public static String breakIntoLines(String text) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        while (index < text.length()) {
            // Check if the remaining text has more than 22 characters
            if (index + 22 < text.length()) {
                // If so, add the next 22 characters to the result and a newline
                result.append(text.substring(index, index + 22)).append("\n");
                // Move the index 22 characters forward
                index += 22;
            } else {
                // If not, add the remaining text to the result
                result.append(text.substring(index));
                // Move the index to the end of the text
                index = text.length();
            }
        }
        return result.toString();
    }

    public BufferedImage loadImage(String text) {
        String brokenText = breakIntoLines(text);
        String[] lines = brokenText.split("\n");
        System.out.println(lines.length);

        System.out.println("Chat image loaded");
        Graphics g;
        int textHeight;
        switch (lines.length) {
            case 1:
                chatImage = oneLineImage;
                g = chatImage.getGraphics();
//                g.clearRect(0, 0, chatImage.getWidth(), chatImage.getHeight()); // Clear the old text
                g.setColor(Color.BLACK);
                textHeight = g.getFontMetrics().getHeight();

                g.setFont(new Font("Arial", Font.BOLD,10));
                g.drawString(lines[0], 10, textHeight + 5);
                break;
            case 2:
                chatImage = oneLineImage;
                g = chatImage.getGraphics();
//                g.clearRect(0, 0, chatImage.getWidth(), chatImage.getHeight()); // Clear the old text
                g.setColor(Color.BLACK);
                textHeight = g.getFontMetrics().getHeight();

                g.setFont(new Font("Arial", Font.BOLD,10));
                g.drawString(lines[0], 10, textHeight + 5);
                g.drawString(lines[1], 10, textHeight * 2 + 5);
                break;
            case 3:
                chatImage = twoLineImage;
                g = chatImage.getGraphics();
//                g.clearRect(0, 0, chatImage.getWidth(), chatImage.getHeight()); // Clear the old text
                g.setColor(Color.BLACK);
                textHeight = g.getFontMetrics().getHeight();

                g.setFont(new Font("Arial", Font.PLAIN,10));
                g.drawString(lines[0], 10, textHeight +5);
                g.drawString(lines[1], 10, textHeight * 2 + 5);
                g.drawString(lines[2], 10, textHeight * 3 + 5);
                break;
            default:
                chatImage = oneLineImage;
                g = chatImage.getGraphics();
//                g.clearRect(0, 0, chatImage.getWidth(), chatImage.getHeight()); // Clear the old text
                g.setColor(Color.BLACK);
                textHeight = g.getFontMetrics().getHeight();

                g.setFont(new Font("Arial", Font.PLAIN,10));
                g.drawString(lines[0], 10, textHeight + 5);
        }
        return chatImage;
    }

    public BufferedImage getChatImage() {
        return chatImage;
    }

    public void setChatImage(BufferedImage chatImage) {
        this.chatImage = chatImage;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(200, 200);

        DialogText dialogText = new DialogText();
        BufferedImage image = dialogText.loadImage("Hello, this is a test message to see if the text");
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
