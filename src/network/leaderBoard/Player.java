package network.leaderBoard;

import javax.swing.*;

public class Player {
    private String name;
    private int score;
    private ImageIcon image;

    public Player(String name, int score, ImageIcon image) {
        this.name = name;
        this.score = score;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public ImageIcon getImage() {
        return image;
    }

    @Override
    public String toString() {
        return name + " - Score: " + score;
    }
}
