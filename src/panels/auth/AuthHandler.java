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
    private Socket clientAuthSocket;
    private DataInputStream reader;
    private DataOutputStream writer;
    private boolean isRunning = true;
    private Runnable onSuccess;

    private Client client;

    public AuthHandler(Runnable onSuccess) {
        this.onSuccess = onSuccess;


        try {
            clientAuthSocket = new Socket("localhost", 11111);
            reader = new DataInputStream(clientAuthSocket.getInputStream());
            writer = new DataOutputStream(clientAuthSocket.getOutputStream());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server is not running");
            System.exit(0);
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                if (reader.available() > 0) {
                    String sentence = reader.readUTF();
                    processResponse(sentence);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void processResponse(String response) {
        if (response.startsWith("Login")) {

            String[] parts = response.split(",");

            String status = parts[1];
            String msg = parts[2];

            SignInModel signInModel = SignInModel.getInstance();
            if (status.equals("Success")) {
                System.out.println("Login Success");

                closeAll();

                SignInModel.getInstance().setSignedIn(true);

                isRunning = false;
                onSuccess.run();
            } else {
                System.out.println("Login Failed");
                signInModel.setSignedIn(false);

                signInModel.setMsg(msg);

                isRunning = false;
                onSuccess.run();
            }
        } else if (response.startsWith("Register")) {
            String[] parts = response.split(",");

            String status = parts[1];
            String msg = parts[2];

            if (status.equals("Success")) {
                System.out.println("Register Success");
                JOptionPane.showMessageDialog(null, msg);
                closeAll();
                isRunning = false;
                onSuccess.run();
            } else {
                System.out.println("Register Failed");
                JOptionPane.showMessageDialog(null, msg);
                isRunning = false;
                onSuccess.run();
            }
        }
    }

    public void sendToServer(String message) {
        if (message.equals("exit")) {
            System.exit(0);
        } else {
            try {
                writer.writeUTF(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void closeAll() {
        try {
            reader.close();
            writer.close();
            clientAuthSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void stopHandler() {
        isRunning = false;
    }
}