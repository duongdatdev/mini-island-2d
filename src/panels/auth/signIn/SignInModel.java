package panels.auth.signIn;

import network.client.Client;
import network.client.Protocol;
import panels.auth.AuthHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SignInModel {
    private int id;
    private String username;
    private String password;
    private String email;
    private Client client;
    private boolean isSignedIn = false;

    private Protocol protocol = new Protocol();

    private static SignInModel instance;


    public static SignInModel getInstance() {
        if (instance == null) {
            instance = new SignInModel();
        }
        return instance;
    }

    private SignInModel() {
        client = Client.getGameClient();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isSignedIn() {
        return isSignedIn;
    }
    public void setSignedIn(boolean signedIn) {
        isSignedIn = signedIn;
    }

    public void signIn(Runnable onSuccess) {
        Socket socket = null;
        try {
            socket = Client.getGameClient().getSocket();
            DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

            outToServer.writeUTF("Login," + username + "," + password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new AuthHandler(socket,onSuccess).start();
    }

    public boolean signUp() {
        return true;
    }
}
