package panels.auth.signUp;

import network.client.Client;
import network.client.Protocol;

public class SignUpModel {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private Client client;
    public SignUpModel() {
        client = Client.getGameClient();
    }

    /**
     * Creates a new account
     * @return a message indicating the result of the operation
     */
    public String signUp() {
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match";
        }
        client.sendToServer(new Protocol().RegisterPacket(username, email, password));
        return "Account created successfully";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
