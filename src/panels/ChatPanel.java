package panels;

import main.GameScene;
import network.client.Client;
import network.client.Protocol;
import network.entitiesNet.PlayerMP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatPanel extends JPanel implements ActionListener {
    private JButton submitButton;
    private JTextField chatField;
    private GameScene gameScene;
    private PlayerMP playerMP;
    private Client client;

    public ChatPanel(GameScene gameScene) {
        submitButton = new JButton("Submit");
        chatField = new JTextField(20);

        this.add(chatField);
        this.add(submitButton);

        client = Client.getGameClient();

        this.gameScene = gameScene;

        this.playerMP = gameScene.getPlayerMP();

        init();
    }
    private void init() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submitButton) {
            String message = chatField.getText();

            client.sendToServer(new Protocol().chatPacket(message));
        }

    }
}
