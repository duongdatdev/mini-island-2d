package panels.auth;

import main.GameScene;
import network.client.Client;
import network.entitiesNet.PlayerMP;
import panels.auth.signIn.SignInModel;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class AuthHandler extends Thread {
    private Socket clientSocket;
    private DataInputStream reader;
    private DataOutputStream writer;
    private boolean isRunning = true;
    private Runnable onSuccess;

    public AuthHandler(Socket clientSocket,Runnable onSuccess) {
        this.clientSocket = Client.getGameClient().getSocket();
        this.onSuccess = onSuccess;
        try {
            reader = new DataInputStream(clientSocket.getInputStream());
            writer = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        while (isRunning) {
            String sentence = "";
            try {
                sentence = reader.readUTF();
                System.out.println(sentence);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (sentence.startsWith("Login")) {
                String status = sentence.substring(5, sentence.length());
                if (status.equals("Success")) {
                    System.out.println("Login Success");
                    onSuccess.run();
                    try {
                        writer.writeUTF("Exit Auth");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    SignInModel.getInstance().setSignedIn(true);
                    isRunning = false;
                } else {
                    System.out.println("Login Failed");
                    SignInModel.getInstance().setSignedIn(false);
                }
            } else if (sentence.startsWith("Register")) {

                int pos1 = sentence.indexOf(',');
                String status = sentence.substring(8, pos1);

                if (status.equals("Success")) {
                    int posResult = sentence.indexOf('|');

                    JOptionPane.showMessageDialog(null, sentence.substring(pos1 + 1, posResult),"Success", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Register Success");
                } else {
                    System.out.println("Register Failed");
                    JOptionPane.showMessageDialog(null, sentence.substring(pos1 + 1, sentence.length()),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
