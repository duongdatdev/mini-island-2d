package panes.chat;

import main.GameScene;
import network.client.Client;
import network.client.Protocol;
import network.entitiesNet.PlayerMP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ChatPane extends JPanel implements ActionListener {
    private JButton submitButton;
    private JTextField chatField;
    private GameScene gameScene;
    private PlayerMP playerMP;
    private Client client;

    private BufferedImage chatImage;

    public ChatPane(GameScene gameScene) {
        submitButton = new JButton("Submit");
        chatField = new JTextField(20);

        this.add(chatField);
        this.add(submitButton);

        client = Client.getGameClient();

        this.gameScene = gameScene;

        this.playerMP = gameScene.getPlayerMP();

        this.submitButton.addActionListener(this);

        init();
    }
    private void init() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submitButton) {
            String message = chatField.getText();

            //load the image
            playerMP.setChatImage(playerMP.getDialogText().loadImage(message));

            // Send the message to the server
            client.sendToServer(new Protocol().chatPacket(gameScene.getPlayerMP().getUsername(),message));

            chatField.setText("");
        }

    }

    public BufferedImage getChatImage() {
        return chatImage;
    }

    public void setChatImage(BufferedImage chatImage) {
        this.chatImage = chatImage;
    }

    public void drawChatImage() {
        gameScene.getGraphics().drawImage(chatImage, 0, 0, null);
    }
}
