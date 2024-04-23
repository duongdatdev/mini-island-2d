package panels.signIn;

import main.GameScene;
import network.client.Client;
import network.client.ClientRecivingThread;
import network.client.Protocol;

import java.io.IOException;

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

    public boolean signIn() {
        client = Client.getGameClient();

        try {
            client.getWriter().writeUTF(protocol.LoginPacket(username, password,1000,1000));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean signUp() {
        return true;
    }
}
