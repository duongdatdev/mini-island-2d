package network.client;

import main.GameScene;
import network.entitiesNet.PlayerMP;
import panels.auth.signIn.SignInModel;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Struct;

/**
 * This class is responsible for receiving messages from the server and updating the game state accordingly.
 * It is a thread that runs in the background and listens for messages from the server.
 * It updates the game state based on the messages received from the server.
 *
 * @author DuongDat
 */

public class ClientRecivingThread extends Thread {
    private Socket clientSocket;
    private DataInputStream reader;
    private PlayerMP clientPlayer;
    private GameScene boardPanel;
    boolean isRunning = true;

    /**
     * Creates a new instance of ClientRecivingThread
     *
     * @param clientSocket the client socket
     * @param clientPlayer the player
     * @param boardPanel   the game scene
     */

    public ClientRecivingThread(Socket clientSocket, PlayerMP clientPlayer, GameScene boardPanel) {
        this.clientSocket = clientSocket;
        this.clientPlayer = clientPlayer;
        this.boardPanel = boardPanel;
        try {
            reader = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public void run() {
        while (isRunning) {
            String sentence = "";
            try {
                sentence = reader.readUTF();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Received: " + sentence);
            if (sentence.startsWith("ID")) {
                int pos1 = sentence.indexOf(',');

                int id = Integer.parseInt(sentence.substring(2, pos1));
                String username = sentence.substring(pos1 + 1, sentence.length());

                clientPlayer.setID(id);
                clientPlayer.setUsername(username);

                System.out.println("My ID= " + id);
                System.out.println("My Username= " + clientPlayer.getUsername());

            } else if (sentence.startsWith("NewClient")) {
                int pos1 = sentence.indexOf(',');
                int pos2 = sentence.indexOf('-');
                int pos3 = sentence.indexOf('|');
                int pos4 = sentence.indexOf('!');

                String username = sentence.substring(9, pos1);
                int x = Integer.parseInt(sentence.substring(pos1 + 1, pos2));
                int y = Integer.parseInt(sentence.substring(pos2 + 1, pos3));
                int dir = Integer.parseInt(sentence.substring(pos3 + 1, pos4));
                int id = Integer.parseInt(sentence.substring(pos4 + 1, sentence.length()));

                if (id != clientPlayer.getID())
                    boardPanel.registerNewPlayer(new PlayerMP(username, x, y, dir, id));

                System.out.println("New Client ID= " + id);
                System.out.println("New Client Username= " + username);

            } else if (sentence.startsWith("Update")) {
                String[] parts = sentence.split(",");

                String username = parts[1];
                int x = Integer.parseInt(parts[2]);
                int y = Integer.parseInt(parts[3]);
                int dir = Integer.parseInt(parts[4]);
                int id = Integer.parseInt(parts[5]);

                if (id != clientPlayer.getID()) {
                    boardPanel.getPlayer(id).setX(x);
                    boardPanel.getPlayer(id).setY(y);
                    boardPanel.getPlayer(id).setDirection(dir);
                }

            } else if (sentence.startsWith("Shot")) {

                int id = Integer.parseInt(sentence.substring(4));

                if (id != clientPlayer.getID()) {
                    System.out.println("Shot from " + id);
                }

            } else if (sentence.startsWith("Remove")) {
                int id = Integer.parseInt(sentence.substring(6));

                if (id == clientPlayer.getID()) {
                    int response = JOptionPane.showConfirmDialog(null, "Sorry, You are loss. Do you want to try again ?", "2D Multiplayer Game", JOptionPane.OK_CANCEL_OPTION);
                    if (response == JOptionPane.OK_OPTION) {
                        //client.closeAll();
//                        clientGUI.setVisibility(false);
//                        clientGUI.dispose();

                    } else {
                        System.exit(0);
                    }
                } else {
                    boardPanel.removePlayer(id);
                }
            } else if (sentence.startsWith("Exit")) {
                int id = Integer.parseInt(sentence.substring(4));

                if (id != clientPlayer.getID()) {
                    boardPanel.removePlayer(id);
                }
            }

        }

        try {
            reader.close();
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
