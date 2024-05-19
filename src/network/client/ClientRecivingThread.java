package network.client;

import main.GameScene;
import network.entitiesNet.PlayerMP;
import panels.auth.signIn.SignInModel;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * This class is responsible for receiving messages from the server and updating the game state accordingly.
 * It is a thread that runs in the background and listens for messages from the server.
 * It updates the game state based on the messages received from the server.
 *
 * @author DuongDat
 */

public class ClientRecivingThread extends Thread {

    private DataInputStream reader;

    private PlayerMP clientPlayer;
    private GameScene gameScene;
    boolean isRunning = true;

    private Socket socket;

    /**
     * Creates a new instance of ClientRecivingThread
     *
     * @param clientPlayer the player
     * @param gameScene    the game scene
     */

    public ClientRecivingThread(Socket socket, PlayerMP clientPlayer, GameScene gameScene) {


        this.clientPlayer = clientPlayer;
        this.gameScene = gameScene;

        this.socket = socket;

        try {
            reader = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        while (isRunning) {
            String sentence = "";
            try {
                sentence = reader.readUTF();
                System.out.println("Received: " + sentence);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
                int pos5 = sentence.indexOf('#');

                String username = sentence.substring(9, pos1);
                int x = Integer.parseInt(sentence.substring(pos1 + 1, pos2));
                int y = Integer.parseInt(sentence.substring(pos2 + 1, pos3));
                int dir = Integer.parseInt(sentence.substring(pos3 + 1, pos4));
                int id = Integer.parseInt(sentence.substring(pos4 + 1, pos5));
                String map = sentence.substring(pos5 + 1, sentence.length());

                if (!username.equals(gameScene.getPlayerMP().getUsername())) {
                    if (gameScene.getCurrentMap().equals(map))
                        gameScene.registerNewPlayer(new PlayerMP(username, x, y, dir, id));
                }
//                if (id != clientPlayer.getID())d
//                    gameScene.registerNewPlayer(new PlayerMP(username, x, y, dir, id));

                System.out.println("New Client ID= " + id);
                System.out.println("New Client Username= " + username);

            } else if (sentence.startsWith("Update")) {
                String[] parts = sentence.split(",");

                String username = parts[1];
                int x = Integer.parseInt(parts[2]);
                int y = Integer.parseInt(parts[3]);
                int dir = Integer.parseInt(parts[4]);
                int id = Integer.parseInt(parts[5]);

                if (!username.equals(clientPlayer.getUsername())) {
                    PlayerMP player = gameScene.getMap().getPlayer(username);

                    if (player != null) {
                        player.setX(x);
                        player.setY(y);
                        player.setDirection(dir);
                    } else {
                        System.out.println("Player with username " + username + " not found in the map.");
                    }
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
                        //han

                    } else {
                        System.exit(0);
                    }
                } else {
                    String username = gameScene.getPlayerMP().getUsername();

                    gameScene.removePlayer(username);
                }

            } else if (sentence.startsWith("TeleportToMap")) {
                String[] parts = sentence.split(",");

                String username = parts[1];
                String mapName = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);


                System.out.println(gameScene.getPlayerMP().getUsername());
                System.out.println(username);
                System.out.println(!username.equals(gameScene.getPlayerMP().getUsername()));
                if (!username.equals(gameScene.getPlayerMP().getUsername())) {
                    gameScene.teleportPlayer(username, mapName, x, y);
                }
            } else if (sentence.startsWith("Chat")) {
                String[] parts = sentence.split(",");

                String username = parts[1];
                String message = parts[2];


                if (username.equals(clientPlayer.getUsername())) {
                    gameScene.getPlayerMP().getDialogText().loadImage(message);
                } else {
                    gameScene.getPlayer(username).getDialogText().loadImage(message);
                }
            } else if (sentence.startsWith("Leaderboard")) {

                System.out.println(sentence);
                String[] parts = sentence.split(",");

                gameScene.getLeaderBoard().clear();
                for (int i = 1; i < parts.length; i++) {
                    String[] playerInfo = parts[i].split(" ");
                    String username = playerInfo[0];
                    int score = Integer.parseInt(playerInfo[1]);

                    gameScene.getLeaderBoard().add(username, score);
                }
            } else if (sentence.startsWith("Exit")) {
                String username = sentence.substring(4, sentence.length());

                System.out.println("Exit: " + username);

                if (!username.equals(clientPlayer.getUsername())) {
                    gameScene.removePlayer(username);
                }
            }

        }

        try {
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
